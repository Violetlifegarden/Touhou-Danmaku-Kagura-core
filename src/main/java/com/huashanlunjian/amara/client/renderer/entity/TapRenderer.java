package com.huashanlunjian.amara.client.renderer.entity;

import com.huashanlunjian.amara.Amara;
import com.huashanlunjian.amara.entity.Tap;
import com.huashanlunjian.amara.music_game_extension.DanmakuColor;
import com.huashanlunjian.amara.music_game_extension.DanmakuType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TapRenderer extends EntityRenderer<Tap> {
    private static final ResourceLocation DANMAKU_TEXTURE = ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, "textures/entity/danmaku.png");
    private static final RenderType RENDER_TYPE = RenderType.itemEntityTranslucentCull(DANMAKU_TEXTURE);
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;
    public TapRenderer(EntityRendererProvider.Context pContext, float pScale, boolean pFullBright) {
        super(pContext);
        this.itemRenderer = pContext.getItemRenderer();
        this.scale = pScale;
        this.fullBright = pFullBright;
    }
    private static void vertex(VertexConsumer bufferIn, Matrix4f pose, Matrix3f normal, double x, double y, double texU, double texV, int packedLight) {
        Vector3f vector3f = normal.transform(new Vector3f(0.0F, 1.0F, 0.0F));
        bufferIn.addVertex(pose, (float) x, (float) y, 0.0F).setColor(255, 255, 255, 255).setUv((float) texU, (float) texV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(vector3f.x(), vector3f.y(), vector3f.z());
    }

    public TapRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, 1.0F, true);
    }
    protected int getBlockLightLevel(Tap pEntity, BlockPos pPos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(pEntity, pPos);
    }
    public void render(Tap pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        // 获取相关数据
        DanmakuColor color = pEntity.getColor();
        DanmakuType type = pEntity.getDanmakuType();
        // 材质宽度
        int width = 416;
        // 材质长度
        int length = 128;
        // 依据类型颜色开始定位材质位置（材质块都是 32 * 32 大小）
        double startU = 32 * color.ordinal();
        double startV = 32 * type.ordinal();

        pPoseStack.pushPose();
        pPoseStack.translate(0, 0.1, 0);
        pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());

        VertexConsumer buffer = pBuffer.getBuffer(RENDER_TYPE);
        PoseStack.Pose poseStackLast = pPoseStack.last();
        Matrix4f pose = poseStackLast.pose();
        Matrix3f normal = poseStackLast.normal();

        vertex(buffer, pose, normal, -type.getSize(), type.getSize(), (startU + 0) / width, (startV + 0) / length, pPackedLight);
        vertex(buffer, pose, normal, -type.getSize(), -type.getSize(), (startU + 0) / width, (startV + 32) / length, pPackedLight);
        vertex(buffer, pose, normal, type.getSize(), -type.getSize(), (startU + 32) / width, (startV + 32) / length, pPackedLight);
        vertex(buffer, pose, normal, type.getSize(), type.getSize(), (startU + 32) / width, (startV + 0) / length, pPackedLight);
        pPoseStack.popPose();
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param pEntity
     */
    @Override
    public ResourceLocation getTextureLocation(Tap pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }


}
