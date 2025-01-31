package com.wdcftgg.topalldependents.mods.topaddons;

import com.google.common.base.Function;
import com.wdcftgg.topalldependents.AddonManager;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import javax.annotation.Nullable;

public class TOPRegistrar {

    private static boolean registered;

    public static void register() {
        if (registered)
            return;
        registered = true;

        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.wdcftgg.topalldependents.mods.topaddons.TOPRegistrar$GetTheOneProbe");
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {

        public static ITheOneProbe probe;

        @Nullable
        @Override
        public Void apply(ITheOneProbe theOneProbe) {
            probe = theOneProbe;
            AddonManager.ADDONS.forEach(addon -> {
                probe.registerProvider(addon);
                probe.registerProbeConfigProvider(addon);
                probe.registerEntityProvider(addon);
                addon.getBlockDisplayOverrides().forEach(probe::registerBlockDisplayOverride);
                addon.getEntityDisplayOverrides().forEach(probe::registerEntityDisplayOverride);
                addon.registerElements();
                addon.addFluidColors();
                addon.addTankNames();
            });

            return null;
        }
    }
}
