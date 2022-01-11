package com.ceylon.trade.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;
    private List<String> lore = new ArrayList<>();

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = this.itemStack.getItemMeta();
    }
    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();
    }
    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = new ItemStack(itemStack);
        this.itemMeta = this.itemStack.getItemMeta();
        if(this.itemMeta.hasLore()) {
            this.lore = this.itemMeta.getLore();
        }
    }

    public ItemBuilder setDisplayName(String s) {
        this.itemMeta.setDisplayName(s);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder addLore(String s) {
        this.lore.add(s);

        return this;
    }

    public ItemBuilder setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        this.itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemStack build() {
        this.itemMeta.setLore(this.lore);
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}
