package org.winterdev.SakuraHeads;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.winterdev.SakuraHeads.Commands.SHeads;
import org.winterdev.SakuraHeads.Listeners.HeadsOnDeath;

public final class SakuraHeads extends JavaPlugin {

    private static SakuraHeads plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("sheads").setExecutor(new SHeads());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new HeadsOnDeath(), this);

        getLogger().info("SakuraChat is enabled!");
    }

    public static SakuraHeads getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        getLogger().info("SakuraChat is disabled!");
    }
}
