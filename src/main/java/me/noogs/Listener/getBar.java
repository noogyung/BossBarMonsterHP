package me.noogs.Listener;

import me.noogs.BossBarMonsterHP;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

import java.util.Iterator;

public class getBar {

    Plugin plugin = BossBarMonsterHP.getPlugin();
    Configuration config = plugin.getConfig();

    public BossBar makeBar(LivingEntity entity){
        BarColor color = BarColor.RED;
        BarStyle style = BarStyle.SOLID;

        Iterator var4 = config.getConfigurationSection("styles").getKeys(false).iterator();

        while(var4.hasNext()){
            String item = (String) var4.next();
            try {
                if (Class.forName("org.bukkit.entity." + item).isInstance(entity)) {
                    String cColor = config.getString("styles." + item + ".color");
                    String cStyle = config.getString("styles." + item + ".style");
                    if (cColor != null) color = BarColor.valueOf(cColor);
                    if (cStyle != null) style = BarStyle.valueOf(cStyle);
                    break;
                }
            }catch (Exception var8){
                plugin.getLogger().severe(item + " is not a valid entity type.");
                break;
            }
        }
        return Bukkit.createBossBar(entity.getName(), color, style, new BarFlag[0]);
    }
}
