package me.kallix.magicswing.cmd.sub;

import me.kallix.magicswing.cmd.SubCommand;
import me.kallix.magicswing.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public final class SubGive extends SubCommand {

    public SubGive() {
        super("give", "giveItem", "item");
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target != null && target.isOnline()) {
                PlayerInventory inv = target.getInventory();

                if (inv.firstEmpty() != -1) {
                    inv.addItem(ItemUtils.createSwingItem());
                } else {
                    player.sendMessage(ChatColor.RED + "Inventory is full!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Player not found!");
            }
        } else if (args.length == 1) {
            PlayerInventory inv = player.getInventory();

            if (inv.firstEmpty() != -1) {
                inv.addItem(ItemUtils.createSwingItem());
            } else {
                player.sendMessage(ChatColor.RED + "Inventory is full!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Usage: /swing give <player> | /swing give");
        }
    }
}
