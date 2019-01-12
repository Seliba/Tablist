package de.seliba.tablist;

/*
Tablist created by Seliba
*/

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private Main main;

    public PlayerJoinListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        main.getTablistManager().sendTablistHeaderAndFooter(event.getPlayer(), main.getHeader(event.getPlayer()), main.getFooter(event.getPlayer()));
    }

}
