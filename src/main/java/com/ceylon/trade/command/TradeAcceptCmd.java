package com.ceylon.trade.command;

import com.ceylon.trade.TradePlugin;
import com.ceylon.trade.api.SubCommand;
import com.ceylon.trade.data.TradeData;
import com.ceylon.trade.data.TradeManager;
import com.ceylon.trade.util.ItemBuilder;
import com.ceylon.trade.util.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class TradeAcceptCmd extends SubCommand {
    private final TradeManager tradeManager;

    public TradeAcceptCmd(Plugin plugin, String command, TradeManager tradeManager) {
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
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c시용 방법이 잘못되었습니다. §f(Usage: /교환 수락/거부");
            return true;
        }
        Player responder = (Player) sender;
        UUID responderUUID = responder.getUniqueId();
        TradeData tradeData = this.tradeManager.getTradeData(responderUUID);
        if(tradeData == null) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c받은 교환 요청이 없습니다.");
            return true;
        }
        UUID requesterUUID = tradeData.getRequester();
        if(requesterUUID.equals(responderUUID)) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c당신은 교환 요청을 보낸 상태입니다.");
            return true;
        }
        OfflinePlayer requesterOffline = getPlugin().getServer().getOfflinePlayer(requesterUUID);
        Player requester = requesterOffline.getPlayer();
        if(requester == null) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c플레이어 "+requesterOffline.getName()+" 은 접속 중이지 않습니다.");
            return true;
        }
        List<Entity> nearbyPlayers = responder.getNearbyEntities(TradePlugin.NEARBY_SIZE_X, TradePlugin.NEARBY_SIZE_Y, TradePlugin.NEARBY_SIZE_Z);
        if(!nearbyPlayers.contains(requester)) {
            MsgUtil.sendMsg(sender, TradePlugin.prefix + "§c플레이어 "+requesterOffline.getName()+" (이)가 주변에 존재하지 않습니다.");
            return true;
        }

        tradeData.onTrade();

        // 교환창 설정
        Inventory inv = Bukkit.createInventory(null, 54, "교환");

        ItemStack glass_pane = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§f").build();
        for(int slot : new int[]{4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 49}) {
            inv.setItem(slot, glass_pane);
        }

        for(int slot : new int[]{47, 51}) {
            ItemStack accept = new ItemBuilder(Material.RED_CONCRETE)
                    .setDisplayName("§f클릭 시 거래를 수락합니다.")
                    .build();
            inv.setItem(slot, accept);
        }

        // 플레이어 머리 설정, 오픈
        int slot = 45;
        for(Player player : new Player[]{requester, responder}) {
            inv.setItem(slot, new ItemBuilder(Material.PLAYER_HEAD).setOwningPlayer(player).build());
            player.openInventory(inv);
            slot += 8;
        }
        return true;
    }
}
