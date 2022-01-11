package com.ceylon.trade.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryChecker {
    public static boolean isFullInventory(Inventory inventory) {
        for(ItemStack itemStack : inventory.getStorageContents()) {
            if(itemStack == null) {
                return false;
            }
        }
        return true;
    }
    public static boolean isFullInventory(Player player) {
        return InventoryChecker.isFullInventory(player.getInventory());
    }
    public static boolean isFullInventory(Inventory inventory, ItemStack allowItem) {
        if(allowItem.getAmount() == allowItem.getMaxStackSize()) {
            return InventoryChecker.isFullInventory(inventory);
        }
        for(ItemStack itemStack : inventory.getStorageContents()) {
            if(itemStack == null) {
                return false;
            } else if(itemStack.isSimilar(allowItem) && itemStack.getAmount() != itemStack.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }
    public static boolean isFullInventory(Player player, ItemStack allowItem) {
        return InventoryChecker.isFullInventory(player.getInventory(), allowItem);
    }

    public static boolean hasItemStack(Player player, ItemStack itemStack, int amount) {
        if(amount <= 0) {
            return false;
        }
        for(ItemStack playerItem : player.getInventory().getStorageContents()) {
            if(playerItem != null && itemStack.isSimilar(playerItem)) {
                amount = amount - playerItem.getAmount();
                if(amount <= 0) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean hasItemStack(Player player, ItemStack itemStack) {
        return InventoryChecker.hasItemStack(player, itemStack, itemStack.getAmount());
    }

    public static boolean removeItemStack(Player player, ItemStack itemStack, int amount) {
        if(InventoryChecker.hasItemStack(player, itemStack, amount)) {
            for(ItemStack playerItem : player.getInventory().getStorageContents()) {
                if(playerItem != null && itemStack.isSimilar(playerItem)) {
                    if(playerItem.getAmount() >= amount) {
                        playerItem.setAmount(playerItem.getAmount() - amount);
                        return true;
                    } else {
                        amount = amount - playerItem.getAmount();
                        playerItem.setAmount(0);
                    }
                }
            }
        }
        return false;
    }
    public static boolean removeItemStack(Player player, ItemStack itemStack) {
        return InventoryChecker.removeItemStack(player, itemStack, itemStack.getAmount());
    }
}