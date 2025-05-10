package com.wdcftgg.topalldependents.mods.thaumcraft;

import com.wdcftgg.topalldependents.config.Config;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.items.IGogglesDisplayExtended;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.tiles.devices.TileArcaneEar;

import java.util.ArrayList;
import java.util.List;

public class ThaumHighlightInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return TheOneProbe.MODID + ":thaum_highlight";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo,
                             EntityPlayer player, World world, IBlockState state, IProbeHitData data) {
        if (Config.requireGoggles && !EntityUtils.hasGoggles(player)) {
            return;
        }
        TileEntity tile = world.getTileEntity(data.getPos());
        boolean genericInfoAdded = false;

        if (tile instanceof IGogglesDisplayExtended) {
            for (String line : ((IGogglesDisplayExtended)tile).getIGogglesText()) {
                probeInfo.text(line);
            }
            genericInfoAdded = true;
        } else {
            Block block = state.getBlock();
            if (block instanceof IGogglesDisplayExtended) {
                for (String line : ((IGogglesDisplayExtended)block).getIGogglesText()) {
                    probeInfo.text(line);
                }
                genericInfoAdded = true;
            }
        }

        if (tile instanceof IAspectContainer) {
            AspectList aspects = ((IAspectContainer)tile).getAspects();
            if (aspects != null && aspects.size() > 0) {
                List<Aspect> aspectList = new ArrayList<>();
                for (Aspect aspect : aspects.getAspectsSortedByName()) {

                    if (aspectList.size() < 5) {
                        aspectList.add(aspect);
                    } else {

                        int space = 0;

                        for (Aspect aspect1 : aspectList) {
                            space = Math.max(space, 5 + String.valueOf(aspects.getAmount(aspect1)).length());
                        }

                        IProbeInfo aspectInfo = probeInfo.horizontal(probeInfo.defaultLayoutStyle()
                                .alignment(ElementAlignment.ALIGN_TOPLEFT)
                                .spacing(space));

                        for (Aspect aspect1 : aspectList) {
                            aspectInfo.element(new AspectElement(aspect1, aspects.getAmount(aspect1)));
                        }

                        aspectList.clear();

                    }
                }
            }
            genericInfoAdded = true;
        }

        if (genericInfoAdded) { // tile-specific info below here
            return;
        }

        if (tile instanceof TileEntityNote) {
            probeInfo.text("Note: " + ((TileEntityNote)tile).note);
            return;
        }

        if (tile instanceof TileArcaneEar) {
            probeInfo.text("Note: " + ((TileArcaneEar)tile).note);
        }
    }

}
