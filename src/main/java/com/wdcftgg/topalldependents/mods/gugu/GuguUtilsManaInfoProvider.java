package com.wdcftgg.topalldependents.mods.gugu;

import com.warmthdawn.mod.gugu_utils.modularmachenary.mana.TileSparkManaHatch;
import com.wdcftgg.topalldependents.config.Config;
import com.wdcftgg.topalldependents.mods.botania.ManaPool;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuguUtilsManaInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return "topalldependents.gugu_utils.mana";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileSparkManaHatch && Config.Botaniatop) {
            TileSparkManaHatch manaHatch = (TileSparkManaHatch) tile;
            ManaPool.addManaInfo(probeInfo, manaHatch.getCurrentMana(), TileSparkManaHatch.MAX_MANA);
        }
    }
}
