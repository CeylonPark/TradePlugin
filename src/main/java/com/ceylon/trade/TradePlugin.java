package com.ceylon.trade;

import com.ceylon.trade.command.TradeCommand;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.listener.TradeInventoryListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TradePlugin extends JavaPlugin {
    public final static String prefix = "§f[ §aTrade §f] ";
    private final TradeManager tradeManager;

    public TradePlugin() {
        this.tradeManager = new TradeManager();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(getCommand("교환")).setExecutor(new TradeCommand(this, "교환", this.tradeManager));
        // 장사글 커맨드 등록
        getServer().getPluginManager().registerEvents(new TradeInventoryListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
