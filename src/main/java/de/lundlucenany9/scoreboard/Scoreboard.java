package de.lundlucenany9.scoreboard;

import de.lundlucenany9.scoreboard.commands.GetCommand;
import de.lundlucenany9.scoreboard.commands.JobCommand;
import de.lundlucenany9.scoreboard.commands.RankCommand;
import de.lundlucenany9.scoreboard.listeners.OnDeathListener;
import de.lundlucenany9.scoreboard.listeners.OnJoinListener;
import de.lundlucenany9.scoreboard.money.commands.EcoCommand;
import de.lundlucenany9.scoreboard.money.commands.PayCommand;
import de.lundlucenany9.scoreboard.utils.ScoreboardEntry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public final class Scoreboard extends JavaPlugin {

    public static Map<String, ScoreboardEntry> scoreboardList;
    public static String serverName;
    public static Map<String,String> ranks;
    public static List<String> ranklist;

    //messages
    public static String mWrongArguments;
    public static String mPlayerNotOnline;
    public static String mRankDoesNotExist;
    public static String mTooLessMoney;
    public static String mNotPlayer;
    public static String money;
    public static String saveFile;

    PluginManager manager = this.getServer().getPluginManager();

    @Override
    public void onEnable() {
        this.saveResource("config.yml", false);
        saveFile = this.getConfig().getString("savefile");
        ranks = new HashMap<>();
        File file = new File(this.getDataFolder(), "/" + saveFile);
        if(file.exists()){
            try {
                InputStream stream = Files.newInputStream(file.toPath());
                ObjectInputStream objectStream = new ObjectInputStream(stream);
                scoreboardList = (Map<String, ScoreboardEntry>) objectStream.readObject();
                objectStream.close();
                stream.close();
                Bukkit.getLogger().info(scoreboardList.toString());
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        } else
            scoreboardList = new HashMap<>();


        serverName = this.getConfig().getString("serverName");
        ranklist = this.getConfig().getStringList("ranks");
        for (String rank : ranklist) {
            ranks.put(rank, this.getConfig().getString("rankDisplay." + rank));
        }
        mWrongArguments = this.getConfig().getString("wrongAmountArguments");
        mPlayerNotOnline = this.getConfig().getString("playerNotOnline");
        mRankDoesNotExist = this.getConfig().getString("rankDoesNotExist");
        mTooLessMoney = this.getConfig().getString("tooLessMoney");
        mNotPlayer = this.getConfig().getString("notPlayer");
        money = this.getConfig().getString("money");

        manager.registerEvents(new OnJoinListener(), this);
        manager.registerEvents(new OnDeathListener(), this);
        //Commands
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("rank").setTabCompleter(new RankCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("pay").setTabCompleter(new PayCommand());
        getCommand("eco").setExecutor(new EcoCommand());
        getCommand("eco").setTabCompleter(new EcoCommand());
        getCommand("get").setExecutor(new GetCommand());
        getCommand("job").setExecutor(new JobCommand());
    }

    @Override
    public void onDisable() {
        if(!scoreboardList.isEmpty()){
            if(!this.getDataFolder().exists())
                this.getDataFolder().mkdir();
            File file = new File(this.getDataFolder(), "/" + saveFile);
            try{
                OutputStream stream = Files.newOutputStream(file.toPath());
                ObjectOutputStream objectStream = new ObjectOutputStream(stream);
                objectStream.writeObject(scoreboardList);
                objectStream.close();
                stream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public static ScoreboardEntry get (Player p){

        return scoreboardList.getOrDefault(p.getUniqueId().toString(), new ScoreboardEntry(Scoreboard.ranks.get("player")));
    }
    public static void scoreboardUpdate(Player p){
        new OnJoinListener().updateEntrys(p);
    }
}
