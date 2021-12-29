package me.noogs;

import me.noogs.Listener.*;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.HashMap;

public final class BossBarMonsterHP extends JavaPlugin {

    FileConfiguration config;
    private static BossBarMonsterHP plugin;
    public static HashMap<LivingEntity, BossBar> bars = new HashMap();
    public static HashMap<Player, BukkitTask> bartasks = new HashMap();


    @Override
    public void onEnable() {
        getLogger().info("BossBarMonsterHP Plugin has started.");

        File file = new File(getDataFolder() + File.separator + "config.yml");
        config = getConfig();
        if(!file.exists()){
            config.options().copyDefaults(true);
            saveConfig();
        }
        else {
            saveConfig();
            reloadConfig();
        }

        plugin = this;

        //Command
        getCommand("bbmp").setExecutor(new mainCommand());

        //Event
        Bukkit.getPluginManager().registerEvents(new EntityBarFromAttack(), this);
        Bukkit.getPluginManager().registerEvents(new EntityBarFromLook(), this);
        Bukkit.getPluginManager().registerEvents(new EntityHealing(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDeath(), this);
        Bukkit.getPluginManager().registerEvents(new playerBar(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("BossBarMonsterHP Plugin has stopped.");
    }


    public static BossBarMonsterHP getPlugin(){ return plugin;}
}
