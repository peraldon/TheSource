package co.uk.jedpalmer.thesource.utils.console;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

/**
 * Created by peraldon on 28/06/2015.
 */
public class MessageManager {
    private static ConsoleCommandSender console = Bukkit.getConsoleSender();

    //Normal console messages
    public static void sendInfo(String message, ChatColor color){
        console.sendMessage(color + message);
    }
    public static void sendWarning(String message){
        console.sendMessage(ChatColor.RED + message);
    }
    public static void sendSevere(String message){
        console.sendMessage(ChatColor.DARK_RED + message);
    }

    //For development. Is setup in the config for TheSource, and can be used by any Source Plugins
    public static void sendDebug(String message, Plugin plugin){
        if(Bukkit.getPluginManager().getPlugin("TheSource").getConfig().get("plugins." + plugin.getName() + ".debug").equals(true)){
            console.sendMessage(ChatColor.DARK_GRAY + "[ " + ChatColor.WHITE + plugin.getName() + " debug" + ChatColor.DARK_GRAY + " ] " + ChatColor.GRAY + message);
        }
    }
}
