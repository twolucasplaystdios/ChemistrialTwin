package net.twolucasplay.chemistrial.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

import static net.twolucasplay.chemistrial.lazy_implementations.inventories.InventoryOperations.replaceHeldItemToPlayer;

@EventBusSubscriber(modid = ChemistrialMod.MODID)
public class ModBucketInBiomesHandler {
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();

        // 雖然只在伺服器端修改數據，但客戶端也必須攔截並標記 SUCCESS，否則會產生畫面閃爍（Desync）
        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.is(Items.BUCKET)) {
            // 1. 模擬桶子行為：進行包含「流體源頭」的射線檢測
            BlockHitResult hitResult = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

            // 2. 確保玩家確實有點擊到方塊（不論是固體方塊還是流體方塊）
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos fluidPos = hitResult.getBlockPos(); // 這才是真正點擊到的流體位置

                // 3. 檢查這個位置是不是水源
                if (level.getFluidState(fluidPos).getType() == Fluids.WATER && level.getFluidState(fluidPos).isSource()) {

                    if (!level.isClientSide()) {
                        // 獲取生態系 ID
                        String biomeKey = level.getBiome(fluidPos)
                                .unwrapKey()
                                .map((key) -> key.identifier().toString())
                                .orElse("minecraft:ocean");

                        ItemStack newBucket = new ItemStack(Items.WATER_BUCKET);
                        newBucket.set(ModDataComponents.ORIGINAL_BIOME.get(), biomeKey);

                        ItemStack resultStack = ItemUtils.createFilledResult(heldItem, player, newBucket);
                        player.setItemInHand(hand, resultStack);

                        // 移除世界上的水源方塊
                        level.setBlock(fluidPos, Blocks.AIR.defaultBlockState(), 11);
                    }

                    // 4. 必須取消事件並返回成功，否則原版桶子會再次觸發，把你的自訂水桶洗掉！
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }
}
