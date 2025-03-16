package com.wdcftgg.topalldependents.proxy;


import com.wdcftgg.topalldependents.AddonManager;
import com.wdcftgg.topalldependents.event.NameEvent;
import com.wdcftgg.topalldependents.mods.topaddons.config.HelmetConfig;
import com.wdcftgg.topalldependents.mods.topaddons.helmets.LayerChip;
import com.wdcftgg.topalldependents.mods.topaddons.reference.EnumChip;
import com.wdcftgg.topalldependents.mods.topaddons.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static mcjty.theoneprobe.items.ModItems.PROBETAG;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	public ClientProxy() {
	}


	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	public void onInit(){
		super.onInit();
//		MinecraftForge.EVENT_BUS.register(new EventLossSpatialSense());
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new NameEvent());
	}



	public void onPreInit() {
		super.onPreInit();


//		RenderingRegistry.registerEntityRenderingHandler(EntityTimeCrack.class, RenderTimeCrack::new);

//		ClientRegistry.bindTileEntitySpecialRenderer(HourGlassEntity.class, new HourGrassRender());
	}

	public void onPostInit() {
		super.onPostInit();

		Minecraft.getMinecraft().getRenderManager().getSkinMap().forEach((str, renderPlayer) -> {
			renderPlayer.addLayer(new LayerChip());
		});

//		ParticleInit.registerParticle();
	}

	public static List<LayerRenderer<EntityLivingBase>> getLayerRenderers(RenderPlayer instance) {
		return (List)getPrivateValue(RenderLivingBase.class, instance, "layerRenderers");
	}

	private static <T> Object getPrivateValue(Class<T> clazz, T instance, String name) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(clazz, instance, name);
		} catch (Exception var4) {
			return null;
		}
	}

	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Pre event) {
		for (EnumChip enumChip : EnumChip.values()) {
			enumChip.setSprite(event.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "items/" + enumChip.getTexture())));
		}
	}

	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event) {
		if (event.getItemStack().getItem() instanceof ItemArmor && (AddonManager.SPECIAL_HELMETS.containsKey(((ItemArmor) event.getItemStack().getItem()).getClass()) || !HelmetConfig.helmetBlacklistSet.contains(event.getItemStack().getItem().getRegistryName()))) {
			if (event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().getInteger(PROBETAG) == 1) {
				event.getToolTip().add(TextFormatting.AQUA + "Probing");
			}
		}
	}

}
