package com.huashanlunjian.amara.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SongsEntityRenderer<T extends Entity> extends EntityRenderer<T> {
    public SongsEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
    @Override
    public ResourceLocation getTextureLocation(T Entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
