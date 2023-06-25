package me.kallix.magicswing.cmd;

import me.kallix.magicswing.cmd.sub.SubGive;
import me.kallix.magicswing.cmd.sub.SubReload;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class SwingCommand implements CommandExecutor {

    private final SubCommand[] subCommands = new SubCommand[] {new SubGive(), new SubReload()};

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may execute this command!");
            return true;
        }
        for (SubCommand subCommand : subCommands) {
            for (String name : subCommand.getAlias()) {
                if (name.startsWith(args[0].toLowerCase())) {
                    subCommand.execute((Player) sender, args);
                    return true;
                }
            }
        }
        return false;
    }
}
