package net.twolucasplay.chemistrial.items;

import net.minecraft.world.item.ItemStack;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

public class ChemistrialRadioactiveItem extends ChemistrialChemItem {
    public ChemistrialRadioactiveItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {

        int radLevel = getRadioactivity(stack);

        // 如果等級大於 1 (高輻射、致命輻射)，就直接讓它發光
        if (radLevel > 1) {
            return true;
        }

        // 否則走原版邏輯（例如原版真的附魔了還是會發光）
        return super.isFoil(stack);
    }
}
