package me.kallix.magicswing;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@Getter
public final class Config {

    private float force;
    private float acceleration;
    private float launch;
    private float minDistance;
    private float maxDistance;
    private float gravity;

    private String metaDisplayName;
    private List<String> metaLore;

    public Config(FileConfiguration fileConfig) {
        reload(fileConfig);
    }

    public void reload(FileConfiguration fileConfig) {

        metaDisplayName = fileConfig.getString("item.name");
        metaLore = fileConfig.getStringList("item.lore");

        force = (float) fileConfig.getDouble("physics.force", 0.04F);
        acceleration = (float) fileConfig.getDouble("physics.acceleration", 0.4F);
        launch = (float) fileConfig.getDouble("physics.launch", 1.35F);
        minDistance = (float) fileConfig.getDouble("physics.minDistance", 2.0F);
        maxDistance = (float) fileConfig.getDouble("physics.maxDistance", 16.0F);
        gravity = (float) fileConfig.getDouble("physics.gravity", 0.125F);
    }
}
