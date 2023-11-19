package de.lundlucenany9.scoreboard.money.commands;

import de.lundlucenany9.scoreboard.Scoreboard;
import de.lundlucenany9.scoreboard.listeners.OnJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PayCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Scoreboard.mNotPlayer);
            return false;
        }
        Player p = (Player) sender;
        if(args.length != 2){
            sender.sendMessage(Scoreboard.mWrongArguments);
            return false;
        }
        float money = Float.parseFloat(args[1]);
        if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))){
            sender.sendMessage(Scoreboard.mPlayerNotOnline);
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(Scoreboard.scoreboardList.get(p.getUniqueId()).getMoney() < money){
            sender.sendMessage(Scoreboard.mTooLessMoney);
            return false;
        }
        Scoreboard.scoreboardList.get(p.getUniqueId()).changeMoney(-money);
        Scoreboard.scoreboardList.get(target.getUniqueId()).changeMoney(money);
        new OnJoinListener().updateEntrys(p);
        new OnJoinListener().updateEntrys(target);
        return true;
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
            List<String > back = new ArrayList<>();
            back.add("10");
            back.add("20");
            back.add("50");
            back.add("100");
            return back;
        }
        return null;
    }
}
