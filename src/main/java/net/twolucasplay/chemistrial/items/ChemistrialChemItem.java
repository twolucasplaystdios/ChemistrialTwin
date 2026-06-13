package net.twolucasplay.chemistrial.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

public class ChemistrialChemItem extends Item {
    public ChemistrialChemItem(Properties properties) {
        super(properties);
    }

    public int getRadioactivity(ItemStack stack) {
        if (stack.getOrDefault(ModDataComponents.ISOTOPE.get(), 0) <= 0) return -1;

        int isotopeValue = stack.getOrDefault(ModDataComponents.ISOTOPE.get(), 1);
        int protons = stack.getOrDefault(ModDataComponents.PROTONS.get(), 1);

        // 1. 計算當前同位素的 中子/質子 比值 (N/Z)
        double currentRatio = (double) (isotopeValue - protons) / protons;

        // 2. 經驗公式：計算該質子數下的「最穩定中子比例」
        // 輕元素接近 1.0，重元素(如鈾 Z=92) 接近 1.5 到 1.6
        double stableRatio = 1.0 + 0.0006 * protons;

        // 3. 計算當前比例與穩定比例的「偏離絕對值」
        double deviation = Math.abs(currentRatio - stableRatio);

        int index = (int) (deviation * 15);

        return Math.clamp(index, 0, 3 - 1);
    }
}
