package co.uk.jedpalmer.thesource.plugins.updates;

import co.uk.jedpalmer.thesource.TheSource;
import co.uk.jedpalmer.thesource.utils.console.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Created by peraldon on 30/06/2015.
 */
public class VersionCompare {
    //Returns true if update needing, false if not
    public static boolean compare(Plugin plugin, String child){
        //Initialize for the for loop below
        int remotePluginVersion;
        int pluginVersion;
        //Needs to be plus one to run the correct amount of times #Strings
        int end = child.length() + 1;
        //The for compare loop
        for(int i = 0; i != end; i++){
            //Just catching ex just incase version is only 0.1, and the other is 0.14 or the like
            try {
                remotePluginVersion = Character.getNumericValue(child.charAt(i));
                MessageManager.sendDebug(remotePluginVersion + " - remotePluginVersion - " + child.charAt(i), TheSource.getTheSourcePlugin());
            } catch (StringIndexOutOfBoundsException ex) {
                remotePluginVersion = 0;
                MessageManager.sendDebug("remotePluginVersion could not get numericValue", TheSource.getTheSourcePlugin());
            }
            try {
                pluginVersion = Character.getNumericValue(Bukkit.getPluginManager().getPlugin(plugin.getName()).getDescription().getVersion().charAt(i));
                MessageManager.sendDebug(pluginVersion + " - pluginVersion -  " + Bukkit.getPluginManager().getPlugin(plugin.getName()).getDescription().getVersion().charAt(i), TheSource.getTheSourcePlugin());
            } catch (StringIndexOutOfBoundsException ex) {
                pluginVersion = 0;
                MessageManager.sendDebug("pluginVersion could not get numeric value", TheSource.getTheSourcePlugin());
            }
            //Remote later version? Return true to tell the plugin to download it!
            if(remotePluginVersion > pluginVersion){
                return true;
            } else if(remotePluginVersion < pluginVersion){
                return false;
            }
        }
        //Just to make Java happy
        return false;
    }
}
