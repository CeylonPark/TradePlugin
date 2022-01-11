package com.ceylon.trade.command;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.api.SubCommand;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class TradeRequestCmd extends SubCommand {
    private static final double NEARBY_SIZE_X = 10;
    private static final double NEARBY_SIZE_Y = 30;
    private static final double NEARBY_SIZE_Z = 10;
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
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c시용 방법이 잘못되었습니다. §f(Usage: /교환 요청");
            return true;
        }
        Player player = (Player) sender;
        Player target = getPlugin().getServer().getPlayer(args.get(0));
        if(target == null) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c플레이어 "+args.get(0)+" (이)가 존재하지 않습니다.");
            return true;
        }
        List<Entity> nearbyPlayers = player.getNearbyEntities(TradeRequestCmd.NEARBY_SIZE_X, TradeRequestCmd.NEARBY_SIZE_Y, TradeRequestCmd.NEARBY_SIZE_Y);
        if(!nearbyPlayers.contains(player)) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c플레이어 " + args.get(0) + " (이)가 주변에 존재하지 않습니다.");
            return true;

        }

        //상황 고려 시발
        // 타겟이 딴 놈한테 요청 받은 경우
        // 이새끼가 요청을 보낸경우
        // 그리고 쿨타임 만들어야댐
        // 쿨타임을 20초로 하자. 몇초? 20초
        // 그러면 어디서 쿨타임을 돌리냐
        // 옛날의 나는 커맨드 클래스에서 바로 돌렸다.

        //내일의 나에게 맡기자!
        return true;
    }
}

//https://bukkit.org/threads/get-nearby-entities-in-a-radius.187451/
