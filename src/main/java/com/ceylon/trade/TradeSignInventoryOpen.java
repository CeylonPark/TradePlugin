package com.ceylon.trade;

import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TradeSignInventoryOpen {
    private final TradeManager tradeManager;

    public TradeSignInventoryOpen(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }

    public void openInventory(Player player, int page) {
        Inventory inv = Bukkit.createInventory(player, 54, "장사글: "+page);

        List<ItemStack> list = this.tradeManager.getTradeSignList(45*(page-1), (44*page)+page-1);
        for(int i = 0; i < list.size(); i++) {
            inv.setItem(i, list.get(i));
        }

        if(page == 1) {
            inv.setItem(45, new ItemBuilder(Material.BARRIER)
                    .setDisplayName("§f이전 페이지")
                    .addLore("§c존재하지 않음!")
                    .build());
        } else {
            inv.setItem(45, new ItemBuilder(Material.PAPER)
                    .setDisplayName("§f이전 페이지")
                    .addLore("§f클릭 시 이전 페이지로 이동합니다!")
                    .build());
        }
        if(this.tradeManager.getTradeSignListSize() > 45*page) {
            inv.setItem(53, new ItemBuilder(Material.PAPER)
                    .setDisplayName("§f다음 페이지")
                    .addLore("§f클릭 시 다음 페이지로 이동합니다!")
                    .build());
        } else {
            inv.setItem(53, new ItemBuilder(Material.BARRIER)
                    .setDisplayName("§f다음 페이지")
                    .addLore("§c존재하지 않음!")
                    .build());
        }
        player.openInventory(inv);
    }
}
