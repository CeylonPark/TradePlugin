package com.ceylon.trade.listener;

import com.ceylon.trade.TradeSignInventoryOpen;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TradeSignInventoryListener implements Listener {
    private final TradeSignInventoryOpen tradeSignInventoryOpen;

    public TradeSignInventoryListener(TradeSignInventoryOpen tradeSignInventoryOpen) {
        this.tradeSignInventoryOpen = tradeSignInventoryOpen;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().contains("장사글")) {
            return;
        }
        event.setCancelled(true);

        int slot = event.getRawSlot();
        if(slot != 45 && slot != 53) {
            return;
        }
        ItemStack itemStack = event.getInventory().getItem(slot);
        if(itemStack == null || itemStack.getType() != Material.PAPER) {
            return;
        }
        int page =  Integer.parseInt(event.getView().getTitle().replace("장사글: ", ""));
        this.tradeSignInventoryOpen.openInventory((Player) event.getWhoClicked(), slot == 45 ? page-1 : page+1);
    }
}
