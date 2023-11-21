package de.lundlucenany9.scoreboard.commands;

import de.lundlucenany9.scoreboard.Scoreboard;
import de.lundlucenany9.scoreboard.utils.ScoreboardEntry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if((!sender.hasPermission("scoreboard.job.other") || args.length == 0)){
            if(!(sender instanceof Player)){
                sender.sendMessage(Scoreboard.mNotPlayer);
                return false;
            }
            ScoreboardEntry entry = Scoreboard.get((Player) sender);
            sender.sendMessage("Dein Job ist aktuell§1 " + entry.getJob() + "!");
            return true;
        }
        if(args.length > 2){
            sender.sendMessage(Scoreboard.mWrongArguments);
            return false;
        }
        if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))){
            sender.sendMessage(Scoreboard.mPlayerNotOnline);
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        ScoreboardEntry entry = Scoreboard.get(target);
        if(args.length == 1 || !sender.hasPermission("scoreboard.job.other.change")){
            sender.sendMessage("Der Job von " + target.getDisplayName() + " ist aktuell §1" + entry.getJob());
            return true;
        }
        entry.setJob(args[1]);
        Scoreboard.scoreboardList.replace(target.getUniqueId().toString(), entry);
        Scoreboard.scoreboardUpdate(target);
        sender.sendMessage("Der Job von " + target.getDisplayName() + " ist aktuell §1" + entry.getJob());
        return true;
    }
}
