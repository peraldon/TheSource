package co.uk.jedpalmer.thesource.utils.player;

import co.uk.jedpalmer.thesource.utils.console.MessageManager;
import co.uk.jedpalmer.thesource.TheSource;
import co.uk.jedpalmer.thesource.utils.player.messageformats.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by peraldon on 30/06/2015.
 */
public class UnlocalisedMessage {
    public static void sendMessage(Plugin plugin, Player player, String message, MessageTypes type, MessageFormats format){
        //Easily edit to change timings of titles/subtitles
        int stay = 100;
        int fadeIn = 10;
        int fadeOut = 10;

        //Modify the message as per MessageTypes enum
        switch(type){
            case SUCCESS:
                message = Bukkit.getPluginManager().getPlugin(plugin.getName()).getConfig().get("source." + plugin.getName() + ".colours.success").toString() + message;
            case FAILURE:
                message = Bukkit.getPluginManager().getPlugin(plugin.getName()).getConfig().get("source." + plugin.getName() + ".colours.failure").toString() + message;
            case INFORMATION:
                message = Bukkit.getPluginManager().getPlugin(plugin.getName()).getConfig().get("source." + plugin.getName() + ".colours.information").toString() + message;
            case ERROR:
                message = Bukkit.getPluginManager().getPlugin(plugin.getName()).getConfig().get("source." + plugin.getName() + ".colours.error").toString() + message;
        }
        //Send out in format as defined by MessageFormats enum
        switch(format){
            case TITLE:
                Title.send(player, message, stay, fadeIn, fadeOut);
                MessageManager.sendDebug("Sent title: " + message + ", to " + player.getName(), TheSource.getTheSourcePlugin());
            case SUBTITLE:
                SubTitle.send(player, message, stay, fadeIn, fadeOut);
                MessageManager.sendDebug("Sent subtitle: " + message + ", to " + player.getName(), TheSource.getTheSourcePlugin());
            case ACTIONBAR:
                ActionBar.send(player, message);
                MessageManager.sendDebug("Sent actionbar: " + message + ", to " + player.getName(), TheSource.getTheSourcePlugin());
            case TABTITLE:
                String header = message.substring(0, message.indexOf("\\n"));
                String footer = message.substring(message.indexOf("\\n") + 1);
                TabTitle.send(player, header, footer);
                MessageManager.sendDebug("Sent TabTitle: " + message + ", to " + player.getName(), TheSource.getTheSourcePlugin());
            case CHAT:
                player.sendRawMessage(message);
                MessageManager.sendDebug("Sent text: " + message + ", to " + player.getName(), TheSource.getTheSourcePlugin());
        }
    }

    //Want to quickly broadcast to all players? Here is the place to do it! Same functionality as sendMessage
    public void sendBroadcast(Plugin plugin, String message,MessageTypes type, MessageFormats format){
        int stay = 100;
        int fadeIn = 10;
        int fadeOut = 10;

        switch(type){
            case SUCCESS:
                message = Bukkit.getPluginManager().getPlugin(plugin.getName()).getConfig().get("source." + plugin.getName() + ".colours.success").toString() + message;
            case FAILURE:
                message = Bukkit.getPluginManager().getPlugin(plugin.getName()).getConfig().get("source." + plugin.getName() + ".colours.failure").toString() + message;
            case INFORMATION:
                message = Bukkit.getPluginManager().getPlugin(plugin.getName()).getConfig().get("source." + plugin.getName() + ".colours.information").toString() + message;
            case ERROR:
                message = Bukkit.getPluginManager().getPlugin(plugin.getName()).getConfig().get("source." + plugin.getName() + ".colours.error").toString() + message;
        }
        switch(format){
            case TITLE:
                for( Player player : plugin.getServer().getOnlinePlayers()){
                    Title.send(player, message, stay, fadeIn, fadeOut);
            }
                MessageManager.sendDebug("Sent title: " + message + ", to everyone.", TheSource.getTheSourcePlugin());
            case SUBTITLE:
                for( Player player : plugin.getServer().getOnlinePlayers()){
                    SubTitle.send(player, message, stay, fadeIn, fadeOut);
                }
                MessageManager.sendDebug("Sent subtitle: " + message + ", to everyone.", TheSource.getTheSourcePlugin());
            case ACTIONBAR:
                for( Player player : plugin.getServer().getOnlinePlayers()){
                    ActionBar.send(player, message);
                }
                MessageManager.sendDebug("Sent actionbar: " + message + ", to everyone.", TheSource.getTheSourcePlugin());;
            case TABTITLE:
                String header = message.substring(0, message.indexOf("\\n"));
                String footer = message.substring(message.indexOf("\\n") + 1);
                for( Player player : plugin.getServer().getOnlinePlayers()){
                    TabTitle.send(player, header, footer);
                }
                MessageManager.sendDebug("Sent TabTitle: " + message + ", to everyone.", TheSource.getTheSourcePlugin());
            case CHAT:
                for( Player player : plugin.getServer().getOnlinePlayers()){
                    player.sendRawMessage(message);
                }
                MessageManager.sendDebug("Sent text: " + message + ", to everyone.", TheSource.getTheSourcePlugin());
        }
    }
}
