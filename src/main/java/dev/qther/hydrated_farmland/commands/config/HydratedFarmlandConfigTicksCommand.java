package dev.qther.hydrated_farmland.commands.config;

import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import dev.qther.hydrated_farmland.HydratedFarmland;

import javax.annotation.Nonnull;

public class HydratedFarmlandConfigTicksCommand extends CommandBase {
    private final OptionalArg<Integer> distanceArg = this.withOptionalArg("set", "Distance", ArgTypes.INTEGER);

    public HydratedFarmlandConfigTicksCommand() {
        super("ticks", "Get or set Ticks value. Set to <= 0 to disable the plugin.");
        this.setPermissionGroup(GameMode.Creative);
    }

    @Override
    protected void executeSync(@Nonnull CommandContext ctx) {
        if (distanceArg.provided(ctx)) {
            var distance = distanceArg.get(ctx);
            HydratedFarmland.INSTANCE.config.get().ticks = distance;
            HydratedFarmland.INSTANCE.config.save();
            ctx.sendMessage(Message.raw("[Hydrated Farmland] Ticks set to " + distance));
        } else {
            ctx.sendMessage(Message.raw("[Hydrated Farmland] Ticks is currently " + HydratedFarmland.INSTANCE.config.get().ticks));
        }
    }
}
