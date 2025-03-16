package com.wdcftgg.topalldependents.proxy;


import com.wdcftgg.topalldependents.event.NameEvent;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy extends CommonProxy {

    public ServerProxy() {
    }

    public void onPreInit() {
        super.onPreInit();
    }

    public void onPostInit() {
        super.onPostInit();
    }

    public void onInit(){
        super.onInit();
        MinecraftForge.EVENT_BUS.register(new NameEvent());
    }
}
