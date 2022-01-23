package com.ceylon.trade.command;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.api.SubCommand;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class TradeSignListCmd extends SubCommand {
    private final TradeManager tradeManager;

    public TradeSignListCmd(Plugin plugin, String command, TradeManager tradeManager) {
        super(plugin, command);
        this.tradeManager = tradeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        if(!(sender instanceof Player)) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "플레이어만 사용 가능한 명령어입니다.");
            return true;
        }
        if(args.size() != 0) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c시용 방법이 잘못되었습니다. §f(Usage: /장사글 목록)");
            return true;
        }
        List<String> list = this.tradeManager.getTradeSignListOfRegistrant(((Player) sender).getUniqueId());
        if(list.isEmpty()) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c작성하신 장사글이 없습니다.");
            return true;
        }
        MsgUtil.sendMsg(sender, TradePlugin.prefix + "장사글 목록.");
        for(String string : list) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "  - "+string);
        }
        MsgUtil.sendMsg(sender, " ");
        return true;
    }
}
