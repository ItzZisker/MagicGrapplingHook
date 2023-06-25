package me.kallix.magicswing;

import me.kallix.magicswing.task.SwingManager;
import me.kallix.magicswing.utils.ItemUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.bukkit.event.player.PlayerFishEvent.State;

public final class Listeners implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        SwingManager.dispose(event.getPlayer());
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        if (ItemUtils.isSwingItem(ItemUtils.getAtSlot(player, event.getPreviousSlot())) &&
            !ItemUtils.isSwingItem(ItemUtils.getAtSlot(player, event.getNewSlot()))) {
            SwingManager.dispose(player);
        }
    }

    @EventHandler
    public void onClick(InventoryOpenEvent event) {
        SwingManager.dispose((Player) event.getPlayer());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        SwingManager.dispose((Player) event.getPlayer());
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {

        if (event.getState() == State.FISHING || !ItemUtils.isSwingItem(event.getPlayer().getItemInHand())) {
            return;
        }

        FishHook hook = event.getHook();
        Location loc = hook.getLocation();

        World world = loc.getWorld();

        int x0 = loc.getBlockX() - 1, x1 = loc.getBlockX() + 1;
        int y0 = loc.getBlockY() - 1, y1 = loc.getBlockY() + 1;
        int z0 = loc.getBlockZ() - 1, z1 = loc.getBlockZ() + 1;

        List<Block> blocks = new ArrayList<>();

        for (int i = x0; i <= x1; i++) {
            for (int j = y0; j <= y1; j++) {
                for (int k = z0; k <= z1; k++) {
                    Block target = world.getBlockAt(i, j, k);
                    if (target.getType() != Material.AIR) {
                        blocks.add(target);
                    }
                }
            }
        }
        if (!blocks.isEmpty()) {
            event.setCancelled(true);
            blocks.sort(Comparator.comparingDouble(a -> a.getLocation().distanceSquared(loc)));

            SwingManager.toggle(event.getPlayer(), hook, blocks.get(0));
        }
    }
}
