package net.twolucasplay.chemistrial;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.PreparedText;
import net.minecraft.client.gui.Font.GlyphVisitor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomPreparedText implements PreparedText {
    // 💡 建立一個動態 RenderType 快取，避免重複創建相同的 RenderType 導致顯示卡記憶體洩漏
    private static final Map<Identifier, RenderType> TYPE_CACHE = new ConcurrentHashMap<>();

    private final List<ChemistrialLatexTextProcessor.TextComponent> parts;
    private final float x;
    private final float y;
    private final int originalColor;
    private final boolean drawShadow;
    private final int backgroundColor;
    private final Font font;

    public  CustomPreparedText(List<ChemistrialLatexTextProcessor.TextComponent> parts, float x, float y,
                              int originalColor, boolean drawShadow, int backgroundColor, Font font) {
        this.parts = parts;
        this.x = x;
        this.y = y;
        this.originalColor = originalColor;
        this.drawShadow = drawShadow;
        this.backgroundColor = backgroundColor;
        this.font = font;
    }

    // 💡 延遲建立方法：只有在文字需要被 visit 繪製時才安全調用，徹底避開遊戲啟動時 pipeline=null 的死角
    private RenderType getOrCreateRenderType(Identifier texture) {
        return TYPE_CACHE.computeIfAbsent(texture, id ->
                RenderType.create(
                        "latex_render_type_" + id.getPath(),
                        RenderSetup.builder(RenderPipelines.GUI_TEXT)
                                .withTexture("Sampler0", id)
                                .useOverlay()
                                .useLightmap()
                                .createRenderSetup()
                )
        );
    }

    @Override
    @Nullable
    public ScreenRectangle bounds() {
        return new ScreenRectangle((int) x, (int) y, width(), 9);
    }

    @Override
    public void visit(GlyphVisitor visitor) {
        float currentX = x;

        for (var part : parts) {
            if (!part.isLatex()) {
                if (!part.content().isEmpty()) {
                    PreparedText textChunk = font.prepareText(part.content(), currentX, y, originalColor, drawShadow, backgroundColor);
                    textChunk.visit(visitor);
                    currentX += font.width(part.content());
                }
            } else {
                var texData = ChemistrialLatexTextureManager.getTexture(part.content());
                if (texData != null) {
                    float scale = 9.0f / (float) texData.height();
                    float renderW = texData.width() * scale;
                    float renderH = 9.0f;

                    RenderType latexRenderType = getOrCreateRenderType(texData.location());

                    // 💡 修正：將 texData.view() (真實的 GpuTextureView) 傳給渲染物件
                    visitor.acceptEffect(new LatexTextRenderable(
                            latexRenderType, texData.view(), currentX, y, currentX + renderW, y + renderH
                    ));

                    currentX += renderW;
                } else {
                    String placeholder = "[" + part.content() + "]";
                    PreparedText placeholderChunk = font.prepareText(placeholder, currentX, y, 0x888888, drawShadow, backgroundColor);
                    placeholderChunk.visit(visitor);
                    currentX += font.width(placeholder);
                }
            }
        }
    }

    public int width() {
        int totalWidth = 0;
        for (var part : parts) {
            if (!part.isLatex()) {
                totalWidth += font.width(part.content());
            } else {
                var texData = ChemistrialLatexTextureManager.getTexture(part.content());
                if (texData != null) {
                    float scale = 9.0f / (float) texData.height();
                    totalWidth += (int) (texData.width() * scale);
                } else {
                    totalWidth += font.width("[" + part.content() + "]");
                }
            }
        }
        return totalWidth;
    }
}
