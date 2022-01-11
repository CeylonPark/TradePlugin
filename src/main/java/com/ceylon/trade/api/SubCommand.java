package com.ceylon.trade.api;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract class SubCommand extends CommandFrame {
    public SubCommand(Plugin plugin, String command) {
        super(plugin, command);
    }

    public abstract boolean onCommand(CommandSender sender, List<String> args);
}
