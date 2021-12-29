package me.noogs.Utils;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;

import static me.noogs.BossBarMonsterHP.bars;

public class destroyBar {
    public destroyBar(LivingEntity entity){
        BossBar bar = bars.get(entity);
        if (bar != null){
            bar.removeAll();
        }
    }
}
