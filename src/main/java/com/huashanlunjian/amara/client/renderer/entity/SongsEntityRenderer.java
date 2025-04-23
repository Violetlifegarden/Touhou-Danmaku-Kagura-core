package com.huashanlunjian.amara.client.renderer.entity;

import com.huashanlunjian.amara.Amara;
import com.huashanlunjian.amara.client.model.EntityFairyModel;
import com.huashanlunjian.amara.entity.songs.Boss;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SongsEntityRenderer extends MobRenderer<Boss, EntityFairyModel> {
    private static final ResourceLocation TEXTURE_0 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_0.png");
    private static final ResourceLocation TEXTURE_1 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_1.png");
    private static final ResourceLocation TEXTURE_2 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_2.png");
    private static final ResourceLocation TEXTURE_3 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_3.png");
    private static final ResourceLocation TEXTURE_4 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_4.png");
    private static final ResourceLocation TEXTURE_5 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_5.png");
    private static final ResourceLocation TEXTURE_6 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_6.png");
    private static final ResourceLocation TEXTURE_7 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_7.png");
    private static final ResourceLocation TEXTURE_8 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_8.png");
    private static final ResourceLocation TEXTURE_9 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_9.png");
    private static final ResourceLocation TEXTURE_10 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_10.png");
    private static final ResourceLocation TEXTURE_11 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_11.png");
    private static final ResourceLocation TEXTURE_12 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_12.png");
    private static final ResourceLocation TEXTURE_13 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_13.png");
    private static final ResourceLocation TEXTURE_14 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_14.png");
    private static final ResourceLocation TEXTURE_15 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_15.png");
    private static final ResourceLocation TEXTURE_16 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_16.png");
    private static final ResourceLocation TEXTURE_17 = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/maid_fairy/maid_fairy_17.png");

    //private final NewBossRenderer newEntityFairyRenderer;
    public SongsEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new EntityFairyModel(pContext.bakeLayer(EntityFairyModel.LAYER)), 0.5f);
        //this.newEntityFairyRenderer = new NewBossRenderer(pContext);
    }
    @Override
    public void render(Boss fairy, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(fairy, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected void setupRotations(Boss fairy, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(fairy, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        if (!fairy.onGround()) {
            poseStack.mulPose(Axis.XN.rotation(8 * (float) Math.PI / 180.0f));
        }
    }
    @Override
    public ResourceLocation getTextureLocation(Boss entity) {
        return switch (entity.getFairyTypeOrdinal()) {
            case 1 -> TEXTURE_1;
            case 2 -> TEXTURE_2;
            case 3 -> TEXTURE_3;
            case 4 -> TEXTURE_4;
            case 5 -> TEXTURE_5;
            case 6 -> TEXTURE_6;
            case 7 -> TEXTURE_7;
            case 8 -> TEXTURE_8;
            case 9 -> TEXTURE_9;
            case 10 -> TEXTURE_10;
            case 11 -> TEXTURE_11;
            case 12 -> TEXTURE_12;
            case 13 -> TEXTURE_13;
            case 14 -> TEXTURE_14;
            case 15 -> TEXTURE_15;
            case 16 -> TEXTURE_16;
            case 17 -> TEXTURE_17;
            default -> TEXTURE_0;
        };
    }
}
