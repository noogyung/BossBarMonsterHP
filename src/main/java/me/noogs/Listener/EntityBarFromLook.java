package me.noogs.Listener;

import me.noogs.BossBarMonsterHP;
import me.noogs.Utils.Targeter;
import me.noogs.Utils.refreshBar;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;


public class EntityBarFromLook implements Listener {
    private Plugin plugin = BossBarMonsterHP.getPlugin();
    private Configuration config = plugin.getConfig();
    private HashMap<LivingEntity, BossBar> bars = BossBarMonsterHP.bars;
    private boolean isTask = false;

    //커스텀 네임이 보스바가 만들어지고 나서 생겼을 경우 이름이 정상적으로 안보임
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        if(Targeter.getTargetEntity(player) != null){
            if(Targeter.getTargetEntity(player) instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) Targeter.getTargetEntity(player);
                //거리 제한
                if(player.getLocation().distanceSquared(entity.getLocation()) <= 80){
                    if(!(entity instanceof Wither) || !(entity instanceof EnderDragon)){
                        double entityMaxHP = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                        double entityHP = entity.getHealth();
                        getBar getBar = new getBar();

                        BossBar bar = bars.get(entity);
                        double maxHpProgress = Math.max(0.0D, Math.min(1.0D, 1.0D / entityMaxHP * entityHP));
                        if(bar == null) {
                            bar = getBar.makeBar(entity);
                            bar.setProgress(maxHpProgress);
                            bars.put(entity, bar);
                        }
                        //커스텀 네임 처리
                        else if(entity.getCustomName() != null && entity.getScoreboardTags().isEmpty()){
                            new refreshBar(player);
                            bar = getBar.makeBar(entity);;
                            bar.setProgress(maxHpProgress);
                            bars.put(entity, bar);
                            entity.addScoreboardTag("named");
                        }

                        bar.addPlayer(player);
                        bar.setProgress(Math.max(1.0D / entityMaxHP * entityHP, 0.0D));

                    }
                }
            }
        } else {
            if (isTask == false) { //false면 실행
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        new refreshBar(player);
                        isTask = false;
                    }
                }, config.getLong("duration") / 2);
            }
            isTask = true;
        }
    }
}
