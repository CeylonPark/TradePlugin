package com.ceylon.trade.command;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.TradeSignInventoryOpen;
import com.ceylon.trade.api.SubCommand;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class TradeSignOpenCmd extends SubCommand {
    private final TradeSignInventoryOpen tradeSignInventoryOpen;

    public TradeSignOpenCmd(Plugin plugin, String command, TradeManager tradeManager) {
        super(plugin, command);
        this.tradeSignInventoryOpen = ((TradePlugin) plugin).getTradeSignInventoryOpen();
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        if(!(sender instanceof Player)) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "플레이어만 사용 가능한 명령어입니다.");
            return true;
        }
        if(args.size() != 0) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c시용 방법이 잘못되었습니다. §f(Usage: /장사글 열기)");
            return true;
        }
        this.tradeSignInventoryOpen.openInventory((Player) sender, 1);
        return true;
    }
}
