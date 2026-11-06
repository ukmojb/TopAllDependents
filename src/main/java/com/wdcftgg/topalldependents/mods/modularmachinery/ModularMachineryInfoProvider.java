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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.List;

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
            addValue(probeInfo, "constellation", ConstellationText.translate(starlightOutput.constellation));
        } else if (tile instanceof TileConstellationProvider) {
            ConstellationSkyHandler constellationSkyHandler = ConstellationSkyHandler.getInstance();
            WorldSkyHandler skyHandler = constellationSkyHandler == null
                    ? null
                    : constellationSkyHandler.getWorldHandler(world);
            List<IConstellation> constellations = skyHandler == null
                    ? null
                    : skyHandler.getSortedActiveConstellations();
            if (constellations == null || constellations.isEmpty()) {
                addValue(probeInfo, "active_constellations", Tools.translate("top.magic.none"));
            } else {
                ITextComponent names = new TextComponentString("");
                for (IConstellation constellation : constellations) {
                    if (constellation != null) {
                        if (!names.getSiblings().isEmpty()) {
                            names.appendText(", ");
                        }
                        names.appendSibling(ConstellationText.translate(constellation));
                    }
                }
                ITextComponent activeConstellations = names.getSiblings().isEmpty()
                        ? Tools.translate("top.magic.none")
                        : names;
                addValue(probeInfo, "active_constellations", activeConstellations);
            }
        }
    }

    private void addValue(IProbeInfo probeInfo, String key, Object value) {
        probeInfo.text(Tools.text(TextStyleClass.LABEL, Tools.translate("top.magic." + key),
                ": ", TextStyleClass.INFO, value));
    }
}
