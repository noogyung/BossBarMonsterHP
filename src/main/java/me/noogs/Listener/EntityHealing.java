package me.noogs.Listener;

import me.noogs.BossBarMonsterHP;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.HashMap;

public class EntityHealing implements Listener {
    private HashMap<LivingEntity, BossBar> bars = BossBarMonsterHP.bars;

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity)event.getEntity();
            if (this.bars.containsKey(entity)) {
                double entityMaxHp = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                double hp = entity.getHealth() + event.getAmount();
                BossBar bar = bars.get(entity);
                bar.setProgress(Math.max(0.0D, Math.min(1.0D, 1.0D / entityMaxHp * hp)));
            }

        }
    }
}
