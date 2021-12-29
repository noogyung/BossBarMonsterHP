package me.noogs.Listener;

import me.noogs.Utils.destroyBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){new destroyBar(event.getEntity());}
}
