package net.twolucasplay.chemistrial;

import com.mojang.blaze3d.textures.GpuTextureView; // 26.2 核心視圖
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.Identifier;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;

public class ChemistrialLatexTextureManager {
    // 💡 核心修正：除了快取 Identifier，更要直接快取 GpuTextureView，確保 TextRenderable 能 100% 拿到
    public record TextureData(Identifier location, GpuTextureView view, int width, int height) {}

    private static final Map<String, TextureData> CACHE = new ConcurrentHashMap<>();
    private static final Map<String, Boolean> LOADING = new ConcurrentHashMap<>();
    private static final ExecutorService RENDER_SERVICE = Executors.newVirtualThreadPerTaskExecutor();
    private static int textureIdCounter = 0;

    public static TextureData getTexture(String latex) {
        if (CACHE.containsKey(latex)) {
            return CACHE.get(latex);
        }

        if (!LOADING.containsKey(latex)) {
            LOADING.put(latex, true);
            RENDER_SERVICE.submit(() -> {
                try {
                    TeXFormula formula = new TeXFormula(latex);
                    int scale = 2;
                    TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20  * scale);
                    icon.setInsets(new Insets(1, 1, 1, 1));

                    int w = icon.getIconWidth();
                    int h = icon.getIconHeight();

                    icon.setForeground(Color.WHITE); // 必須是純白


                    BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = img.createGraphics();

                    g2.setComposite(AlphaComposite.Clear);
                    g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
                    g2.setComposite(AlphaComposite.SrcOver);
                    g2.setColor(Color.WHITE);

                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

                    icon.setForeground(Color.WHITE);
                    icon.paintIcon(null, g2, 0, 0);
                    g2.dispose();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(img, "png", baos);
                    byte[] bytes = baos.toByteArray();
                    NativeImage nativeImage = NativeImage.read(new java.io.ByteArrayInputStream(bytes));

                    Minecraft.getInstance().execute(() -> {
                        try {
                            String id = "latex_" + (++textureIdCounter);
                            Identifier res = Identifier.fromNamespaceAndPath(ChemistrialMod.MODID, id);

                            DynamicTexture dynamicTexture = new DynamicTexture(() -> "latex_formula_" + id, nativeImage);
                            dynamicTexture.upload(); // 手動上傳像素到 GPU

                            // 註冊進紋理管理器
                            Minecraft.getInstance().getTextureManager().register(res, dynamicTexture);

                            // 💡 核心修正 2：直接抓取麥塊新版為該動態貼圖配置的 GpuTextureView 視圖指標
                            // 依據 26.2 的 Mappings 不同，這裡的方法可能叫 getTextureView() 或 textureView()
                            GpuTextureView gpuView = dynamicTexture.getTextureView();

                            // 寫入快取
                            CACHE.put(latex, new TextureData(res, gpuView, w, h));
                            LOADING.remove(latex);
                        } catch (Exception e) {
                            e.printStackTrace();
                            LOADING.remove(latex);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    LOADING.remove(latex);
                }
            });
        }
        return null;
    }
}
