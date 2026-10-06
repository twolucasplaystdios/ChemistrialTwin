package net.twolucasplay.chemistrial;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.joml.Matrix3x2f;
import org.joml.Matrix4fc;
import org.joml.Matrix3x2fc;

import static net.neoforged.fml.earlydisplay.render.GlState.enableBlend;

public class LatexTextRenderable implements TextRenderable {
    private final RenderType renderType;
    private final GpuTextureView textureView; // 💡 修正：直接儲存傳進來的真實 GPU 視圖
    private final float left;
    private final float top;
    private final float right;
    private final float bottom;

    public LatexTextRenderable(RenderType renderType, GpuTextureView textureView, float left, float top, float right, float bottom) {
        this.renderType = renderType;
        this.textureView = textureView;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public RenderType renderType(Font.DisplayMode displayMode) {
        return this.renderType;
    }

    @Override
    public RenderPipeline guiPipeline() {
        return RenderPipelines.GUI; // 沿用最穩定的基礎 2D 管線
    }

    // 💡 修正：直接回傳快取好的真實 GPU 視圖，徹底解決純白方塊（沒貼圖）的問題！
    @Override
    public GpuTextureView textureView() {
        return this.textureView;
    }

    @Override
    public float left() { return this.left; }

    @Override
    public float top() { return this.top; }

    @Override
    public float right() { return this.right; }

    @Override
    public float bottom() { return this.bottom; }

    @Override
    public void render(Matrix4fc matrix4, VertexConsumer consumer, int lightmap, boolean dropShadow) {
        Matrix3x2f matrix3x2 = new Matrix3x2f();
        matrix3x2.m00 = matrix4.m00();
        matrix3x2.m01 = matrix4.m01();
        matrix3x2.m10 = matrix4.m10();
        matrix3x2.m11 = matrix4.m11();
        matrix3x2.m20 = matrix4.m30();
        matrix3x2.m21 = matrix4.m31();

        this.renderWith3x2(matrix3x2, consumer, lightmap);
    }

    private void renderWith3x2(Matrix3x2fc matrix, VertexConsumer consumer, int lightmap) {
        // 動態開啟半透明混合模式
        enableBlend(true);

        consumer.addVertexWith2DPose(matrix, left, bottom)
                .setColor(255, 255, 255, 255)
                .setUv(0.0F, 1.0F)
                .setLight(lightmap);

        consumer.addVertexWith2DPose(matrix, right, bottom)
                .setColor(255, 255, 255, 255)
                .setUv(1.0F, 1.0F)
                .setLight(lightmap);

        consumer.addVertexWith2DPose(matrix, right, top)
                .setColor(255, 255, 255, 255)
                .setUv(1.0F, 0.0F)
                .setLight(lightmap);

        consumer.addVertexWith2DPose(matrix, left, top)
                .setColor(255, 255, 255, 255)
                .setUv(0.0F, 0.0F)
                .setLight(lightmap);
    }
}
