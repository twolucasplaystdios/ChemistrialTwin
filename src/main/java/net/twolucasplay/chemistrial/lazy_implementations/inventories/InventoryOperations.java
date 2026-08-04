package net.twolucasplay.chemistrial.lazy_implementations.inventories;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import org.jspecify.annotations.NonNull;

public class InventoryOperations {

    /**
     * Use this to replace holding items
     * @param hand Player's hand
     * @param player Player itself
     * @param newItemStack Result (The replacement of the slot)
     */
    public static void replaceHeldItemToPlayer(@NonNull Player player, InteractionHand hand, ItemStack newItemStack) {
        ItemStack itemStackFrom = player.getItemInHand(hand);

        ItemStack resultStack = ItemUtils.createFilledResult(itemStackFrom, player, newItemStack);

        player.setItemInHand(hand, resultStack);
    }

}
