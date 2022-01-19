package com.ceylon.trade.command;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.api.CommandConstructor;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class TradeSignCommand extends CommandConstructor {

    public TradeSignCommand(Plugin plugin, String command, TradeManager tradeManager) {
        super(plugin, command);
        registerSubCommand(new TradeSignAddCmd(plugin, "등록", tradeManager));
        registerSubCommand(new TradeSignRemoveCmd(plugin, "제거", tradeManager));
        registerSubCommand(new TradeSignListCmd(plugin, "목록", tradeManager));
        registerSubCommand(new TradeSignOpenCmd(plugin, "열기", tradeManager));
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
                "  => /장사글 등록 <제목> <내용> - 장사글을 등록합니다.",
                "  => /장사글 삭제 <제목> - 장사글을 삭제합니다.",
                "  => /장사글 목록 - 나의 장사글 목록을 확인합니다.",
                "  => /장사글 열기 - 장사글 GUI를 엽니다.",
                " "
        };
        MsgUtil.sendMsg(sender, help);
    }
}
