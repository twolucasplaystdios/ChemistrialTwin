package net.twolucasplay.chemistrial.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.util.FormattedCharSequence;
import net.twolucasplay.chemistrial.ChemistrialLatexTextProcessor;
import net.twolucasplay.chemistrial.CustomPreparedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Font.class)
public class FontMixin {

    @Inject(
            method = "prepareText(Ljava/lang/String;FFIZI)Lnet/minecraft/client/gui/Font$PreparedText;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onPrepareTextString(
            String text, float x, float y, int originalColor, boolean drawShadow, int backgroundColor,
            CallbackInfoReturnable<Font.PreparedText> cir
    ) {
        if (text == null || !text.contains("$")) return;

        List<ChemistrialLatexTextProcessor.TextComponent> parts = ChemistrialLatexTextProcessor.parse(text);
        if (parts.stream().noneMatch(ChemistrialLatexTextProcessor.TextComponent::isLatex)) return;

        Font.PreparedText customText = new CustomPreparedText(
                parts, x, y, originalColor, drawShadow, backgroundColor, (Font)(Object)this
        );
        cir.setReturnValue(customText);
    }

    @Inject(
            method = "prepareText(Lnet/minecraft/util/FormattedCharSequence;FFIZZI)Lnet/minecraft/client/gui/Font$PreparedText;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onPrepareTextSequence(
            FormattedCharSequence text, float x, float y, int originalColor, boolean drawShadow, boolean includeEmpty, int backgroundColor,
            CallbackInfoReturnable<Font.PreparedText> cir
    ) {
        if (text == null) return;

        StringBuilder sb = new StringBuilder();
        text.accept((index, style, codePoint) -> {
            sb.appendCodePoint(codePoint);
            return true;
        });
        String plainText = sb.toString();

        if (!plainText.contains("$")) return;

        List<ChemistrialLatexTextProcessor.TextComponent> parts = ChemistrialLatexTextProcessor.parse(plainText);
        if (parts.stream().noneMatch(ChemistrialLatexTextProcessor.TextComponent::isLatex)) return;

        Font.PreparedText customText = new CustomPreparedText(
                parts, x, y, originalColor, drawShadow, backgroundColor, (Font)(Object)this
        );
        cir.setReturnValue(customText);
    }
}
