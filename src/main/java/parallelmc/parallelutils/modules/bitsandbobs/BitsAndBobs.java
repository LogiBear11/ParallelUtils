package parallelmc.parallelutils.modules.bitsandbobs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import parallelmc.parallelutils.Constants;
import parallelmc.parallelutils.ParallelModule;
import parallelmc.parallelutils.Parallelutils;
import parallelmc.parallelutils.modules.bitsandbobs.minimodules.*;

import java.util.logging.Level;

public class BitsAndBobs implements ParallelModule {
    @Override
    public void onEnable() {
        PluginManager manager = Bukkit.getPluginManager();
        Plugin plugin = manager.getPlugin(Constants.PLUGIN_NAME);

        if (plugin == null) {
            Parallelutils.log(Level.SEVERE, "Unable to enable BitsAndBobs. Plugin " + Constants.PLUGIN_NAME + " does not exist!");
            return;
        }

        Parallelutils puPlugin = (Parallelutils) plugin;

        if (!puPlugin.registerModule("BitsAndBobs", this)) {
            Parallelutils.log(Level.SEVERE, "Unable to register module BitsAndBobs! Module may already be registered. Quitting...");
            return;
        }

        FileConfiguration config = puPlugin.getConfig();

        //manager.registerEvents(new DoorKnocker(), plugin);
        manager.registerEvents(new SpecialItems(), plugin);
        //manager.registerEvents(new SpeedyMinecarts(), plugin);

        if (config.getBoolean("disable-ender-chests", false)) {
            manager.registerEvents(new DisableEnderChest(), plugin);
        }

        if (config.getBoolean("prevent-spawner-mining", false)) {
            manager.registerEvents(new PreventSpawnerMining(), plugin);
        }
    }

    @Override
    public void onDisable() {

    }
}