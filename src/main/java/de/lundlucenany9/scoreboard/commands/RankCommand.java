package de.lundlucenany9.scoreboard.commands;

import de.lundlucenany9.scoreboard.Scoreboard;
import de.lundlucenany9.scoreboard.listeners.OnJoinListener;
import de.lundlucenany9.scoreboard.utils.ScoreboardEntry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RankCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender cmdsender, Command command, String s, String[] args) {
        if(args.length > 2 || args.length < 1){
            cmdsender.sendMessage(Scoreboard.mWrongArguments);
            return false;
        }
        if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))){
            cmdsender.sendMessage(Scoreboard.mPlayerNotOnline);
            return false;
        }
        if(args.length == 2 && !Scoreboard.ranklist.contains(args[1])){
            cmdsender.sendMessage(Scoreboard.mRankDoesNotExist);
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(Scoreboard.scoreboardList.containsKey(target.getUniqueId())){
            ScoreboardEntry entry = Scoreboard.scoreboardList.get(target.getUniqueId());
            if(args.length == 1){
                cmdsender.sendMessage("Rang von " + target.getDisplayName() + ": "  + entry.getRang());
                return true;
            }else {
                entry.setRang(Scoreboard.ranks.get(args[1]));
                new OnJoinListener().updateEntrys(target);
                cmdsender.sendMessage("Rang von " + target.getDisplayName() + " ist jetzt " + entry.getRang());
                Scoreboard.scoreboardList.replace(target.getUniqueId(), entry);
                return true;
            }

        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if(args.length == 1){
            List<String> back = new ArrayList<>();
            List<Player> players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
            for (Player p: players) {
                back.add(p.getName());
            }
            return back;
        }
        if(args.length == 2){
            return Scoreboard.ranklist;
        }
        return null;
    }
}
