package com.wdcftgg.topalldependents.mods.botaniverse;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/12/16 18:48
 */

import com.aeternal.botaniverse.common.block.tile.TileMoreSpreader;
import com.wdcftgg.topalldependents.config.Config;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.awt.*;

public class MoreSpreader implements IProbeInfoProvider {

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        if (world.getTileEntity(data.getPos()) instanceof TileMoreSpreader) {
            TileMoreSpreader tile = (TileMoreSpreader) world.getTileEntity(data.getPos());

            final int mana = (int) tile.getCurrentMana();
            final int manamax = (int) tile.getMaxMana();
            int light_bule = new Color(39, 255, 247).getRGB();
            int gray = Color.gray.getRGB();
            int white = Color.white.getRGB();
            if (Config.showBotaniaprogress) {
                if (Config.textinprogress) {
                    probeInfo.progress(mana, manamax, new ProgressStyle()
                            .prefix("Mana" + ":" + mana).suffix("/" + manamax)
                            .width(110)
                            .numberFormat(NumberFormat.NONE)
                            .borderColor(white)
                            .backgroundColor(gray)
                            .filledColor(light_bule)
                            .alternateFilledColor(light_bule));
                } else {
                    probeInfo.progress(mana, manamax, new ProgressStyle()
                            .width(110)
                            .numberFormat(NumberFormat.NONE)
                            .borderColor(white)
                            .backgroundColor(gray)
                            .filledColor(light_bule)
                            .alternateFilledColor(light_bule));
                    probeInfo.text("Mana:" + mana + "/" + manamax);
                }

            } else {
                probeInfo.text("Mana:" + mana + "/" + manamax);
            }
        }
    }

    @Override
    public String getID() {
        return "botaniverse.Spreader";
    }
}
