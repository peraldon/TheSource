package co.uk.jedpalmer.thesource.utils.player.messageformats;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by peraldon on 30/06/2015.
 * Thanks to ConnorLinfoot for the base code: https://github.com/ConnorLinfoot/TitleAPI
 */
public class Title {

    public static void send(Player player, String message, int stay, int fadeIn, int fadeOut){
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
        connection.sendPacket(packetPlayOutTimes);

        message = message.replaceAll("%player%", player.getDisplayName());
        message = ChatColor.translateAlternateColorCodes('&', message);
        IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
        connection.sendPacket(packetPlayOutTitle);
    }
}
