package com.wdcftgg.topalldependents.mods.botania;


import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import vazkii.botania.common.block.tile.TileRuneAltar;

public class RuneAltar implements IProbeInfoProvider {

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

        Object tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileRuneAltar) {
            TileRuneAltar altar = (TileRuneAltar) tile;
            ManaPool.addManaInfo(probeInfo, altar.getCurrentMana(), altar.getTargetMana());
        }
    }

    @Override
    public String getID() {
        return "botania.RuneAltar";
    }
}
