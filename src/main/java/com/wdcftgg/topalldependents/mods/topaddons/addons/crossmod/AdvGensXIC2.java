package com.wdcftgg.topalldependents.mods.topaddons.addons.crossmod;

import com.wdcftgg.topalldependents.mods.topaddons.addons.AddonBlank;
import mcjty.theoneprobe.api.IProbeInfo;
import net.bdew.generators.modules.euOutput.BlockEuOutputBase;
import net.bdew.generators.modules.euOutput.TileEuOutputBase;
import net.bdew.lib.multiblock.block.BlockOutput;
import net.minecraft.tileentity.TileEntity;

import java.text.DecimalFormat;

public final class AdvGensXIC2 {

    public static boolean isEuOutput(BlockOutput blockOutput) {
        return blockOutput instanceof BlockEuOutputBase;

    }

    public static void euOutputInfo(IProbeInfo probeInfo, TileEntity tile) {
        if (tile instanceof TileEuOutputBase) {
            AddonBlank.textPrefixed(probeInfo, "{*topaddons.advgenerators:max_output*}", new DecimalFormat("#.##").format(((TileEuOutputBase) tile).maxOutput()) + " EU/t");
        }
    }
}
