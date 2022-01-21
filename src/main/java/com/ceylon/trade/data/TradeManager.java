package com.ceylon.trade.data;

import com.ceylon.trade.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TradeManager {
    private final Plugin plugin;
    private final Set<TradeData> tradeDataSet = new HashSet<>();
    private final List<TradeSign> tradeSignList = new ArrayList<>();

    public TradeManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void addTradeData(UUID requester, UUID responder) {
        this.tradeDataSet.add(new TradeData(this.plugin, this, requester, responder));
    }

    public void removeTradeData(TradeData tradeData) {
        this.tradeDataSet.remove(tradeData);
    }

    public TradeData getTradeData(UUID uuid) {
        for(TradeData tradeData : this.tradeDataSet) {
            if(tradeData.getRequester().equals(uuid) || tradeData.getResponder().equals(uuid)) {
                return tradeData;
            }
        }
        return null;
    }

    public boolean addTradeSign(UUID registrant, String title, String contents) {
        if(this.getTradeSign(title) == null) {
            this.tradeSignList.add(new TradeSign(registrant, title, contents));
            return true;
        }
        return false;
    }

    public void removeTradeSign(TradeSign tradeSign) {
        this.tradeSignList.remove(tradeSign);
    }

    public TradeSign getTradeSign(String title) {
        for(TradeSign tradeSign : this.tradeSignList) {
            if(tradeSign.getTitle().equals(title)) {
                return tradeSign;
            }
        }
        return null;
    }

    public List<ItemStack> getTradeSignList(int start, int end) {
        List<ItemStack> list = new ArrayList<>();
        if(start <= end && start < this.tradeSignList.size()) {
            for(int i = start; i < Math.min(end+1, this.tradeSignList.size()); i++) {
                TradeSign tradeSign = this.tradeSignList.get(i);
                ItemStack itemStack = new ItemBuilder(Material.BIRCH_SIGN)
                        .setDisplayName("§f"+tradeSign.getTitle())
                        .addLore("§f")
                        .addLore("§a[등록자] §f: " + this.plugin.getServer().getOfflinePlayer(tradeSign.getRegistrant()).getName())
                        .addLore("§d[내용] §f: " + tradeSign.getContents())
                        .addLore("§f")
                        .build();
                list.add(itemStack);
            }
        }
        return list;
    }

    public List<String> getTradeSignList(UUID registrant) {
        List<String> list = new ArrayList<>();
        for(TradeSign tradeSign : this.tradeSignList) {
            if(tradeSign.getRegistrant().equals(registrant)) {
                list.add(tradeSign.getTitle());
            }
        }
        return list;
    }

    public void load() {
        if(this.plugin.getDataFolder().mkdirs()) {
            this.plugin.getLogger().info("플러그인 풀더가 생성되었습니다.");
        }
        File file = new File(this.plugin.getDataFolder(), "trade.yml");
        FileConfiguration yml = YamlConfiguration.loadConfiguration(file);
        MemorySection list = (MemorySection) yml.get("list");
        if(list == null) {
            return;
        }
        for(String path : list.getKeys(false)) {
            TradeSign tradeSign = new TradeSign(
                    UUID.fromString(Objects.requireNonNull(yml.getString("list."+path+".registrant"))),
                    yml.getString("list."+path+".title"),
                    yml.getString("list."+path+".contents"));
            this.tradeSignList.add(tradeSign);
        }
    }

    public void save() {
        File file = new File(this.plugin.getDataFolder(), "trade.yml");
        FileConfiguration yml = new YamlConfiguration();
        for(int i = 0; i < this.tradeSignList.size(); i++) {
            TradeSign tradeSign = this.tradeSignList.get(i);
            yml.set("list."+i+".registrant", tradeSign.getRegistrant().toString());
            yml.set("list."+i+".title", tradeSign.getTitle());
            yml.set("list."+i+".contents", tradeSign.getContents());
        }
        try {
            yml.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}

