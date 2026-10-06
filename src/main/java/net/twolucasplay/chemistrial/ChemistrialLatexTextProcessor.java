package net.twolucasplay.chemistrial;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChemistrialLatexTextProcessor {
    // 💡 修正 1：放寬匹配規則，容許公式內包含非破壞性的排版字元，並使用強力的分段捕捉
    private static final Pattern LATEX_PATTERN = Pattern.compile("\\$([^\\$]+)\\$");

    public record TextComponent(boolean isLatex, String content) {}

    public static List<TextComponent> parse(String text) {
        List<TextComponent> components = new ArrayList<>();
        if (text == null || text.isEmpty()) return components;

        // 💡 修正 2：如果字串包含了 § 等麥塊原生色彩符號，先做清洗或確保匹配穩定
        Matcher matcher = LATEX_PATTERN.matcher(text);
        int lastEnd = 0;

        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                components.add(new TextComponent(false, text.substring(lastEnd, matcher.start())));
            }
            // 拿到 $ 內部的 LaTeX 公式碼
            String latexContent = matcher.group(1);
            // 剔除可能被誤塞入的麥塊格式化字元（例如移除 §r 這種重置樣式碼）
            latexContent = latexContent.replaceAll("§[0-9a-fk-orxX]", "");

            components.add(new TextComponent(true, latexContent.trim()));
            lastEnd = matcher.end();
        }

        if (lastEnd < text.length()) {
            components.add(new TextComponent(false, text.substring(lastEnd)));
        }
        return components;
    }
}
