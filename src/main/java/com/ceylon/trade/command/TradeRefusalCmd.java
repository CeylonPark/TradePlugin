package com.ceylon.trade.command;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.api.SubCommand;
import com.ceylon.trade.data.TradeData;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class TradeRefusalCmd extends SubCommand {
    private final TradeManager tradeManager;

    public TradeRefusalCmd(Plugin plugin, String command, TradeManager tradeManager) {
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
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c시용 방법이 잘못되었습니다. §f(Usage: /교환 수락/거절");
            return true;
        }
        UUID responder = ((Player) sender).getUniqueId();
        TradeData tradeData = this.tradeManager.getTradeData(responder);
        if(tradeData == null) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c받은 교환 요청이 없습니다.");
            return true;
        }
        if(tradeData.getRequester().equals(responder)) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c당신은 교환 요청을 보낸 상태입니다.");
            return true;
        }
        tradeData.deleteTradeData();
        MsgUtil.sendMsg(tradeData.getRequester(), TradePlugin.prefix + "§c상대방이 교환 요청을 거절하였습니다.");
        MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c교환 요청을 거절하였습니다.");
        return true;
    }
}
