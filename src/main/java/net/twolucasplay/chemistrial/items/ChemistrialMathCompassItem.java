package net.twolucasplay.chemistrial.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
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
