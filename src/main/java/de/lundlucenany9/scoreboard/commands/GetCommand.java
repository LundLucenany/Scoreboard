package de.lundlucenany9.scoreboard.commands;

import de.lundlucenany9.scoreboard.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length == 0)
            return false;
        if(!(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0])))){
            sender.sendMessage(Scoreboard.mPlayerNotOnline);
            return false;
        }
        sender.sendMessage(Scoreboard.get(Bukkit.getPlayer(args[0])).toString());
        return true;
    }
}
