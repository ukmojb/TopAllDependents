package com.wdcftgg.topalldependents.mods.modularmachinery;

import hellfirepvp.astralsorcery.common.constellation.IConstellation;
import mcjty.theoneprobe.Tools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;

public final class ConstellationText {

    private ConstellationText() {
    }

    public static ITextComponent translate(IConstellation constellation) {
        if (constellation == null) {
            return Tools.translate("top.magic.none");
        }

        // NBT 保留星座的完整语言 key，避免不同 Astral Sorcery 版本的名称方法差异。
        NBTTagCompound data = new NBTTagCompound();
        constellation.writeToNBT(data);
        String key = data.getString(IConstellation.getDefaultSaveKey());
        return Tools.translate(key);
    }
}
