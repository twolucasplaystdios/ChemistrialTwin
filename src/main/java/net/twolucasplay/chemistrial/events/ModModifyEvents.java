package net.twolucasplay.chemistrial.events;

import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

@EventBusSubscriber(modid = ChemistrialMod.MODID)
public class ModModifyEvents {
    @SubscribeEvent
    public static void onModifyDefaultComponents(ModifyDefaultComponentsEvent event) {

        // 為原版鐵錠 (Items.IRON_INGOT) 注入預設組件
        event.modify(Items.IRON_INGOT, builder -> {
            builder.set(ModDataComponents.ISOTOPE.get(), 56);  // 鐵-56 (質量數)
            builder.set(ModDataComponents.PROTONS.get(), 26);  // 質子數 26
        });

        // 順便幫其他原版金屬設定，例如黃金 (質子數 79, 金-197)
        event.modify(Items.GOLD_INGOT, builder -> {
            builder.set(ModDataComponents.ISOTOPE.get(), 197);
            builder.set(ModDataComponents.PROTONS.get(), 79);
        });
    }
}
