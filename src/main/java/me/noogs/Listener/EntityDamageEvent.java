package me.noogs.Listener;

import me.noogs.BossBarMonsterHP;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;


public class EntityDamageEvent implements Listener {
    Plugin plugin = BossBarMonsterHP.getPlugin();
    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard scoreBoard = manager.getNewScoreboard();;
    Objective objective = null;
    String entityName = null;

    @EventHandler
    public void damage(EntityDamageByEntityEvent event){
        LivingEntity entity;
        entity = (LivingEntity) event.getEntity();
        entityName = entity.getName();

        if (event.getDamager() instanceof Player || event.getDamager() instanceof Arrow && event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)){
            double entityMaxHP = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            entity.setRemoveWhenFarAway(true);

            double entityhealth = entity.getHealth() - event.getDamage();

            if (entityhealth < 0.0) entityhealth = 0;

            entity.setCustomName(entityName + " " + (int)entityhealth + " / " + (int)entityMaxHP);
            entity.setCustomNameVisible(true);

            LivingEntity finalEntity = entity;

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    finalEntity.setCustomName(entityName);
                    finalEntity.setCustomNameVisible(false);
                }
            }, 60L);

            if (entity.getHealth() < 0){
                entity.setCustomName(null);
                entity.setCustomNameVisible(false);
            }
        }
        if (event.getEntity() instanceof Player){
            entity = (LivingEntity) event.getEntity();
            String entityName = entity.getName();

            if (objective == null){
                objective = scoreBoard.registerNewObjective(entityName, "health", "/ 20");
                objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }

            for (Player online : Bukkit.getOnlinePlayers()){
                online.setScoreboard(scoreBoard);
                online.setHealth(online.getHealth());
            }


        }
    }

    public static double removeDecimal(double number){
        double result = Math.round(number*100)/100.0;
        return result;
    }

}
