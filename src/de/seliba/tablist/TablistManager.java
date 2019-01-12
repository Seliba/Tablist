package de.seliba.tablist;

/*
Tablist created by Seliba
*/

import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TablistManager {

    public void sendTablistHeaderAndFooter(Player p, String header, String footer) {
        PacketPlayOutPlayerListHeaderFooter packetPlayOutPlayerListHeaderFooter = new PacketPlayOutPlayerListHeaderFooter();
        packetPlayOutPlayerListHeaderFooter.header = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        packetPlayOutPlayerListHeaderFooter.footer = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutPlayerListHeaderFooter);
    }

}
