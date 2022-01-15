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

    public boolean containTradeData(UUID uuid) {
        return this.isRequester(uuid) || this.isResponder(uuid);
    }

    public boolean isRequester(UUID requester) {
        return this.getResponder(requester) != null;
    }

    public boolean isResponder(UUID responder) {
        return this.getRequester(responder) != null;
    }

    public UUID getRequester(UUID responder) {
        for(TradeData tradeData : this.tradeDataSet) {
            if(tradeData.getResponder().equals(responder)) {
                return tradeData.getRequester();
            }
        }
        return null;
    }

    public UUID getResponder(UUID requester) {
        for(TradeData tradeData : this.tradeDataSet) {
            if(tradeData.getRequester().equals(requester)) {
                return tradeData.getResponder();
            }
        }
        return null;
    }
}
