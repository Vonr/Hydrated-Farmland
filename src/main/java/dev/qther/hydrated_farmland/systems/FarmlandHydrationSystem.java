package dev.qther.hydrated_farmland.systems;

import com.hypixel.hytale.builtin.adventure.farming.states.TilledSoilBlock;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.qther.hydrated_farmland.HydratedFarmland;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadLocalRandom;

public class FarmlandHydrationSystem extends EntityTickingSystem<ChunkStore> {
    @Override
    public Query<ChunkStore> getQuery() {
        return WorldChunk.getComponentType();
    }

    @Override
    public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
        var config = HydratedFarmland.INSTANCE.config.get();
        var distance = config.distance;
        var ticks = config.ticks;
        if (distance <= 0 || ticks <= 0) {
            return;
        }

        WorldChunk worldChunk = archetypeChunk.getComponent(index, WorldChunk.getComponentType());

        var random = ThreadLocalRandom.current();

        for (int tick = 0; tick < ticks; tick++) {
            var x = ChunkUtil.worldCoordFromLocalCoord(worldChunk.getX(), random.nextInt(32));
            var y = random.nextInt(320);
            var z = ChunkUtil.worldCoordFromLocalCoord(worldChunk.getZ(), random.nextInt(32));

            checkWaterSource(worldChunk, x, y, z, distance);
        }
    }

    private static void checkWaterSource(WorldChunk worldChunk, int x, int y, int z, int distance) {
        var world = worldChunk.getWorld();
        var soilRef = worldChunk.getBlockComponentEntity(x, y, z);

        if (soilRef == null) {
            return;
        }

        Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
        TilledSoilBlock soil = chunkStore.getComponent(soilRef, TilledSoilBlock.getComponentType());

        if (soil != null && !soil.hasExternalWater()) {
            var validFluidIds = new int[]{
                    Fluid.getAssetMap().getIndex("Water"),
                    Fluid.getAssetMap().getIndex("Water_Source")
            };

            for (int dx = x - distance; dx <= x + distance; dx++) {
                for (int dz = z - distance; dz <= z + distance; dz++) {
                    if (dx == x && dz == z) {
                        continue;
                    }

                    var fluidId = world.getFluidId(dx, y, dz);
                    if (fluidId == 0) {
                        continue;
                    }

                    var match = false;
                    for (var valid : validFluidIds) {
                        if (fluidId == valid) {
                            match = true;
                            break;
                        }
                    }

                    if (match) {
                        soil.setExternalWater(true);
                        worldChunk.setTicking(x, y, z, true);

                        return;
                    }
                }
            }

            soil.setExternalWater(false);
        }
    }
}
