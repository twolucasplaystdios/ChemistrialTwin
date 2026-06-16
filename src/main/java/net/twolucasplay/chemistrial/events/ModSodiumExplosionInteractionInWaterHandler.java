package net.twolucasplay.chemistrial.events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

@EventBusSubscriber(modid = ChemistrialMod.MODID)
public class ModSodiumExplosionInteractionInWaterHandler {
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

            if (itemId.getNamespace().equals("chemistrial") && itemId.getPath().equals("sodium_ingot")){
                BlockPos pos = itemEntity.blockPosition();

                boolean isInWater = level.getFluidState(pos).is(FluidTags.WATER) ||
                        level.getFluidState(pos.above()).is(FluidTags.WATER);

                boolean causesExplosion = isInWater;
                if (causesExplosion){
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
                                var fluidState = level.getFluidState(clearPos);
                                if (fluidState.is(FluidTags.WATER)) {
                                    level.setBlock(clearPos, Blocks.AIR.defaultBlockState(), 2);
                                }
                            });

                    float massSodiumGrams = ((1000000f * count) / 9f) * 0.968f * purity;
                    float molesSodium = massSodiumGrams / 22.99f;

                    int waterBlockCount = 0;
                    // 設定一個掃描半徑（例如周圍 3x3x3 的範圍）
                    int scanRadius = 2;

                    BlockPos centerPos = BlockPos.containing(x, y, z);
                    BlockPos minPos = centerPos.offset(-scanRadius, -scanRadius, -scanRadius);
                    BlockPos maxPos = centerPos.offset(scanRadius, scanRadius, scanRadius);

                    for (BlockPos targetPos : BlockPos.betweenClosed(minPos, maxPos)) {
                        FluidState fluidState = level.getFluidState(targetPos);
                        // 檢查是否是「水源流」（Fluids.WATER），忽略流動的水（Flowing Water）以求質量精確
                        if (fluidState.is(Fluids.WATER) && fluidState.isSource()) {
                            waterBlockCount++;
                        }
                    }

                    if (waterBlockCount == 0) {
                        waterBlockCount = 1;
                    }

                    float massWaterGrams = waterBlockCount * 1000000f;
                    float molesWater = massWaterGrams / 18.02f;

                    float actualReactedMoles = Math.min(molesSodium, molesWater);


                    // 消耗的鈉質量 (克) = 實際反應莫耳數 * 鈉原子量
                    float actualReactedSodiumGrams = actualReactedMoles * 22.99f;

                    // 計算總能量（引入平衡常數）
                    float totalEnergykJ = actualReactedSodiumGrams * ENERGY_PER_GRAM_SODIUM * REACTION_EFFICIENCY;
                    float physicalPower = totalEnergykJ / MC_POWER_TO_KJ;

                    // 計算最終爆炸半徑/威力
                    float explosionPower = (float) Math.sqrt(physicalPower) * 4.0f;

                    final float MAX_SAFE_POWER = 35.0f;
                    if (explosionPower > MAX_SAFE_POWER) {
                        explosionPower = MAX_SAFE_POWER;
                    }

                    level.explode(
                            null,                          // 造成爆炸的實體（null 代表環境/化學爆炸）
                            null,
                            null,
                            x, y, z,
                            explosionPower,                // 爆炸半徑
                            true,
                            Level.ExplosionInteraction.TNT // 破壞方塊模式 (BLOCK/TNT/NONE)
                    );

                    if (level instanceof ServerLevel serverLevel) {

                        serverLevel.sendParticles(ParticleTypes.LAVA, x, y + 0.5, z, 30, 0.5, 0.5, 0.5, 0.2);
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION, x, y + 0.5, z, 5, 1.0, 1.0, 1.0, 0.0);

                        // 精準蒸發：算出化學反應實際上消耗掉多少體積的水方塊，並將其蒸發成空氣
                        int blocksToEvaporate = Math.min(waterBlockCount, (int) (actualReactedMoles * 18.02f / 1000000f) + 1);
                        int evaporated = 0;

                        // 水面、固體表面點燃氫氣火花
                        for (BlockPos targetPos : BlockPos.betweenClosed(minPos, maxPos)) {
                            if (evaporated >= blocksToEvaporate) break;

                            FluidState fluidState = level.getFluidState(targetPos);
                            if (fluidState.is(FluidTags.WATER) && fluidState.isSource()) {
                                level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 2);
                                evaporated++;
                            }
                        }
                    }
                }
            }

        }
    }
}
