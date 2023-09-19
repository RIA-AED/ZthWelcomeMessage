package ink.magma.zthwelcomemessage;

import ink.magma.zthwelcomemessage.command.WelcomeCommand;
import ink.magma.zthwelcomemessage.reader.MessageReader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ZthWelcomeMessage extends JavaPlugin implements Listener {
    public static JavaPlugin instance;
    public static boolean enablePlaceholder = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();


        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("已找到 PlaceholderAPI 插件，启用变量功能.");
            enablePlaceholder = true;
        }

        if (Bukkit.getPluginCommand("welcome-msg") != null) {
            Bukkit.getPluginCommand("welcome-msg").setExecutor(new WelcomeCommand());
        }

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List<String> welcomeMessage = MessageReader.getWelcomeMessage(player);
        for (String messageLine : welcomeMessage) {
            player.sendMessage(messageLine);
        }
    }

}
