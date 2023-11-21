package de.lundlucenany9.scoreboard.money.commands;

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
import java.util.Objects;

public class EcoCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length != 3){
            sender.sendMessage(Scoreboard.mWrongArguments);
            return false;
        }
        if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))){
            sender.sendMessage(Scoreboard.mPlayerNotOnline);
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        sender.sendMessage(target.getDisplayName());
        if(Objects.equals(args[1], "add")){
            ScoreboardEntry entry = Scoreboard.scoreboardList.get(target.getUniqueId().toString());
            entry.changeMoney(Float.parseFloat(args[2]));
            Scoreboard.scoreboardList.replace(target.getUniqueId().toString(), entry);
            sender.sendMessage("Das Geld von " + target.getDisplayName() + " ist jetzt " + entry.getMoney());
            new OnJoinListener().updateEntrys(target);
            return true;
        } else if (Objects.equals(args[1], "take")) {
            ScoreboardEntry entry = Scoreboard.scoreboardList.get(target.getUniqueId().toString());
            entry.changeMoney(-Float.parseFloat(args[2]));
            Scoreboard.scoreboardList.replace(target.getUniqueId().toString(), entry);
            sender.sendMessage("Das Geld von " + target.getDisplayName() + " ist jetzt " + entry.getMoney());
            new OnJoinListener().updateEntrys(target);
            return true;
        } else if (Objects.equals(args[1], "set")) {
            ScoreboardEntry entry = Scoreboard.scoreboardList.get(target.getUniqueId().toString());
            entry.setMoney(Float.parseFloat(args[2]));
            Scoreboard.scoreboardList.replace(target.getUniqueId().toString(), entry);
            sender.sendMessage("Das Geld von " + target.getDisplayName() + " ist jetzt " + entry.getMoney());
            new OnJoinListener().updateEntrys(target);
            return true;
        } else if (Objects.equals(args[1], "get")) {
            ScoreboardEntry entry = Scoreboard.scoreboardList.get(target.getUniqueId().toString());
            sender.sendMessage("Das Geld von " + target.getDisplayName() + " ist aktuell " + entry.getMoney());
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
            List<String> back = new ArrayList<>();
            back.add("add");
            back.add("take");
            back.add("set");
            back.add("get");
            return back;
        }
        return null;
    }
}
