package com.ceylon.trade;

import com.ceylon.trade.command.TradeCommand;
import com.ceylon.trade.command.TradeSignCommand;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.listener.TradeInventoryListener;
import com.ceylon.trade.listener.TradeSignInventoryListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TradePlugin extends JavaPlugin {
    public static final double NEARBY_SIZE_X = 10;
    public static final double NEARBY_SIZE_Y = 30;
    public static final double NEARBY_SIZE_Z = 10;
    public final static String prefix = "§f[ §aTrade §f] ";
    private final TradeManager tradeManager;
    private final TradeSignInventoryOpen tradeSignInventoryOpen;

    public TradePlugin() {
        this.tradeManager = new TradeManager(this);
        this.tradeSignInventoryOpen = new TradeSignInventoryOpen(this.tradeManager);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.tradeManager.load();
        Objects.requireNonNull(getCommand("교환")).setExecutor(new TradeCommand(this, "교환", this.tradeManager));
        Objects.requireNonNull(getCommand("장사글")).setExecutor(new TradeSignCommand(this, "장사글", this.tradeManager));
        getServer().getPluginManager().registerEvents(new TradeInventoryListener(this.tradeManager), this);
        getServer().getPluginManager().registerEvents(new TradeSignInventoryListener(this.tradeSignInventoryOpen), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.tradeManager.save();
    }

    public TradeSignInventoryOpen getTradeSignInventoryOpen() {
        return this.tradeSignInventoryOpen;
    }
}
