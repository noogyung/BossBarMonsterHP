package me.noogs;

import me.noogs.Listener.EntityDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class HoloHpBar extends JavaPlugin {

    FileConfiguration config;
    private static HoloHpBar plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("HoloHpBar Plugin has started.");


        plugin = this;

        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();


        //Command
        getCommand("holohp").setExecutor(new mainCommand());

        //Event
        Bukkit.getPluginManager().registerEvents(new EntityDamageEvent(), this);

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("HoloHpBar Plugin has stopped.");
    }


    public static HoloHpBar getPlugin(){ return plugin;}
}
