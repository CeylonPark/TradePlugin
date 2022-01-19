package com.ceylon.trade.data;

import com.ceylon.trade.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class TradeManager {
    private final Plugin plugin;
    private final Set<TradeData> tradeDataSet = new HashSet<>();
    private final List<TradeSign> tradeSignList = new ArrayList<>();

    public TradeManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void addTradeData(UUID requester, UUID responder) {
        this.tradeDataSet.add(new TradeData(this.plugin, this, requester, responder));
    }

    public void removeTradeData(TradeData tradeData) {
        this.tradeDataSet.remove(tradeData);
    }

    public TradeData getTradeData(UUID uuid) {
        for(TradeData tradeData : this.tradeDataSet) {
            if(tradeData.getRequester().equals(uuid) || tradeData.getResponder().equals(uuid)) {
                return tradeData;
            }
        }
        return null;
    }

    public boolean addTradeSign(UUID registrant, String title, String contents) {
        if(this.getTradeSign(title) == null) {
            this.tradeSignList.add(new TradeSign(registrant, title, contents));
            return true;
        }
        return false;
    }

    public void removeTradeSign(TradeSign tradeSign) {
        this.tradeSignList.remove(tradeSign);
    }

    public TradeSign getTradeSign(String title) {
        for(TradeSign tradeSign : this.tradeSignList) {
            if(tradeSign.getTitle().equals(title)) {
                return tradeSign;
            }
        }
        return null;
    }

    public List<ItemStack> getTradeSignList(int start, int end) {
        List<ItemStack> list = new ArrayList<>();
        if(start <= end && start < this.tradeSignList.size()) {
            for(int i = start; i < Math.min(end, this.tradeSignList.size()); i++) {
                TradeSign tradeSign = this.tradeSignList.get(i);
                ItemStack itemStack = new ItemBuilder(Material.BIRCH_SIGN)
                        .setDisplayName("§f"+tradeSign.getTitle())
                        .addLore("§f")
                        .addLore("§a[등록자] §f: " + this.plugin.getServer().getOfflinePlayer(tradeSign.getRegistrant()).getName())
                        .addLore("§d[내용] §f: " + tradeSign.getContents())
                        .addLore("§f")
                        .build();
                list.add(itemStack);
            }
        }
        return list;
    }

    public List<String> getTradeSignList(UUID registrant) {
        List<String> list = new ArrayList<>();
        for(TradeSign tradeSign : this.tradeSignList) {
            if(tradeSign.getRegistrant().equals(registrant)) {
                list.add(tradeSign.getTitle());
            }
        }
        return list;
    }
}
