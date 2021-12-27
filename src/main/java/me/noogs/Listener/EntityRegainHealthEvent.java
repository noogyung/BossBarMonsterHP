package me.noogs.Listener;

import me.noogs.HoloHpBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;


public class EntityRegainHealthEvent implements Listener {
    Plugin plugin = HoloHpBar.getPlugin();
    FileConfiguration config = plugin.getConfig();

    @EventHandler
    public void onEntityRegain(org.bukkit.event.entity.EntityRegainHealthEvent event){
        if (event.getEntity() instanceof LivingEntity){
            LivingEntity entity = (LivingEntity) event.getEntity();
            String entityName = String.valueOf(entity.getType());

            double entityhealth = entity.getHealth() + event.getAmount();
            entity.setCustomName(entityName + " " + config.getString("HPName") + EntityDamageEvent.removeDecimal(entityhealth));
            entity.setCustomNameVisible(true);

            if (entity.isDead()){
                entity.setCustomName(null);
                entity.setCustomNameVisible(false);
            }
        }
    }
}
