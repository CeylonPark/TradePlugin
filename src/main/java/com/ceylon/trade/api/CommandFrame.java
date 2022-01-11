package com.ceylon.trade.api;

import org.bukkit.plugin.Plugin;

public abstract class CommandFrame {
    private final Plugin plugin;
    private final String command;

    public CommandFrame(Plugin plugin, String command) {
        this.plugin = plugin;
        this.command = command;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public String getCommand() {
        return this.command;
    }
}
