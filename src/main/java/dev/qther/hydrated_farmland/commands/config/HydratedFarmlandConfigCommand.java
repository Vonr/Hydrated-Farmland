package dev.qther.hydrated_farmland.commands.config;

import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

public class HydratedFarmlandConfigCommand extends AbstractCommandCollection {
    public HydratedFarmlandConfigCommand() {
        super("config", "View or change configured values");
        this.addAliases("cfg");
        this.setPermissionGroup(GameMode.Creative);
        this.addSubCommand(new HydratedFarmlandConfigDistanceCommand());
        this.addSubCommand(new HydratedFarmlandConfigTicksCommand());
    }
}
