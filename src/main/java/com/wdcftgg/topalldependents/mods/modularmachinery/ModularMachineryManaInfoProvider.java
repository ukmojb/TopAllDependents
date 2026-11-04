package com.wdcftgg.topalldependents.mods.modularmachinery;

import com.wdcftgg.topalldependents.config.Config;
import com.wdcftgg.topalldependents.mods.botania.ManaPool;
import kport.modularmagic.common.tile.TileManaProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ModularMachineryManaInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return "topalldependents.modularmachinery.mana";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileManaProvider && Config.Botaniatop) {
            TileManaProvider manaProvider = (TileManaProvider) tile;
            ManaPool.addManaInfo(probeInfo, manaProvider.getCurrentMana(), manaProvider.getManaCapacity());
        }
    }
}
