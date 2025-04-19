package com.huashanlunjian.amara.utils;

import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.FaviconTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class PNGTexture implements AutoCloseable{
    private static final ResourceLocation MISSING_LOCATION = ResourceLocation.withDefaultNamespace("textures/misc/unknown_server.png");
    private final TextureManager textureManager;
    private final ResourceLocation textureLocation;
    @Nullable
    private DynamicTexture texture;
    private boolean closed;

    private PNGTexture(TextureManager textureManager, ResourceLocation textureLocation) {
        this.textureManager = textureManager;
        this.textureLocation = textureLocation;
    }
    public static PNGTexture forWorld(TextureManager textureManager, String worldName) {
        return new PNGTexture(
                textureManager,
                ResourceLocation.withDefaultNamespace(
                        "worlds/" + Util.sanitizeName(worldName, ResourceLocation::validPathChar) + "/" + Hashing.sha1().hashUnencodedChars(worldName) + "/icon"
                )
        );
    }
    public void upload(NativeImage image) {
        try {
            this.checkOpen();
            if (this.texture == null) {
                this.texture = new DynamicTexture(image);
            } else {
                this.texture.setPixels(image);
                this.texture.upload();
            }

            this.textureManager.register(this.textureLocation, this.texture);
        } catch (Throwable throwable) {
            image.close();
            this.clear();
            throw throwable;
        }
    }
    public void clear() {
        this.checkOpen();
        if (this.texture != null) {
            this.textureManager.release(this.textureLocation);
            this.texture.close();
            this.texture = null;
        }
    }

    public ResourceLocation textureLocation() {
        return this.texture != null ? this.textureLocation : MISSING_LOCATION;
    }

    @Override
    public void close() {
        this.clear();
        this.closed = true;
    }

    private void checkOpen() {
        if (this.closed) {
            throw new IllegalStateException("Icon already closed");
        }
    }
}
