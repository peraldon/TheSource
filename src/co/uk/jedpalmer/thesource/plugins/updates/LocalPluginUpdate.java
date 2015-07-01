package co.uk.jedpalmer.thesource.plugins.updates;

import co.uk.jedpalmer.thesource.TheSource;
import co.uk.jedpalmer.thesource.utils.console.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Created by peraldon on 30/06/2015.
 */
public class LocalPluginUpdate {
    //@Param: file is the name of the file to move the updated plugin to
    public static void update(String file, Plugin plugin){
        InputStream is = null;
        OutputStream os = null;
        try {
            //Get inputstream
            is = new FileInputStream(Bukkit.getPluginManager().getPlugin("TheSource").getConfig().get("updates.address").toString() + "\\" + file);
            try {
                //Get outputstream
                os = new FileOutputStream(TheSource.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            } catch (URISyntaxException ex){
                ex.printStackTrace();
            }
            MessageManager.sendDebug("Plugin update coming from: " + Bukkit.getPluginManager().getPlugin("TheSource").getConfig().get("updates.address").toString() + "\\" + file, TheSource.getTheSourcePlugin());
            byte[] buffer = new byte[1024];
            int length;
            //Transfers file
            while ((length = is.read(buffer)) > 0) {
                try {
                    os.write(buffer, 0, length);
                    MessageManager.sendDebug("Getting... " + length, TheSource.getTheSourcePlugin());
                } catch (NullPointerException ex) {
                    MessageManager.sendSevere("is.read(buffer) while loop throwing a NPE; cannot download plugin " + plugin.getName());
                }
            }
            //Closes streams
            is.close();
            os.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
