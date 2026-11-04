package com.wdcftgg.topalldependents.mods.gugu;

import com.warmthdawn.mod.gugu_utils.modularmachenary.starlight.BlockStarightInputHatch;
import com.warmthdawn.mod.gugu_utils.modularmachenary.starlight.TileStarlightInputHatch;
import hellfirepvp.astralsorcery.common.constellation.IConstellation;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuguUtilsInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return "topalldependents.gugu_utils";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());

        if (tile instanceof TileStarlightInputHatch) {
            TileStarlightInputHatch starlightInput = (TileStarlightInputHatch) tile;
            int capacity = world.getBlockState(data.getPos()).getValue(BlockStarightInputHatch.VARIANT).getStarlightMaxStorage();
            addValue(probeInfo, "starlight_storage", starlightInput.getStarlightStored() + " / " + capacity);

            IConstellation constellation = starlightInput.getFocusedConstellation();
            String name = constellation == null
                    ? Tools.translate("topaddons.magic:none").getFormattedText()
                    : Tools.translate(constellation.getUnlocalizedName()).getFormattedText();
            addValue(probeInfo, "constellation", name);
        }
    }

    private void addValue(IProbeInfo probeInfo, String key, String value) {
        String label = Tools.translate("topaddons.magic:" + key).getFormattedText();
        probeInfo.text(TextStyleClass.LABEL + label + ": " + TextStyleClass.INFO + value);
    }
}
