package co.uk.jedpalmer.thesource.plugins.updates;

import co.uk.jedpalmer.thesource.TheSource;
import co.uk.jedpalmer.thesource.utils.console.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.net.URL;

/**
 * Created by peraldon on 30/06/2015.
 */
public class UpdateCheck {
    public static void check(Plugin plugin) {
        String pluginName = plugin.getName();
        File dir = null;

        MessageManager.sendDebug("Checking update for " + pluginName, TheSource.getTheSourcePlugin());
        MessageManager.sendDebug("Using local updates.", TheSource.getTheSourcePlugin());
        //Loads up the local directory as defined in the config
        //TODO: Add remote config check and setup here
//        if(Bukkit.getPluginManager().getPlugin("TheSource").getConfig().get("updates.local").equals(true)){
            dir = new File(Bukkit.getPluginManager().getPlugin("TheSource").getConfig().get("updates.address").toString());
//        } else {
//            URL url = new URL(Bukkit.getPluginManager().getPlugin("TheSource").getConfig().get("updates.address").toString());
//            dir =
//        }

        //Gets all the files in this directory
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            MessageManager.sendDebug("Found files in the configured update folder", TheSource.getTheSourcePlugin());
            //Iterates through every file in the directory
            for (File child : directoryListing) {
                //Seperates the plugin name and the version
                int seperator = child.toString().lastIndexOf("-");
                int startName = child.toString().lastIndexOf("\\");
                String remotePluginName = child.toString().substring(startName + 1, seperator);
                if (remotePluginName.equals(pluginName)) {
                    //They're the same name, time to compare version!
                    if (VersionCompare.compare(plugin, child.toString().substring(seperator + 1, child.toString().lastIndexOf('.')))) {
                        if(Bukkit.getPluginManager().getPlugin("TheSource").getConfig().get("plugins." + pluginName + ".autoupdate").equals(true)){
                            if(pluginName.equals("TheSource")){
                                if(Bukkit.getPluginManager().getPlugin(plugin.getName()).getConfig().get("updates.local").equals(true)){
                                    LocalPluginUpdate.update(child.toString().substring(startName + 1), plugin);
                                    Bukkit.reload();
                                } else {
                                    MessageManager.sendSevere("The Source does not support remote plugin installing");
                                }
                            } else {
                                if(Bukkit.getPluginManager().getPlugin(plugin.getName()).getConfig().get("updates.local").equals(true)){
                                    LocalPluginUpdate.update(child.toString().substring(startName + 1), plugin);
                                } else {
                                    MessageManager.sendSevere("The Source does not support remote plugin installing");
                                }
                                if(Bukkit.getPluginManager().getPlugin(pluginName).isEnabled()){
                                    Bukkit.getPluginManager().disablePlugin(plugin);
                                    Bukkit.getPluginManager().enablePlugin(plugin);
                                } else {
                                    Bukkit.getPluginManager().enablePlugin(plugin);
                                }

                            }
                        } else {
                            MessageManager.sendInfo(pluginName + " is not running the latest version, but has auto update turned off!", ChatColor.RED);
                        }
                    } else {
                        MessageManager.sendInfo(pluginName + " is already up to date!", ChatColor.GREEN);
                    }
                } else {
                    MessageManager.sendDebug("No plugin to compare to in the update place for plugin " + pluginName, TheSource.getTheSourcePlugin());
                }
            }
        } else {
            MessageManager.sendSevere("The update folder is empty. Does it even exist?");
        }
    }
}
