package com.ceylon.trade.command;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.api.CommandConstructor;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class TradeCommand extends CommandConstructor {

    public TradeCommand(Plugin plugin, String command, TradeManager tradeManager) {
        super(plugin, command);
        registerSubCommand(new TradeRequestCmd(plugin, "요청", tradeManager));
        registerSubCommand(new TradeAcceptCmd(plugin, "수락", tradeManager));
        registerSubCommand(new TradeRefusalCmd(plugin, "거절", tradeManager));
    }

    @Override
    public boolean onCommandEmpty(CommandSender sender, Command command, String label) {
        this.sendHelp(sender);
        return true;
    }

    @Override
    public boolean onBeforeCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @Override
    public boolean onAfterCommand(CommandSender sender, Command command, String label, String[] args, boolean sub_result) {
        if(!sub_result) {
            this.sendHelp(sender);
        }
        return true;
    }

    public void sendHelp(CommandSender sender) {
        String[] help = new String[] {
                TradePlugin.prefix + "Usage:",
                "  => /교환 요청 <플레이어> - 근처에 있는 <플레이어>에게 요청을 보냅니다.",
                "  => /교환 수락/거부 - 교환 요청을 수락/거부 합니다.",
                " "
        };
        MsgUtil.sendMsg(sender, help);
    }
}
