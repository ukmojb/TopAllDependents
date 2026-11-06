package com.wdcftgg.topalldependents.mods.ExtraBotany;

import com.meteor.extrabotany.common.block.tile.TileQuantumManaBuffer;
import com.wdcftgg.topalldependents.mods.botania.ManaPool;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class QuantumManaBuffer implements IProbeInfoProvider {

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileQuantumManaBuffer) {
            TileQuantumManaBuffer buffer = (TileQuantumManaBuffer) tile;
            ManaPool.addManaInfo(probeInfo, (int) buffer.getCurrentMana(), (int) buffer.getMaxMana());
        }
    }

    @Override
    public String getID() {
        return "extrabotania.QuantumManaBuffer";
    }
}
