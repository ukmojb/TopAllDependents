package com.wdcftgg.topalldependents.mods.botaniverse;

import com.aeternal.botaniverse.blocks.tiles.TileMorePool;
import com.wdcftgg.topalldependents.mods.botania.ManaPool;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MoreManaPool implements IProbeInfoProvider {

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileMorePool) {
            TileMorePool pool = (TileMorePool) tile;
            ManaPool.addManaInfo(probeInfo, (int) pool.getCurrentMana(), (int) pool.manaCap);
        }
    }

    @Override
    public String getID() {
        return "botaniverse.pool";
    }
}
