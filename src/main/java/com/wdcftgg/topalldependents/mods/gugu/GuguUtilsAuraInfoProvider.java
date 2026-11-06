package com.wdcftgg.topalldependents.mods.gugu;

import com.warmthdawn.mod.gugu_utils.modularmachenary.aura.TileAuraHatch;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuguUtilsAuraInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return "topalldependents.gugu_utils.aura";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileAuraHatch) {
            IAuraContainer container = ((TileAuraHatch) tile).getCapability(NaturesAuraAPI.capAuraContainer, null);
            if (container != null) {
                probeInfo.text(Tools.text(TextStyleClass.LABEL, Tools.translate("top.magic.aura_storage"),
                        ": ", TextStyleClass.INFO, container.getStoredAura(), " / ", container.getMaxAura()));
            }
        }
    }
}
