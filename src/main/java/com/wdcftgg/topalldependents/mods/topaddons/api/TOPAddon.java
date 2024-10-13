package com.wdcftgg.topalldependents.mods.topaddons.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TOPAddon {

    /**
     * @return The modid of the mod the plugin is written for, will be used to check if the mod is loaded
     */
    String dependency();

    /**
     * @return Optional readable plugin name
     */
    String fancyName() default "";

    /**
     * @return Optional order for registering the info providers etc. (mainly for general
     * information across mods e.g. {@link com.wdcftgg.topalldependents.mods.topaddons.addons.AddonForge})
     *
     * Higher number is lower order.
     */
    int order() default 10;
}
