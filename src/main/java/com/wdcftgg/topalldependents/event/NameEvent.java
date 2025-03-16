package com.wdcftgg.topalldependents.event;

import mcjty.theoneprobe.compat.event.SpecialNameEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.shadowmage.ancientwarfare.npc.entity.NpcBase;
import net.shadowmage.ancientwarfare.npc.entity.NpcPlayerOwned;

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
        }
    }
}
