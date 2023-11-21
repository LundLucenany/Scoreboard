package de.lundlucenany9.scoreboard.listeners;

import de.lundlucenany9.scoreboard.Scoreboard;
import de.lundlucenany9.scoreboard.utils.ScoreboardEntry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnDeathListener implements Listener {
    @EventHandler
    void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        ScoreboardEntry entry = Scoreboard.get(p);
        entry.changeDeaths(1);
        Scoreboard.scoreboardList.replace(p.getUniqueId().toString(), entry);
        Scoreboard.scoreboardUpdate(p);
    }
}
