package com.wdcftgg.topalldependents.mods.projecte;

import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.*;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.api.proxy.IEMCProxy;
import moze_intel.projecte.api.tile.IEmcStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ProjectEInfoProvider implements IProbeInfoProvider, IProbeInfoEntityProvider {

    private static final Item REDSTONE = Items.REDSTONE;
    private static final Item PAINTING = Items.PAINTING;
    private static final Item MINECART = Items.MINECART;
    private static final Item MINECART_CHEST = Items.CHEST_MINECART;
    private static final Item MINECART_HOPPER = Items.HOPPER_MINECART;
    private static final Item MINECART_COMMAND_BLOCK = Items.COMMAND_BLOCK_MINECART;
    private static final Item MINECART_FURNACE = Items.FURNACE_MINECART;
    private static final Item MINECART_TNT = Items.TNT_MINECART;

    @Override
    public String getID() {
        return "random.projecte";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, @Nonnull EntityPlayer player, World world, IBlockState blockState, @Nonnull IProbeHitData data) {
        if (!player.isSneaking()) return;
        long emc = getEMC(blockState, world, data.getPos());
        if (emc > 0) probeInfo.text(TextFormatting.YELLOW + "{*top.projecte.emc*} " + Tools.FORMAT.format(emc));

        TileEntity tileEntity = world.getTileEntity(data.getPos());
        if (tileEntity instanceof IEmcStorage) {
            IEmcStorage emcStorage = (IEmcStorage) tileEntity;
            probeInfo.text(TextStyleClass.LABEL + "{*top.projecte.contained_emc*} " + Tools.FORMAT.format(emcStorage.getStoredEmc()));
        }
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, @Nonnull EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {
        if (!player.isSneaking()) return;
        long emc = getEMC(entity);
        if (emc > 0) probeInfo.text(TextFormatting.YELLOW + "{*top.projecte.emc*} " + Tools.FORMAT.format(emc));
    }

    private static long getEMC(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos) {
        Block block = state.getBlock();
        int meta = block.getMetaFromState(state);

        IEMCProxy proxy = ProjectEAPI.getEMCProxy();
        long emc = proxy.getValue(new ItemStack(block, 1, meta));
        if (emc <= 0) {
            if (block == Blocks.REDSTONE_WIRE) return proxy.getValue(REDSTONE);
            if (block instanceof BlockBush) return proxy.getValue(block.getItem(world, pos, state));
            if (block instanceof BlockDoor) return proxy.getValue(block.getItem(world, pos, state));
            return proxy.getValue(new ItemStack(block, 1, meta));
        }
        return emc;
    }

    private static long getEMC(@Nonnull Entity entity) {
        IEMCProxy proxy = ProjectEAPI.getEMCProxy();
        if (entity instanceof EntityPainting) return proxy.getValue(PAINTING);
        if (entity instanceof EntityItemFrame) return proxy.getValue(((EntityItemFrame) entity).getDisplayedItem());
        if (entity instanceof EntityBoat) return proxy.getValue(((EntityBoat) entity).getItemBoat());
        if (entity instanceof EntityMinecart) {
            if (entity instanceof EntityMinecartEmpty) return proxy.getValue(MINECART);
            if (entity instanceof EntityMinecartChest) return proxy.getValue(MINECART_CHEST);
            if (entity instanceof EntityMinecartHopper) return proxy.getValue(MINECART_HOPPER);
            if (entity instanceof EntityMinecartCommandBlock) return proxy.getValue(MINECART_COMMAND_BLOCK);
            if (entity instanceof EntityMinecartFurnace) return proxy.getValue(MINECART_FURNACE);
            if (entity instanceof EntityMinecartTNT) return proxy.getValue(MINECART_TNT);
        }
        return 0;
    }
}

