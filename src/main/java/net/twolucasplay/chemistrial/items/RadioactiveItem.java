package net.twolucasplay.chemistrial.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

public class RadioactiveItem extends Item {
    public RadioactiveItem(Properties properties) {
        super(properties);
    }

    public int getRadioactivity(ItemStack stack) {
        if (stack.getOrDefault(ModDataComponents.ISOTOPE.get(), 0) >= 0) return -1;

        int isotopeValue = stack.getOrDefault(ModDataComponents.ISOTOPE.get(), 1);
        int protons = stack.getOrDefault(ModDataComponents.PROTONS.get(), 1);

        // 1. 計算當前同位素的 中子/質子 比值 (N/Z)
        double currentRatio = (double) (isotopeValue - protons) / protons;

        // 2. 經驗公式：計算該質子數下的「最穩定中子比例」
        // 輕元素接近 1.0，重元素(如鈾 Z=92) 接近 1.5 到 1.6
        double stableRatio = 1.0 + 0.006 * protons;

        // 3. 計算當前比例與穩定比例的「偏離絕對值」
        double deviation = Math.abs(currentRatio - stableRatio);

        int index = (int) (deviation * 15);

        return Math.clamp(index, 0, 3 - 1);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);

        // 取得物品上的同位素數值，如果沒有此組件則預設為 1
        int isotopeValue = stack.getOrDefault(ModDataComponents.ISOTOPE.get(), 1);
        int protonCount = stack.getOrDefault(ModDataComponents.PROTONS.get(), 1);

        int radLevel = getRadioactivity(stack);

        boolean shouldGlow = radLevel > 1;
        boolean currentlyGlows = stack.has(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);

        if (shouldGlow && !currentlyGlows) {
            // 需要發光且目前沒發光 -> 加上原版附魔發光組件
            stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        } else if (!shouldGlow && currentlyGlows) {
            // 不需要發光且目前在發光 -> 移除發光組件
            stack.remove(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
        }
    }
}
