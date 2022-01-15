package com.ceylon.trade.data;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class TradeData {
    private final TradeManager tradeManager;
    private final UUID requester;
    private final UUID responder;
    private boolean state;
    private BukkitRunnable runnable;

    public TradeData(Plugin plugin, TradeManager tradeManager, UUID requester, UUID responder) {
        this.tradeManager = tradeManager;
        this.requester = requester;
        this.responder = responder;
        this.state = false;
        this.runnable = new BukkitRunnable() {
            private int tick = 10;

            @Override
            public void run() {
                if(TradeData.this.state) {
                    cancel();
                    return;
                }
                if(this.tick == 0) {
                    MsgUtil.sendMsg(TradeData.this.requester, TradePlugin.prefix + "§c요청이 만료되었습니다.");
                    MsgUtil.sendMsg(TradeData.this.responder, TradePlugin.prefix + "§c요청이 만료되었습니다.");
                    cancel();
                }
                this.tick--;
            }
        };
        this.runnable.runTaskTimer(plugin, 0, 20);
    }

    public UUID getRequester() {
        return this.requester;
    }

    public UUID getResponder() {
        return this.responder;
    }

    public void deleteTradeData() {
        if(!this.runnable.isCancelled()) {
            this.runnable.cancel();
        }
        this.tradeManager.removeTradeData(this);
    }
}
