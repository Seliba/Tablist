package de.seliba.tablist;

/*
Tablist created by Seliba
*/

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Main extends JavaPlugin {

    private TablistManager tablistManager;
    private Config cfg;

    @Override
    public void onEnable() {
        System.out.println("[Tablist] Gestartet!");
        tablistManager = new TablistManager();
        config();
        runTasks();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[Tablist] Gestoppt!");
    }

    private void runTasks() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TickHelper(), 100L, 1L);
        Runnable tablistRunnable = () -> {
            Bukkit.getOnlinePlayers().forEach(player -> tablistManager.sendTablistHeaderAndFooter(player, getHeader(player), getFooter(player)));
        };
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, tablistRunnable, 20L, 0L);
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }

    private void config() {
        cfg = new Config("config.yml", this);
        if(cfg.getString("ip") == null) {
            cfg.set("ip", "Server.de");
            cfg.set("header", getDefaultHeaderList());
            cfg.set("footer", getDefaultFooterList());
            cfg.save();
        }
    }

    public Config getCfg() {
        return cfg;
    }

    private List<String> getDefaultHeaderList() {
        String[] headerArray = new String[]{"--------------------------------------------", "&7Du spielst auf &6%IP%&7!", "&7Spieler online: &6%PLAYERCOUNT%&7/&6%MAXPLAYERS%"};
        return (new ArrayList<>(Arrays.asList(headerArray)));
    }

    private List<String> getDefaultFooterList() {
        String[] headerArray = new String[]{"&7TPS: &6%TPS%", "&7Uhrzeit: &6%TIME%", "--------------------------------------------"};
        return (new ArrayList<>(Arrays.asList(headerArray)));
    }

    public String getHeader(Player player) {
        List<String> headerList = cfg.getStringList("header");
        String header = "";
        for(int i = 0; i < headerList.size(); i++) {
            header = header + ChatColor.translateAlternateColorCodes('&', headerList.get(i)
                    .replaceAll("%PLAYERCOUNT%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replaceAll("%MAXPLAYERS%", String.valueOf(Bukkit.getMaxPlayers()))
                    .replaceAll("%TPS%", String.valueOf(TickHelper.getTPS()))
                    .replaceAll("%PING%", String.valueOf(getPing(player)))
                    .replaceAll("%TIME%", getTime()).replaceAll("%IP%", cfg.getString("ip")));
            if((i + 1) < headerList.size()) header = header + "\n";
        }
        return header;
    }

    public String getFooter(Player player) {
        List<String> footerList = cfg.getStringList("footer");
        String footer = "";
        for(int i = 0; i < footerList.size(); i++) {
            footer = footer + "§f" + ChatColor.translateAlternateColorCodes('&', footerList.get(i)
                    .replaceAll("%PLAYERCOUNT%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replaceAll("%MAXPLAYERS%", String.valueOf(Bukkit.getMaxPlayers()))
                    .replaceAll("%TPS%", String.valueOf(Math.round(TickHelper.getTPS() * 100.0D) / 100.0D))
                    .replaceAll("%PING%", String.valueOf(getPing(player))).replaceAll("%TIME%", getTime())
                    .replaceAll("%IP%", cfg.getString("ip")));
            if((i + 1) < footerList.size()) footer = footer + "\n";
        }
        return footer;
    }

    private int getPing(Player player) {
        return ((CraftPlayer)player).getHandle().ping;
    }

    public static String getTime() {
        final SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
        return date.format(Calendar.getInstance().getTime());
    }

}
