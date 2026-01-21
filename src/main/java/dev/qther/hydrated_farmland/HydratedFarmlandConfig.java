package dev.qther.hydrated_farmland;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class HydratedFarmlandConfig {
    public int distance = 4;
    public int ticks = 64;

    public static final BuilderCodec<HydratedFarmlandConfig> CODEC = BuilderCodec.builder(HydratedFarmlandConfig.class, HydratedFarmlandConfig::new)
            .append(new KeyedCodec<>("Distance", Codec.INTEGER), (data, value) -> data.distance = value, data -> data.distance).add()
            .append(new KeyedCodec<>("Ticks", Codec.INTEGER), (data, value) -> data.ticks = value, data -> data.ticks).add()
            .build();
}
