package me.noogs.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class playerBar implements Listener {

    @EventHandler
    public void playerBar(PlayerJoinEvent event){
        // 플레이어일 경우 ScoreBoard를 통해 이름 밑에 생성
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreBoard = manager.getNewScoreboard();;
        Objective objective = null;

        String playerName = event.getPlayer().getName();

        if (objective == null){
            objective = scoreBoard.registerNewObjective(playerName, "health", "/ 20");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }

        for (Player online : Bukkit.getOnlinePlayers()){
            online.setScoreboard(scoreBoard);
            online.setHealth(online.getHealth());
        }
    }
}
