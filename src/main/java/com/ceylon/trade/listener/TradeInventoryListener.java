package com.ceylon.trade.listener;

import com.ceylon.trade.data.TradeData;
import com.ceylon.trade.data.TradeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
        // 47, 51 클릭 시 이벤트 처리, 블럭 바뀌거나 조건 만족하면 교환하기, 만약 플레이어 인벤에 가득찼으면 교환 취소
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if(!event.getView().getTitle().contains("교환")) {
            return;
        }
        // 교환중인지 확인하고 교환 취소.
        // 교환창에 있던 아이템 반납. 가득 찼으면 바닥에 떨구기
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
