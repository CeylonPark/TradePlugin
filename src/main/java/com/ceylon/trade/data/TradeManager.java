package com.ceylon.trade.data;

import org.bukkit.plugin.Plugin;

import java.util.*;

public class TradeManager {
    private final Plugin plugin;
    private final Set<TradeData> tradeDataSet = new HashSet<>();

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
}
