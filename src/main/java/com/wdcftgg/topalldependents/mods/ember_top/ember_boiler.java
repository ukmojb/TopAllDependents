package com.wdcftgg.topalldependents.mods.ember_top;

import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import teamroots.embers.tileentity.TileEntityBoilerTop;

import java.awt.*;

public class ember_boiler implements IProbeInfoProvider {
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        if (world.getTileEntity(data.getPos()) instanceof TileEntityBoilerTop) {
            TileEntityBoilerTop tile = (TileEntityBoilerTop) world.getTileEntity(data.getPos());
            assert tile != null;
            final int max = (int) tile.capability.getEmberCapacity();
            final int ember = (int) tile.capability.getEmber();
            int orange = Color.ORANGE.getRGB();
            int yellow = Color.yellow.getRGB();
            int white = Color.white.getRGB();
            if (tile.capability.getEmber() > 0) {
                probeInfo.progress(ember, max + 3, new ProgressStyle().prefix("Ember" + ":" + ember).suffix("/" + max)
                        .width(100)
                        .numberFormat(NumberFormat.NONE)
                        .borderColor(yellow)
                        .backgroundColor(white)
                        .filledColor(orange));
            }
        }
    }

    public String getID() {
        return "ember." + this;
    }
}
