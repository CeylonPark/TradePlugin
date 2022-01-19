package com.ceylon.trade.command;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.api.SubCommand;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class TradeSignAddCmd extends SubCommand {
    private final TradeManager tradeManager;

    public TradeSignAddCmd(Plugin plugin, String command, TradeManager tradeManager) {
        super(plugin, command);
        this.tradeManager = tradeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        if(!(sender instanceof Player)) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "플레이어만 사용 가능한 명령어입니다.");
            return true;
        }
        if(args.size() < 2) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c시용 방법이 잘못되었습니다. §f(Usage: /장사글 등록 <제목> <내용>)");
            return true;
        }
        String title = args.get(0);
        // 색깔 코드 삭제 ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name))
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < args.size(); i++) {
            builder.append(args.get(i));
        }
        if(this.tradeManager.addTradeSign(((Player) sender).getUniqueId(), title, builder.toString())) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "장사글 [ "+title+" ] 가 성공적으로 등록되었습니다.");
        } else {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c장사글 [ "+title+" ] 은 이미 존재하는 장사글입니다.");
        }
        return true;
    }
}
