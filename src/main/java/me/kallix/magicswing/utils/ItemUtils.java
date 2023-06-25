package me.kallix.magicswing.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.kallix.magicswing.Config;
import me.kallix.magicswing.MagicSwing;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemUtils {

    private static final Config CONFIG = MagicSwing.getInstance().getConfiguration();
    private static final String SWING_ITEM_KEY = "swingItem";

    public static ItemStack createSwingItem() {

        ItemStack stack = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(MessageUtils.translate(CONFIG.getMetaDisplayName()));
        meta.setLore(MessageUtils.translate(CONFIG.getMetaLore()));

        stack.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(stack);
        nbtItem.setBoolean(SWING_ITEM_KEY, true);

        return nbtItem.getItem();
    }

    public static ItemStack getAtSlot(Player player, int slot) {
        return player.getInventory().getItem(slot);
    }

    public static boolean isSwingItem(ItemStack stack) {
        return stack != null && stack.getType() == Material.FISHING_ROD && new NBTItem(stack).hasTag(SWING_ITEM_KEY);
    }
}
