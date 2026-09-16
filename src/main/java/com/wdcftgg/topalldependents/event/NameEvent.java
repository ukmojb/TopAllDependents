package com.wdcftgg.topalldependents.event;

import mcjty.theoneprobe.compat.event.SpecialNameEvent;
import com.setycz.chickens.entity.EntityChickensChicken;
import com.setycz.chickens.registry.ChickensRegistry;
import com.setycz.chickens.registry.ChickensRegistryItem;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.shadowmage.ancientwarfare.npc.entity.NpcBase;

@Mod.EventBusSubscriber
public class NameEvent {

    @SubscribeEvent
    public void onSpecialName(SpecialNameEvent event) {
        if (event.getEntity() != null) {
            Entity entity = event.getEntity();

            if (Loader.isModLoaded("ancientwarfare")) {
                if (entity instanceof NpcBase) {
                    NpcBase npc = (NpcBase) entity;
                    event.setSpacialName("ancientwarfarenpc." + npc.getNpcFullType());
                }
            }

            if (Loader.isModLoaded("chickens") && entity instanceof EntityChickensChicken) {
                EntityChickensChicken chicken = (EntityChickensChicken) entity;
                NBTTagCompound chickenData = new NBTTagCompound();
                chicken.writeEntityToNBT(chickenData);
                ChickensRegistryItem chickenDescription = ChickensRegistry.getByRegistryName(chickenData.getString("Type"));
                if (chickenDescription != null) {
                    event.setSpacialName(chickenDescription.getEntityName());
                }
            }
        }
    }
}
