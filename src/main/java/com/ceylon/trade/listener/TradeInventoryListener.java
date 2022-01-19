package com.ceylon.trade.listener;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.data.TradeData;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.InventoryUtil;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TradeInventoryListener implements Listener {
    private static final List<Integer> REQUESTER_PERMIT_SLOT = Arrays.asList(0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30);
    private static final List<Integer> RESPONDER_PERMIT_SLOT = Arrays.asList(5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35);
    private final TradeManager tradeManager;

    public TradeInventoryListener(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) { // 47, 51
        if(!event.getView().getTitle().contains("교환")) {
            return;
        }
        UUID playerUUID = event.getWhoClicked().getUniqueId();
        TradeData tradeData = this.tradeManager.getTradeData(playerUUID);
        if(tradeData == null) {
            return;
        }
        int type = tradeData.getRequester().equals(playerUUID) ? 0 : 1; // 0: requester, 1: responder
        int slot = event.getRawSlot();
        if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || (slot < 54 && (type == 0 ? !REQUESTER_PERMIT_SLOT.contains(slot) : !RESPONDER_PERMIT_SLOT.contains(slot)))) {
            event.setCancelled(true);
        }

        ItemStack itemStack = event.getInventory().getItem(type == 0 ? 47 : 51);
        if(itemStack == null) {
            event.setCancelled(true);
            return;
        }
        if(itemStack.getType() == Material.LIME_CONCRETE) {
            event.setCancelled(true);
        }

        if(type == 0 ? slot != 47 : slot != 51) {
            return;
        }

        if(itemStack.getType() == Material.LIME_CONCRETE) {
            event.getInventory().setItem(type == 0 ? 47 : 51, new ItemStack(Material.RED_CONCRETE));
            return;
        }
        ItemStack itemStack2 = event.getInventory().getItem(type == 0 ? 51 : 47);
        if(itemStack2 == null) {
            return;
        }
        if(itemStack2.getType() == Material.LIME_CONCRETE) {
            tradeData.deleteTradeData();
            Player requester = Bukkit.getPlayer(tradeData.getRequester());
            Player responder = Bukkit.getPlayer(tradeData.getResponder());
            type = 0;
            Inventory inv = event.getInventory();
            for(Player player : new Player[]{requester, responder}) {
                player.closeInventory();
                for(int invSlot : type == 0 ? REQUESTER_PERMIT_SLOT : RESPONDER_PERMIT_SLOT) {
                    ItemStack item = inv.getItem(invSlot);
                    if (item == null) {
                        continue;
                    }
                    if(type == 0) {
                        InventoryUtil.giveItemOrDrop(responder, item);
                    } else {
                        InventoryUtil.giveItemOrDrop(requester, item);
                    }
                }
                type++;
            }
        } else {
            event.getInventory().setItem(type == 0 ? 47 : 51, new ItemStack(Material.LIME_CONCRETE));
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if(!event.getView().getTitle().contains("교환")) {
            return;
        }

        TradeData tradeData = this.tradeManager.getTradeData(event.getPlayer().getUniqueId());
        if(tradeData == null) {
            return;
        }
        tradeData.deleteTradeData();

        Player[] players = new Player[] {
                Bukkit.getPlayer(tradeData.getRequester()),
                Bukkit.getPlayer(tradeData.getResponder())
        };

        for(int i = 0; i < players.length; i++) {
            Player player = players[i];
            if(player == null) {
                continue;
            }
            player.closeInventory();
            for(int slot : i == 0 ? REQUESTER_PERMIT_SLOT : RESPONDER_PERMIT_SLOT) {
                ItemStack itemStack = event.getInventory().getItem(slot);
                if(itemStack == null) {
                    continue;
                }
                InventoryUtil.giveItemOrDrop(player, itemStack);
            }
            MsgUtil.sendMsg(player, TradePlugin.prefix + "§c교환이 취소 되었습니다.");
        }
    }


    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if(!event.getView().getTitle().contains("교환")) {
            return;
        }
        event.setCancelled(true);
        // 허용 구간 허용하기 craft 참고.
    }

}
