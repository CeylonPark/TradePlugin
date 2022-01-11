package com.ceylon.trade.command;

import com.ceylon.trade.api.SubCommand;
import com.ceylon.trade.data.TradeManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class TradeAcceptCmd extends SubCommand {
    private final TradeManager tradeManager;

    public TradeAcceptCmd(Plugin plugin, String command, TradeManager tradeManager) {
        super(plugin, command);
        this.tradeManager = tradeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        // 교환 수락 ㅇ
        return true;
    }
}