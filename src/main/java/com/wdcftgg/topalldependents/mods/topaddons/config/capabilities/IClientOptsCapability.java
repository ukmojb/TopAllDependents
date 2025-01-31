package com.wdcftgg.topalldependents.mods.topaddons.config.capabilities;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * A {@link net.minecraftforge.common.capabilities.Capability} linked to an
 * {@link net.minecraft.entity.player.EntityPlayer} that stores player-specific options i.e.
 * turning off functionality, modifying behaviour/appearance.
 * This approach is necessary because TheOneProbe does all its logic for what to display on the
 * server-side.
 * v1.11.2-0.14.0: Now also includes custom element ids
 */
public interface IClientOptsCapability {

    /* Client option sutff */

    boolean getBoolean(String option);

    int getInt(String option);

    void setAllOptions(@Nonnull Map<String, Integer> options);

    Map<String, Integer> getAllOptions();

    /* Element id stuff */

    int getElementId(String name);

    void setAllElementIds(@Nonnull Map<String, Integer> elementIds);

    Map<String, Integer> getAllElementIds();
}
