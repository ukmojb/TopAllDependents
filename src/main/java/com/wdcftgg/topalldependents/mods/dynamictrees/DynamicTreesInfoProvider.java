package com.wdcftgg.topalldependents.mods.dynamictrees;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.network.MapSignal;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.blocks.BlockTrunkShell;
import com.ferreusveritas.dynamictrees.systems.nodemappers.NodeNetVolume;
import com.ferreusveritas.dynamictrees.trees.Species;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class DynamicTreesInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return "random.dynamic_trees";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, @Nonnull IBlockState blockState, IProbeHitData data) {
        Block block = blockState.getBlock();
        if (block instanceof BlockRooty) {
            int life = ((BlockRooty) block).getSoilLife(blockState, world, data.getPos());
            probeInfo.text(TextStyleClass.LABEL + "{*top.dynamic_trees.soil_life*} " + TextStyleClass.INFO + Math.floor(life * 100.0F / 15.0F) + "%");
        } else if (block instanceof BlockBranch || block instanceof BlockTrunkShell) {
            Species species = TreeHelper.getBestGuessSpecies(world, data.getPos());
            if (species != Species.NULLSPECIES) {
                probeInfo.text(TextStyleClass.LABEL + "{*top.dynamic_trees.species*} " + TextStyleClass.INFO + species.getLocalizedName());

                IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                horizontalPane.item(species.getSeedStack(1));

                float volume = getTreeVolume(world, blockState, block, data.getPos());
                if (volume > 0.0F) {
                    Species.LogsAndSticks logsAndSticks = species.getLogsAndSticks(volume);
                    if (logsAndSticks.logs > 0) horizontalPane.item(species.getFamily().getPrimitiveLogItemStack(logsAndSticks.logs));
                    if (logsAndSticks.sticks > 0) horizontalPane.item(species.getFamily().getStick(logsAndSticks.sticks));
                }
            }
        }
    }

    private static float getTreeVolume(@Nonnull World world, @Nonnull IBlockState state, @Nonnull Block block, @Nonnull BlockPos pos) {
        // Dereference proxy trunk shell block
        if (block instanceof BlockTrunkShell) {
            BlockTrunkShell.ShellMuse muse = ((BlockTrunkShell) block).getMuse(world, pos);
            if (muse != null) {
                state = muse.state;
                block = state.getBlock();
                pos = muse.pos;
            }
        }

        if (block instanceof BlockBranch) {
            BlockBranch branch = (BlockBranch) block;

            // Analyze only part of the tree beyond the break point and calculate its volume, then destroy the branches
            NodeNetVolume volumeSum = new NodeNetVolume();
            branch.analyse(state, world, pos, null, new MapSignal(volumeSum));

            return volumeSum.getVolume() * ModConfigs.treeHarvestMultiplier;
        }

        return 0.0F;
    }
}

