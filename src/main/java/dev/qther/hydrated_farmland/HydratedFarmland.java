package dev.qther.hydrated_farmland;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import dev.qther.hydrated_farmland.commands.HydratedFarmlandCommand;
import dev.qther.hydrated_farmland.systems.FarmlandHydrationSystem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class HydratedFarmland extends JavaPlugin {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    public static HydratedFarmland INSTANCE = null;

    public final Config<HydratedFarmlandConfig> config = this.withConfig(HydratedFarmlandConfig.CODEC);

    public HydratedFarmland(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("Setting up plugin " + this.getName());
        INSTANCE = this;
        this.config.save();
    }

    @Nullable
    @Override
    public CompletableFuture<Void> preLoad() {
        return super.preLoad();
    }

    @Override
    protected void start() {
        this.getCommandRegistry().registerCommand(new HydratedFarmlandCommand());
        this.getChunkStoreRegistry().registerSystem(new FarmlandHydrationSystem());
    }
}
