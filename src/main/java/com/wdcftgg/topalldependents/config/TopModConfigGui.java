package com.wdcftgg.topalldependents.config;

import com.google.common.collect.Lists;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;
import java.util.Set;

public class TopModConfigGui extends GuiConfig {

    public TopModConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), TheOneProbe.MODID, false, false, "The One Probe Config");
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = Lists.newArrayList();

        list.add(new ConfigElement(mcjty.theoneprobe.config.Config.mainConfig.getCategory(mcjty.theoneprobe.config.Config.CATEGORY_CLIENT)));
        list.add(new ConfigElement(mcjty.theoneprobe.config.Config.mainConfig.getCategory(mcjty.theoneprobe.config.Config.CATEGORY_PROVIDERS)));
        list.add(new ConfigElement(mcjty.theoneprobe.config.Config.mainConfig.getCategory(Config.CATEGORY_THEONEPROBE)));

        return list;
    }




    public static class TopModConfigGuiFactory implements IModGuiFactory {

        @Override
        public void initialize(Minecraft minecraftInstance) {

        }

        @Override
        public boolean hasConfigGui() {
            return true;
        }

        @Override
        public GuiScreen createConfigGui(GuiScreen parentScreen) {
            return new TopModConfigGui(parentScreen);
        }

        @Override
        public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
            // dead code
            return null;
        }

    }
}
