package com.wdcftgg.topalldependents.mods.modularmachinery;

import kport.modularmagic.common.tile.TileAuraProvider;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ModularMachineryAuraInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return "topalldependents.modularmachinery.aura";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileAuraProvider) {
            String label = Tools.translate("topaddons.magic:aura").getFormattedText();
            int amount = ((TileAuraProvider) tile).getAura().getAmount();
            probeInfo.text(TextStyleClass.LABEL + label + ": " + TextStyleClass.INFO + amount);
        }
    }
}
