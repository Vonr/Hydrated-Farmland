package dev.qther.hydrated_farmland.commands;

import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import dev.qther.hydrated_farmland.commands.config.HydratedFarmlandConfigCommand;

public class HydratedFarmlandCommand extends AbstractCommandCollection {
    public HydratedFarmlandCommand() {
        super("hydratedfarmland", "Configure the Hydrated Farmland plugin");
        this.addAliases("hf");
        this.setPermissionGroup(GameMode.Creative);
        this.addSubCommand(new HydratedFarmlandConfigCommand());
    }
}
