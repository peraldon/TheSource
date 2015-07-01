package co.uk.jedpalmer.thesource.listeners;

import co.uk.jedpalmer.thesource.TheSource;
import co.uk.jedpalmer.thesource.plugins.PluginStatus;
import co.uk.jedpalmer.thesource.utils.player.MessageTypes;
import co.uk.jedpalmer.thesource.utils.player.UnlocalisedMessage;
import co.uk.jedpalmer.thesource.utils.player.messageformats.MessageFormats;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by peraldon on 30/06/2015.
 */
public class ConnectionListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event){
        //If they have the admin perm node, the player will get send an error message each time they login detailing the un-updated plugins
        if(event.getPlayer().hasPermission("thesource.admin")){
            for(int i = 0; i < PluginStatus.getActivePlugins().size(); i++){
                UnlocalisedMessage.sendMessage(TheSource.getTheSourcePlugin(), event.getPlayer(), PluginStatus.getActivePlugins().get(i) + " is out of date! Please update it.", MessageTypes.ERROR, MessageFormats.CHAT);
            }
        }
    }
}
