package me.kallix.magicswing;

import lombok.Getter;
import me.kallix.magicswing.cmd.SwingCommand;
import me.kallix.magicswing.task.SwingManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicSwing extends JavaPlugin {

    @Getter
    private static MagicSwing instance;

    @Getter
    private Config configuration;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        reloadConfig();

        configuration = new Config(getConfig());

        getCommand("magicswing").setExecutor(new SwingCommand());
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
        SwingManager.dispose();
        configuration = null;
        instance = null;
    }
}
