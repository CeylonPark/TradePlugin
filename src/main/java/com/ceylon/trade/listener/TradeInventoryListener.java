package com.ceylon.trade.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class TradeInventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        // 교환 ?
        // 복벅 방지를
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        // 싸가지 없이 교환 창 닫을경우 처리
    }


    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        //
    }

}
