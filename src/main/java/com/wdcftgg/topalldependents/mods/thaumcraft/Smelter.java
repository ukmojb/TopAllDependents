package com.wdcftgg.topalldependents.mods.thaumcraft;

import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import thaumcraft.common.tiles.essentia.TileSmelter;

import javax.annotation.Nonnull;

public class Smelter implements IProbeInfoProvider {

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);

    @Override
    public String getID() {
        return "thaumcraft.smelter";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, @Nonnull World world, IBlockState blockState, @Nonnull IProbeHitData data) {
        TileEntity tileEntity = world.getTileEntity(data.getPos());
        if (tileEntity instanceof TileSmelter) {
            NBTTagCompound compound = new NBTTagCompound();
            tileEntity.writeToNBT(compound);
            int totalCookTime = ((TileSmelter) tileEntity).smeltTime;
            int fuelTime = ((TileSmelter) tileEntity).furnaceBurnTime;
            ItemStackHelper.loadAllItems(compound, inventory);
            ItemStack fuel = inventory.get(1);
            ItemStack input = inventory.get(0);
            inventory.clear();

            if (totalCookTime > 0 && !input.isEmpty()) {
                int cookTime = compound.getInteger("CookTime");
                String text;
                int maxProgress = totalCookTime;

                if (maxProgress < 20) {
                    // less than 1 second uses ticks
                    text = String.format(" t / %s t", maxProgress);
                } else {
                    cookTime = Math.round(cookTime / 20.0F);
                    maxProgress = Math.round(maxProgress / 20.0F);
                    text = String.format(" s / %s s", maxProgress);
                }

                // progress info
                probeInfo.progress(cookTime, maxProgress, probeInfo.defaultProgressStyle()
                        .suffix(text)
                        .filledColor(0xFF4E025B)
                        .alternateFilledColor(0xFF4E025B)
                        .backgroundColor(0x00FFFFFF));
            }

            // fuel info
            IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            if (!fuel.isEmpty() || fuelTime != 0) {
                int baseBurnTime = 0;
                if (!fuel.isEmpty()) {
                    horizontalPane.item(fuel);
                    baseBurnTime = TileEntityFurnace.getItemBurnTime(fuel);
                }
                float smeltableAmount = 0.0F;
                float itemSmeltDuration = totalCookTime == 0 ? 200.0F : 1.0F * totalCookTime;
                smeltableAmount += fuelTime / itemSmeltDuration;
                smeltableAmount += baseBurnTime * Math.max(1, fuel.getCount()) / itemSmeltDuration;

                if (smeltableAmount > 0.0F) {
                    horizontalPane.text(TextStyleClass.LABEL + " {*top.furnace_fuel*} " + TextStyleClass.OK + Tools.FORMAT.format(smeltableAmount) + TextStyleClass.LABEL + " {*top.items*}");
                }
            } else if (!input.isEmpty()) {
                horizontalPane.text(TextStyleClass.WARNING + "{*top.no_fuel*}");
            }
        }
    }
}
