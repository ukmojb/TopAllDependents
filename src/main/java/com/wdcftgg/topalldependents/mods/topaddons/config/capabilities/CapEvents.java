package com.wdcftgg.topalldependents.mods.topaddons.config.capabilities;

import com.wdcftgg.topalldependents.TopAllDependents;
import com.wdcftgg.topalldependents.mods.topaddons.config.ConfigClient;
import com.wdcftgg.topalldependents.mods.topaddons.network.MessageClientOptions;
import com.wdcftgg.topalldependents.mods.topaddons.network.PacketHandler;
import com.wdcftgg.topalldependents.mods.topaddons.reference.ElementSync;
import com.wdcftgg.topalldependents.mods.topaddons.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

import static com.wdcftgg.topalldependents.TopAllDependents.OPTS_CAP;


public class CapEvents {

    @SubscribeEvent
    public void onAttachCapabilityEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(Reference.MOD_ID, "options"), new ICapabilityProvider() {
                private IClientOptsCapability instance = OPTS_CAP.getDefaultInstance();

                @Override
                public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
                    return capability == OPTS_CAP;
                }

                @Override
                public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
                    return capability == OPTS_CAP ? OPTS_CAP.<T>cast(this.instance) : null;
                }
            });
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getWorld().isRemote && event.getEntity() == Minecraft.getMinecraft().player) {
            //noinspection VariableUseSideOnly
            PacketHandler.INSTANCE.sendToServer(new MessageClientOptions(ConfigClient.getClientValues(TopAllDependents.configClient), ElementSync.elementIds, (EntityPlayer) event.getEntity()));
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        IClientOptsCapability originalCap = event.getOriginal().getCapability(OPTS_CAP, null);
        event.getEntityPlayer().getCapability(OPTS_CAP, null).setAllOptions(originalCap.getAllOptions());
        event.getEntityPlayer().getCapability(OPTS_CAP, null).setAllElementIds(originalCap.getAllElementIds());
    }
}
