package com.wdcftgg.topalldependents.mods.botania;


import com.wdcftgg.topalldependents.config.Config;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.IProgressStyle;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import vazkii.botania.common.block.tile.mana.TilePool;

import java.awt.Color;

public class ManaPool implements IProbeInfoProvider {

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());
        if (tile instanceof TilePool) {
            TilePool pool = (TilePool) tile;
            addManaInfo(probeInfo, pool.getCurrentMana(), pool.manaCap);
        }
    }

    public static void addManaInfo(IProbeInfo probeInfo, int mana, int capacity) {
        if (Config.showBotaniaprogress) {
            int manaColor = new Color(39, 255, 247).getRGB();
            IProgressStyle style = new ProgressStyle()
                    .width(110)
                    .numberFormat(NumberFormat.NONE)
                    .borderColor(Color.white.getRGB())
                    .backgroundColor(Color.gray.getRGB())
                    .filledColor(manaColor)
                    .alternateFilledColor(manaColor);
            if (Config.textinprogress) {
                style.prefix(Tools.translate("top.magic.mana", mana)).suffix("/" + capacity);
            }
            probeInfo.progress(mana, capacity, style);
        }
        if (!Config.showBotaniaprogress || !Config.textinprogress) {
            probeInfo.text(Tools.translate("top.magic.mana", mana + "/" + capacity));
        }
    }

    @Override
    public String getID() {
        return "botania.pool";
    }
}
