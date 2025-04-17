package com.huashanlunjian.amara.worldgen;

import com.huashanlunjian.amara.Amara;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public class ModDimension {
    public static final ResourceKey<LevelStem> AMARADIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID,"amaradim"));
    public static final ResourceKey<Level> AMARADIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID,"amaradim"));
    public static final ResourceKey<DimensionType> AMARA_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID,"amaradim_type"));
}
