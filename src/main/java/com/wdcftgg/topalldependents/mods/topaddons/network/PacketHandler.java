package com.wdcftgg.topalldependents.mods.topaddons.network;

import com.wdcftgg.topalldependents.mods.topaddons.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    public static void init() {
        INSTANCE.registerMessage(MessageClientOptions.class, MessageClientOptions.class, 0, Side.SERVER);
    }
}
