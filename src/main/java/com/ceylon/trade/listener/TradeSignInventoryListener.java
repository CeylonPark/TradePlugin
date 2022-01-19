package com.ceylon.trade.listener;

import com.ceylon.trade.data.TradeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TradeSignInventoryListener implements Listener {
    private final TradeManager tradeManager;

    public TradeSignInventoryListener(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().contains("장사글")) {
            return;
        }
        event.setCancelled(true);
    }
}
