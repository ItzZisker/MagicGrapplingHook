package me.kallix.magicswing.cmd.sub;

import me.kallix.magicswing.MagicSwing;
import me.kallix.magicswing.cmd.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class SubReload extends SubCommand {

    public SubReload() {
        super("reload", "reloadConfig");
    }

    @Override
    public void execute(Player player, String[] args) {
        MagicSwing plugin = MagicSwing.getInstance();

        plugin.reloadConfig();
        plugin.getConfiguration().reload(plugin.getConfig());

        player.sendMessage(ChatColor.GREEN + "Successfully reloaded configuration file!");
    }
}
