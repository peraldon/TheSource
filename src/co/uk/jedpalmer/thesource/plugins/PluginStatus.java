package co.uk.jedpalmer.thesource.plugins;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peraldon on 30/06/2015.
 */
public class PluginStatus {
    private static List<String> activePlugins = new ArrayList<String>();
    private static List<String> inactivePlugins = new ArrayList<String>();

    //Grab data
    public static List<String> getActivePlugins() {
        return activePlugins;
    }
    public static List<String> getInactivePlugins() {
        return inactivePlugins;
    }
    //Edit data
    public static void addActivePlugin(String pluginName){
        activePlugins.add(pluginName);
    }
    public static void removeActivePlugin(String pluginName){
        activePlugins.remove(pluginName);
    }
    public static void addInactivePlugin(String pluginName){
        inactivePlugins.add(pluginName);
    }
    public static void removeInactivePlugin(String pluginName){
        inactivePlugins.remove(pluginName);
    }
}
