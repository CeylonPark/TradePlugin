package com.ceylon.trade.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MsgUtil {
    public static void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage(msg);
    }
    public static void sendMsg(CommandSender sender, String... msg) {
        sender.sendMessage(msg);
    }
    public static void sendColorMsg(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
    public static void sendColorMsg(CommandSender sender, char altColorChar, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes(altColorChar, msg));
    }

    public static void sendMsg(Player player, String msg) {
        player.sendMessage(msg);
    }
    public static void sendMsg(Player player, String... msg) {
        player.sendMessage(msg);
    }
    public static void sendColorMsg(Player player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
    public static void sendColorMsg(Player player, char altColorChar, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes(altColorChar, msg));
    }

    public static void sendMsg(OfflinePlayer offlinePlayer, String msg) {
        if(offlinePlayer.isOnline()) {
            offlinePlayer.getPlayer().sendMessage(msg);
        }
    }
    public static void sendMsg(OfflinePlayer offlinePlayer, String... msg) {
        if(offlinePlayer.isOnline()) {
            offlinePlayer.getPlayer().sendMessage(msg);
        }
    }
    public static void sendColorMsg(OfflinePlayer offlinePlayer, String msg) {
        if(offlinePlayer.isOnline()) {
            offlinePlayer.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void sendColorMsg(OfflinePlayer offlinePlayer, char altColorChar, String msg) {
        if(offlinePlayer.isOnline()) {
            offlinePlayer.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes(altColorChar, msg));
        }
    }

    public static void sendMsg(UUID uuid, String msg) {
        Player player = Bukkit.getPlayer(uuid);
        if(player != null) {
            MsgUtil.sendMsg(player, msg);
        }
    }
    public static void sendMsg(UUID uuid, String... msg) {
        Player player = Bukkit.getPlayer(uuid);
        if(player != null) {
            MsgUtil.sendMsg(player, msg);
        }
    }
}
