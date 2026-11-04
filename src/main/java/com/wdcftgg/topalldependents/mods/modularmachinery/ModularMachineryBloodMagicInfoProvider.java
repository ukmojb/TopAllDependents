package com.wdcftgg.topalldependents.mods.modularmachinery;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import kport.modularmagic.common.tile.TileLifeEssenceProvider;
import kport.modularmagic.common.tile.TileWillProvider;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ModularMachineryBloodMagicInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return "topalldependents.modularmachinery.bloodmagic";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        Object tile = world.getTileEntity(data.getPos());

        if (tile instanceof TileWillProvider) {
            DecimalFormat format = new DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.ROOT));
            TileWillProvider willProvider = (TileWillProvider) tile;
            for (EnumDemonWillType willType : EnumDemonWillType.values()) {
                ItemStack willStack = new ItemStack(RegistrarBloodMagicItems.MONSTER_SOUL, 1, willType.ordinal());
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().spacing(0))
                        .itemLabel(willStack)
                        .text(": " + TextStyleClass.INFO + format.format(willProvider.getWill(willType)));
            }
        } else if (tile instanceof TileLifeEssenceProvider) {
            TileLifeEssenceProvider lifeEssenceProvider = (TileLifeEssenceProvider) tile;
            int amount = lifeEssenceProvider.getLifeEssenceCache();
            int capacity = lifeEssenceProvider.getOrbCapacity() / 10;
            String storage = capacity > 0 ? amount + " / " + capacity + " LP" : amount + " LP";
            addValue(probeInfo, "life_essence", storage);
        }
    }

    private void addValue(IProbeInfo probeInfo, String key, String value) {
        probeInfo.text(Tools.text(TextStyleClass.LABEL, Tools.translate("topaddons.magic:" + key),
                ": ", TextStyleClass.INFO, value));
    }
}
