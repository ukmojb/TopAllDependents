package com.wdcftgg.topalldependents.mods.BotanicAdditions;

import com.wdcftgg.topalldependents.mods.botania.ManaPool;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tk.zeitheron.botanicadds.blocks.tiles.TileElvenAltar;

public class ElvenAltar implements IProbeInfoProvider {
    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileElvenAltar) {
            TileElvenAltar altar = (TileElvenAltar) tile;
            ManaPool.addManaInfo(probeInfo, (int) altar.getCurrentMana(), (int) altar.getTargetMana());
        }
    }

    @Override
    public String getID() {
        return "botanic_additions.ElvenAltar";
    }
}
