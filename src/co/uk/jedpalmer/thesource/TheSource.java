package co.uk.jedpalmer.thesource;

import co.uk.jedpalmer.thesource.plugins.PluginStatus;
import co.uk.jedpalmer.thesource.plugins.updates.UpdateCheck;
import co.uk.jedpalmer.thesource.utils.console.MessageManager;
import co.uk.jedpalmer.thesource.listeners.ConnectionListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * Created by peraldon on 28/06/2015.
 */
public class TheSource extends JavaPlugin{
    FileConfiguration config;
    private static Plugin theSourcePlugin;

    //Get plugin instance;
    public static Plugin getTheSourcePlugin(){
        return theSourcePlugin;
    }

    @Override
    public void onEnable(){
        //make plugin instance not null
        theSourcePlugin = this;

        //This is scheduled so that it runs after all plugins have loaded, thereby ensuring that all active plugins are in activePlugins
        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                activateSource();
                getServer().getPluginManager().registerEvents(new ConnectionListener(), theSourcePlugin);
            }
        }, 20L);

    //If there is no config file, create it
    if(!new File(getDataFolder(), "config.yml").exists()){
        saveResource("config.yml", false);
        this.config = getConfig();
    //If there is, just load it
    } else {
        this.config = getConfig();
    }
        MessageManager.sendInfo("The Source is enabled, but will not start doing it's work until all plugins are loaded.", ChatColor.YELLOW);
        //Adds TheSource to itself so that it can update itself
        addPlugin(this);
    }

    @Override
    public void onDisable(){
        //Make plugin instance null
        theSourcePlugin = null;
    }

    //Main startup method
    public void activateSource(){
        MessageManager.sendInfo("-----------------------------------------", ChatColor.YELLOW);
        MessageManager.sendInfo("--              The Source             --", ChatColor.YELLOW);
        MessageManager.sendInfo("--              Version: " + getDescription().getVersion() + "           --", ChatColor.YELLOW);
        MessageManager.sendInfo("-----------------------------------------", ChatColor.YELLOW);

        //Iterate through all plugins.* and add them to activePlugins or unactivePlugins dependant on whether they're loaded or not
        MessageManager.sendDebug("Adding plugins to activePlugins or inactivePlugins", theSourcePlugin);

        for(String key : getConfig().getConfigurationSection("plugins").getKeys(false)){
            if(Bukkit.getPluginManager().getPlugin(key) == null){
                PluginStatus.addInactivePlugin(Bukkit.getPluginManager().getPlugin(key).getName());
            } else if(Bukkit.getPluginManager().getPlugin(key).isEnabled()){
                PluginStatus.addActivePlugin(Bukkit.getPluginManager().getPlugin(key).getName());
            } else {
                PluginStatus.addInactivePlugin(Bukkit.getPluginManager().getPlugin(key).getName());
            }
        }

        //Iterates through each online plugin and prints out their general information
        MessageManager.sendInfo("The following plugins have hooked into The Source: ", ChatColor.LIGHT_PURPLE);
        for(int i = 0; i < PluginStatus.getActivePlugins().size(); i++){
            MessageManager.sendInfo(Bukkit.getPluginManager().getPlugin(PluginStatus.getActivePlugins().get(i)).getName() + " version " + Bukkit.getPluginManager().getPlugin(PluginStatus.getActivePlugins().get(i)).getDescription().getVersion() + " by " + Bukkit.getPluginManager().getPlugin(PluginStatus.getActivePlugins().get(i)).getDescription().getAuthors(), ChatColor.GOLD);
        }

        //Checks to see if there's an inactivePlugins, and if so prints out general information. Note: inactivePlugins will NOT update/install themselves!
        if(PluginStatus.getInactivePlugins().size() != 0){
            MessageManager.sendWarning("These plugins are in the config, but are not enabled:");
            for(int i = 0; i < PluginStatus.getInactivePlugins().size(); i++){
                MessageManager.sendWarning(PluginStatus.getInactivePlugins().get(i) + " version " + this.config.get("plugins." + PluginStatus.getInactivePlugins().get(i) + ".version") + " by " + this.config.get("plugins." + PluginStatus.getInactivePlugins().get(i) + ".authors"));
            }
        }

        MessageManager.sendInfo("-----------------------------------------", ChatColor.YELLOW);

        //Iterate through each activePlugin and check if it needs updating
        MessageManager.sendInfo("Checking for updates...", ChatColor.YELLOW);
        for(int i = 0; i < PluginStatus.getActivePlugins().size(); i++){
            UpdateCheck.check(Bukkit.getPluginManager().getPlugin(PluginStatus.getActivePlugins().get(i)));
        }
    }

    //Method for other plugins to call to allow the updater functionality and other things that The Source offer. It should be noted that this isn't needed to make use of the library utilities.
    public static void addPlugin(Plugin plugin){
        //Create section in the config if this plugin isn't already setup in there
        if(!Bukkit.getPluginManager().getPlugin("TheSource").getConfig().contains("plugins." + plugin.getName())){
            Bukkit.getPluginManager().getPlugin("TheSource").getConfig().set("plugins." + plugin.getName() + ".debug", false);
            Bukkit.getPluginManager().getPlugin("TheSource").getConfig().set("plugins." + plugin.getName() + ".author", Bukkit.getPluginManager().getPlugin(plugin.getName()).getDescription().getAuthors());
            Bukkit.getPluginManager().getPlugin("TheSource").getConfig().set("plugins." + plugin.getName() + ".version", Bukkit.getPluginManager().getPlugin(plugin.getName()).getDescription().getVersion());
            Bukkit.getPluginManager().getPlugin("TheSource").getConfig().set("plugins." + plugin.getName() + ".autoupdate", false);
            Bukkit.getPluginManager().getPlugin("TheSource").getConfig().set("plugins." + plugin.getName() + ".colours.success", "ChatColor.GREEN");
            Bukkit.getPluginManager().getPlugin("TheSource").getConfig().set("plugins." + plugin.getName() + ".colours.failure", "ChatColor.RED");
            Bukkit.getPluginManager().getPlugin("TheSource").getConfig().set("plugins." + plugin.getName() + ".colours.information", "ChatColor.GOLD");
            Bukkit.getPluginManager().getPlugin("TheSource").getConfig().set("plugins." + plugin.getName() + ".colours.error", "ChatColor.RED ChatColor.BOLD");
            Bukkit.getPluginManager().getPlugin("TheSource").getConfig().set("plugins." + plugin.getName() + ".colours.name", "ChatColor.DARK_GRAY [ ChatColor.PURPLE" + plugin.getName() + "ChatColor.DARK_GRAY ]");
            MessageManager.sendInfo("Created config setup for plugin " + plugin.getName(), ChatColor.GREEN);
            Bukkit.getPluginManager().getPlugin("TheSource").saveConfig();
        }
        PluginStatus.addActivePlugin(plugin.getName());
    }
}
