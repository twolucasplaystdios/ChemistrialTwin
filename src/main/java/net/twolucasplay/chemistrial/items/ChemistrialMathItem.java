package net.twolucasplay.chemistrial.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

public class ChemistrialMathItem extends Item {
    public ChemistrialMathItem(Properties properties) {
        super(properties);
    }


    public float getOriginX(ItemStack stack){
        if (stack.get(ModDataComponents.CENTER_X.get()) == null) return -1f;
        return stack.get(ModDataComponents.CENTER_X.get());
    }

}
