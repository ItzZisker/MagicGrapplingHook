package me.kallix.magicswing.task;

import lombok.RequiredArgsConstructor;
import me.kallix.magicswing.Config;
import me.kallix.magicswing.MagicSwing;
import me.kallix.magicswing.utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public final class SwingManager extends BukkitRunnable {

    private static final Map<Player, SwingManager> swinging = new ConcurrentHashMap<>();

    private final Config config;
    private final Player player;
    private final Entity hook;
    private final Location blockLoc;

    private int ticks = 0;

    public static boolean isSwinging(Player player) {
        return swinging.containsKey(player);
    }

    public static void toggle(Player player, Entity hook, Block block) {
        if (!isSwinging(player)) {
            MagicSwing plugin = MagicSwing.getInstance();
            SwingManager runnable = new SwingManager(plugin.getConfiguration(), player, hook, block.getLocation());

            runnable.runTaskTimer(plugin, 0, 1);

            swinging.put(player, runnable);
        } else {
            dispose(player);
        }
    }

    public static void dispose(Player player) {
        if (isSwinging(player)) {
            swinging.remove(player).stop();
        }
    }

    public static void dispose() {
        swinging.forEach((k, v) -> v.stop());
        swinging.clear();
    }

    @Override
    public void run() {
        Location playerLoc = player.getLocation();
        Vector velocity = player.getVelocity();

        if (ticks == 0) {
            player.setVelocity(velocity.add(blockLoc.toVector().subtract(playerLoc.toVector())
                    .normalize().multiply(config.getLaunch())));
        } else {
            double dx = playerLoc.getX() - blockLoc.getX();
            double dz = playerLoc.getZ() - blockLoc.getZ();

            Vector dir = MathUtils.getSphericalCoordinate(playerLoc.toVector(), blockLoc.toVector(),
                            (float) (Math.PI * config.getAcceleration() * Math.sqrt(dx * dx + dz * dz)))
                    .subtract(playerLoc.toVector())
                    .normalize()
                    .multiply(config.getForce());

            if (player.getFallDistance() > 0) {
                dir.setY(dir.getY() + getAppliedGravityY(player.getFallDistance()));
                player.setFallDistance(0);
            }
            player.setVelocity(velocity.add(dir));
        }

        if (ticks++ % 10 == 0 && ticks > 20) {
            double distance = playerLoc.distanceSquared(blockLoc);
            hook.teleport(blockLoc);
            if (playerLoc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid() ||
                    distance < config.getMinDistance() * config.getMinDistance() ||
                    distance >= config.getMaxDistance() * config.getMaxDistance()) {
                stop();
            }
        }
    }

    public void stop() {
        hook.remove();
        swinging.remove(player);
        super.cancel();
    }

    private float getAppliedGravityY(float fallDistance) {
        return (float) Math.sqrt(fallDistance * config.getGravity());
    }
}
