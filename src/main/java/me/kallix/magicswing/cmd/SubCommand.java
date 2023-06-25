package me.kallix.magicswing.cmd;

import lombok.Getter;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    @Getter
    private final String[] alias;

    public SubCommand(String... alias) {
        this.alias = alias;
    }

    public abstract void execute(Player player, String[] args);
}
