package net.twolucasplay.chemistrial.events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.blocks.ModBlocks;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

@EventBusSubscriber(modid = ChemistrialMod.MODID)
public class ModMagnesiumInteractionInFireHandler {
    static final float MC_POWER_TO_KJ = 1046000.0f;
    static final float ENERGY_PER_GRAM_SODIUM = 183.75f / 22.99f;
    static final float REACTION_EFFICIENCY = 0.05f;
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof ItemEntity itemEntity) {
            Level level = itemEntity.level();
            if (level.isClientSide()) return;

            ItemStack stack = itemEntity.getItem();
            net.minecraft.resources.@org.jspecify.annotations.NonNull Identifier itemId =
                    BuiltInRegistries.ITEM.getKey(stack.getItem());

            if (itemId.getNamespace().equals("chemistrial") && itemId.getPath().equals("magnesium_ingot")){
                BlockPos pos = itemEntity.blockPosition();

                boolean isOnFire = itemEntity.level().getBlockState(pos).is(Blocks.FIRE);
                if (isOnFire){
                    double x = itemEntity.getX();
                    double y = itemEntity.getY();
                    double z = itemEntity.getZ();
                    float purity;
                    if (stack.has(ModDataComponents.PURITY.get())){
                        purity = stack.get(ModDataComponents.PURITY);
                    } else {
                        stack.set(ModDataComponents.PURITY, 1.0f);
                        purity = 1.0f;
                    }

                    int count = stack.getCount();

                    itemEntity.discard();
                    BlockPos.betweenClosedStream(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))
                            .forEach(clearPos -> {
                                var fireState = level.getBlockState(clearPos);
                                if (fireState.is(Blocks.FIRE)) {
                                    level.setBlock(clearPos, ModBlocks.MAGNESIUM_FIRE.get().defaultBlockState(), 2);
                                }
                            });

                }
            }

        }
    }
}
