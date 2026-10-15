package net.twolucasplay.chemistrial.items.extendables;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

public class ChemistrialMathCompassItem extends ChemistrialMathItem {
    public ChemistrialMathCompassItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();

        float cx = stack.getOrDefault(ModDataComponents.CENTER_X.get(), 0f);
        float cy = stack.getOrDefault(ModDataComponents.CENTER_Y.get(), 0f);
        float cz = stack.getOrDefault(ModDataComponents.CENTER_Z.get(), 0f);
        return InteractionResult.SUCCESS;
    }

}
