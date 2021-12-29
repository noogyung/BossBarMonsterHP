package me.noogs;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class mainCommand implements CommandExecutor {
    Plugin plugin = BossBarMonsterHP.getPlugin();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        PluginDescriptionFile pdf = plugin.getDescription();

        if(args.length > 0){
            String inputCommand = args[0];
            switch (inputCommand){
                case "reload":
                    plugin.reloadConfig();
                    plugin.onDisable();
                    plugin.onEnable();
                    sender.sendMessage(ChatColor.GREEN +"BossBarMonsterHP Plugin is reloaded!");
                    break;
                default:
                    break;
            }
        }
        return true;
    }
}
