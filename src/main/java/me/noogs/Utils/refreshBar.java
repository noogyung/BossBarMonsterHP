package me.noogs.Utils;

import me.noogs.BossBarMonsterHP;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Iterator;
import java.util.Map;

public class refreshBar {
    public refreshBar(Player player) {
        //bar가 따로 사라지는건 좋은데, task가 계속 호출되어서 나중에 끊기게 나오게 됨.
        BukkitTask task = BossBarMonsterHP.bartasks.get(player);
        if (task != null) {
            task.cancel();
            BossBarMonsterHP.bartasks.remove(player);
        }
        Iterator it = BossBarMonsterHP.bars.entrySet().iterator();
        BukkitTask newtask = null;
        while (it.hasNext()) {
            Map.Entry<LivingEntity, BossBar> entry = (Map.Entry<LivingEntity, BossBar>) it.next();
            BossBar v = entry.getValue();
            if (v.getPlayers().contains(player)) {
                v.removePlayer(player);
                if (v.getPlayers().isEmpty()) {
                    if(it.hasNext()){
                        it.remove();
                        BossBarMonsterHP.bartasks.remove(player);

                    }
                }
            }
        }
        if(newtask != null){
            BossBarMonsterHP.bartasks.put(player, newtask);
        }
    }
}
