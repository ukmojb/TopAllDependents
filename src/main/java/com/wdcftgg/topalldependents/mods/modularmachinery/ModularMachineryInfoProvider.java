package com.wdcftgg.topalldependents.mods.modularmachinery;

import hellfirepvp.astralsorcery.common.constellation.IConstellation;
import hellfirepvp.astralsorcery.common.constellation.distribution.ConstellationSkyHandler;
import hellfirepvp.astralsorcery.common.constellation.distribution.WorldSkyHandler;
import kport.modularmagic.common.tile.TileConstellationProvider;
import kport.modularmagic.common.tile.TileStarlightInput;
import kport.modularmagic.common.tile.TileStarlightOutput;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;
import java.util.StringJoiner;

public class ModularMachineryInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return "topalldependents.modularmachinery";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());

        if (tile instanceof TileStarlightInput) {
            addValue(probeInfo, "starlight", Integer.toString(((TileStarlightInput) tile).getStarlightStored()));
        } else if (tile instanceof TileStarlightOutput) {
            TileStarlightOutput starlightOutput = (TileStarlightOutput) tile;
            addValue(probeInfo, "starlight", Float.toString(starlightOutput.getStarlightProduced()));
            IConstellation constellation = starlightOutput.constellation;
            String name = constellation == null
                    ? Tools.translate("topaddons.magic:none").getFormattedText()
                    : Tools.translate(constellation.getUnlocalizedName()).getFormattedText();
            addValue(probeInfo, "constellation", name);
        } else if (tile instanceof TileConstellationProvider) {
            ConstellationSkyHandler constellationSkyHandler = ConstellationSkyHandler.getInstance();
            WorldSkyHandler skyHandler = constellationSkyHandler == null
                    ? null
                    : constellationSkyHandler.getWorldHandler(world);
            List<IConstellation> constellations = skyHandler == null
                    ? null
                    : skyHandler.getSortedActiveConstellations();
            if (constellations == null || constellations.isEmpty()) {
                addValue(probeInfo, "active_constellations", Tools.translate("topaddons.magic:none").getFormattedText());
            } else {
                StringJoiner names = new StringJoiner(", ");
                for (IConstellation constellation : constellations) {
                    if (constellation != null && constellation.getUnlocalizedName() != null) {
                        names.add(Tools.translate(constellation.getUnlocalizedName()).getFormattedText());
                    }
                }
                String activeConstellations = names.length() == 0
                        ? Tools.translate("topaddons.magic:none").getFormattedText()
                        : names.toString();
                addValue(probeInfo, "active_constellations", activeConstellations);
            }
        }
    }

    private void addValue(IProbeInfo probeInfo, String key, String value) {
        String label = Tools.translate("topaddons.magic:" + key).getFormattedText();
        probeInfo.text(TextStyleClass.LABEL + label + ": " + TextStyleClass.INFO + value);
    }
}
