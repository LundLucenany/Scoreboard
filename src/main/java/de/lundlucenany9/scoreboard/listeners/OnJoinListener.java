package de.lundlucenany9.scoreboard.listeners;

import de.lundlucenany9.scoreboard.Scoreboard;
import de.lundlucenany9.scoreboard.utils.ScoreboardEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

public class OnJoinListener implements Listener {
    private Objective sidebar;
    private static org.bukkit.scoreboard.Scoreboard scoreboard;
    @EventHandler
    void onJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();
        updateEntrys(p);
    }
    public void updateEntrys(Player p){
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (int i = 1; i <= 4; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }
        sidebar.setDisplayName(Scoreboard.serverName);
        if(Scoreboard.scoreboardList.containsKey(p.getUniqueId().toString())){
            ScoreboardEntry entry = Scoreboard.get(p);
            setSlot(4, "Rang: " + entry.getRang());
            setSlot(3, "Money: §2" + entry.getMoney() + Scoreboard.money);
            setSlot(2, "Tode: §4" + entry.getDeaths());
            setSlot(1, "Job: §1"+ entry.getJob());
            p.setScoreboard(scoreboard);
        } else {
            ScoreboardEntry entry = new ScoreboardEntry(Scoreboard.ranks.get("player"), 0f, 0, "None");
            Scoreboard.scoreboardList.put(p.getUniqueId().toString(), entry);
            setSlot(4, "Rang: " + entry.getRang());
            setSlot(3, "Money: §2" + entry.getMoney() + Scoreboard.money);
            setSlot(2, "Tode: §4" + entry.getDeaths());
            setSlot(1, "Job: §1"+ entry.getJob());
            p.setScoreboard(scoreboard);
        }

    }
    private String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }
    public void setSlot(int slot, String text) {
        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);
        if(!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(slot);
        }

        String pre = getFirstSplit(text);
        String suf = getFirstSplit(ChatColor.getLastColors(pre) + getSecondSplit(text));
        team.setPrefix(pre);
        team.setSuffix(suf);
    }
    private String getFirstSplit(String s) {
        return s.length()>16 ? s.substring(0, 16) : s;
    }

    private String getSecondSplit(String s) {
        if(s.length()>32) {
            s = s.substring(0, 32);
        }
        return s.length()>16 ? s.substring(16) : "";
    }
}
