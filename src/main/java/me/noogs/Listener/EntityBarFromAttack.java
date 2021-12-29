package me.noogs.Listener;

import me.noogs.BossBarMonsterHP;
import me.noogs.Utils.destroyBar;
import me.noogs.Utils.refreshBar;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class EntityBarFromAttack implements Listener {
    private HashMap<LivingEntity, BossBar> bars = BossBarMonsterHP.bars;
    private Plugin plugin = BossBarMonsterHP.getPlugin();
    private Configuration config = plugin.getConfig();
    private boolean isTask = false;

    @EventHandler
    public void damage(EntityDamageByEntityEvent event){

        //플레이어가 공격했거나 프로젝타일이 공격했을 경우
        if (event.getDamager() instanceof Player || event.getDamager() instanceof Projectile && !(event.getEntity() instanceof Player)){
            //예외처리
            Player player;
            if (event.getEntity() instanceof Wither) {return;}
            if (event.getEntity() instanceof EnderDragon) {return;}
            if (!(event.getEntity() instanceof LivingEntity)){return;}
            //플레이어 처리
            if(event.getDamager() instanceof Projectile){
                if (((Projectile) event.getDamager()).getShooter() instanceof Player){
                    player = (Player) ((Projectile) event.getDamager()).getShooter();
                }
                else return;
            } else player = (Player) event.getDamager();

            LivingEntity entity = (LivingEntity) event.getEntity();
            if (entity == player) {return;}

            double entityMaxHP = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double entityhealth = entity.getHealth() - event.getFinalDamage();
            getBar getBar = new getBar();


            BossBar bar = bars.get(entity);
            double maxHpProgress = Math.max(0.0D, Math.min(1.0D, 1.0D / entityMaxHP * entityhealth));
            if(bar == null) {
                bar = getBar.makeBar(entity);
                bar.setProgress(maxHpProgress);
                bars.put(entity, bar);
            }else if(entity.getCustomName() != null && entity.getScoreboardTags().isEmpty()){
                new refreshBar(player);
                bar = getBar.makeBar(entity);
                bar.setProgress(maxHpProgress);
                bars.put(entity, bar);
                entity.addScoreboardTag("named");
            }

            bar.addPlayer(player);
            bar.setProgress(Math.max(1.0D / entityMaxHP * entityhealth, 0.0D));

            if (isTask == false) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        new refreshBar(player);
                        isTask = false;
                    }
                }, config.getLong("duration"));
            }
            isTask = true;

            if(entity.isDead()){
                new destroyBar(entity);
            }
        }
    }
}
