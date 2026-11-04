package com.wdcftgg.topalldependents.mods.modularmachinery;

import com.wdcftgg.topalldependents.mods.thaumcraft.AspectElement;
import kport.modularmagic.common.tile.TileAspectProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ModularMachineryAspectInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return "topalldependents.modularmachinery.aspect";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileAspectProvider) {
            AspectList aspects = ((TileAspectProvider) tile).getAspects();
            if (aspects != null) {
                for (Aspect aspect : aspects.getAspectsSortedByName()) {
                    int amount = aspects.getAmount(aspect);
                    if (amount > 0) {
                        probeInfo.element(new AspectElement(aspect, amount));
                    }
                }
            }
        }
    }
}
