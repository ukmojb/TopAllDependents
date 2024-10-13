package com.wdcftgg.topalldependents.mods.topaddons.helmets;

import com.wdcftgg.topalldependents.TopAllDependents;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import com.wdcftgg.topalldependents.AddonManager;
import com.wdcftgg.topalldependents.mods.topaddons.TOPAddons;
import com.wdcftgg.topalldependents.mods.topaddons.config.HelmetConfig;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CommandTOPHelmet implements ICommand {

    @Nonnull
    @Override
    public String getName() {
        return "tophelmet";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/tophelmet blacklist <add/remove>";
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("tophelmet", "th");
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (!HelmetConfig.allHelmetsProbable) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + TextFormatting.ITALIC.toString() + "AllHelmetsProbable config is set to false"));
            return;
        }

        if (args.length < 2) return;

        if ("blacklist".equals(args[0]) && ("add".equals(args[1]) || "remove".equals(args[1]))) {
            if (sender instanceof EntityPlayer) {
                ItemStack stack = (((EntityPlayer) sender).getHeldItemMainhand());
                if (!(stack.getItem() instanceof ItemArmor)) {
                    sender.sendMessage(new TextComponentString(TextFormatting.RED + "Hold a helmet in your main hand to add/remove it to the blacklist"));
                    return;
                }

                ResourceLocation loc = stack.getItem().getRegistryName();
                if ("add".equals(args[1])) {
                    if (AddonManager.SPECIAL_HELMETS.containsKey(((ItemArmor)stack.getItem()).getClass())) {
                        sender.sendMessage(new TextComponentString(TextFormatting.RED + "This helmet cannot be added to the blacklist"));
                        return;
                    }

                    if (HelmetConfig.helmetBlacklistSet.contains(loc)) {
                        sender.sendMessage(new TextComponentString(TextFormatting.RED + "This helmet is already on the blacklist"));
                    } else {
                        HelmetConfig.helmetBlacklistSet.add(loc);
                        sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Helmet was added to the blacklist"));
                    }
                } else {
                    if (HelmetConfig.helmetBlacklistSet.contains(loc)) {
                        HelmetConfig.helmetBlacklistSet.remove(loc);
                        sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Helmet was removed from the blacklist"));
                    } else {
                        sender.sendMessage(new TextComponentString(TextFormatting.RED + "This helmet is not on the blacklist"));
                    }
                }

                HelmetConfig.saveHelmetBlacklist(TopAllDependents.config);
            }
        }
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return sender.canUseCommand(4, this.getName());
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos pos) {
        List<String> opts = new ArrayList<>();
        if (args.length == 1) {
            opts.add("blacklist");
        } else if (args.length == 2 && "blacklist".equals(args[0])) {
            opts.add("add");
            opts.add("remove");
        }

        return opts;
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return getName().compareTo(o.getName());
    }
}
