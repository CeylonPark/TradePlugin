package com.ceylon.trade.command;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.api.SubCommand;
import com.ceylon.trade.data.TradeData;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class TradeRequestCmd extends SubCommand {
    private final TradeManager tradeManager;

    public TradeRequestCmd(Plugin plugin, String command, TradeManager tradeManager) {
        super(plugin, command);
        this.tradeManager = tradeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        if(!(sender instanceof Player)) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "플레이어만 사용 가능한 명령어입니다.");
            return true;
        }
        if(args.size() != 1) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c시용 방법이 잘못되었습니다. §f(Usage: /교환 요청 <플레이어>");
            return true;
        }
        Player player = (Player) sender;
        if(player.getName().equals(args.get(0))) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c자기 자신에게 요청할 수 없습니다.");
            return true;
        }
        UUID requester = player.getUniqueId();
        TradeData tradeData = this.tradeManager.getTradeData(requester);
        if(tradeData != null) {
            if(tradeData.getRequester().equals(requester)) {
                MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c이미 요청을 보낸 상태입니다.");
            } else {
                MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c당신은 요청을 받은 상태입니다.");
            }
            return true;
        }
        Player target = getPlugin().getServer().getPlayer(args.get(0));
        if(target == null) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c플레이어 "+args.get(0)+" (이)가 존재하지 않습니다.");
            return true;
        }
        UUID responder = target.getUniqueId();
        if(this.tradeManager.getTradeData(responder) != null) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c상대방이 교환 중인 상태입니다.");
            return true;
        }
        List<Entity> nearbyPlayers = player.getNearbyEntities(TradePlugin.NEARBY_SIZE_X, TradePlugin.NEARBY_SIZE_Y, TradePlugin.NEARBY_SIZE_Y);
        if(!nearbyPlayers.contains(target)) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c플레이어 " + args.get(0) + " (이)가 주변에 존재하지 않습니다.");
            return true;
        }
        this.tradeManager.addTradeData(requester, responder);
        MsgUtil.sendMsg(sender, TradePlugin.prefix + "플레이어 "+args.get(0)+" 에게 교환 요청을 보냈습니다.");
        MsgUtil.sendMsg(target, TradePlugin.prefix + "플레이어 "+ sender.getName() +" 에게 교환 요청을 받았습니다."
                , TradePlugin.prefix + "교환 요청에 응답할 수 있습니다. (Usage: /교환 수락/거부)");
        return true;
    }
}

//https://bukkit.org/threads/get-nearby-entities-in-a-radius.187451/
