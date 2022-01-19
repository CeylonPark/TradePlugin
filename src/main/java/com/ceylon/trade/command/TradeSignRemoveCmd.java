package com.ceylon.trade.command;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.api.SubCommand;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.data.TradeSign;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class TradeSignRemoveCmd extends SubCommand {
    private final TradeManager tradeManager;

    public TradeSignRemoveCmd(Plugin plugin, String command, TradeManager tradeManager) {
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
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c시용 방법이 잘못되었습니다. §f(Usage: /장사글 제거 <제목>)");
            return true;
        }
        String title = args.get(0);
        // 색깔 코드 삭제 ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name))
        TradeSign tradeSign = this.tradeManager.getTradeSign(title);
        if(tradeSign == null) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c장사글 [ " + title + " ] 은 이미 존재하지 않는 장사글입니다.");
            return true;
        }
        if(!tradeSign.getRegistrant().equals(((Player) sender).getUniqueId())) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c해당 장사글의 작성자만 삭제 가능합니다.");
            return true;
        }
        this.tradeManager.removeTradeSign(tradeSign);
        MsgUtil.sendMsg(sender, TradePlugin.prefix + "장사글 [ "+title+" ] 가 성공적으로 삭제되었습니다.");
        return true;
    }
}
