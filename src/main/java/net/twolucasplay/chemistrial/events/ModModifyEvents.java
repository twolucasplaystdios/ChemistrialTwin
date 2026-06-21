package net.twolucasplay.chemistrial.events;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.ModifyRegistriesEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

@EventBusSubscriber(modid = ChemistrialMod.MODID)
public class ModModifyEvents {
    @SubscribeEvent
    public static void modifyComponents(ModifyDefaultComponentsEvent event) {

        // 設定硫磺
        event.modify(Blocks.SULFUR, (patch, provider, item) -> {
            patch.set(ModDataComponents.PURITY.get(), 1.0f);
        });


        event.modify(Items.IRON_INGOT, (patch, provider, item) -> {
            patch.set(ModDataComponents.ISOTOPE.get(), 56);
            patch.set(ModDataComponents.PROTONS.get(), 26);
            patch.set(ModDataComponents.PURITY.get(), 1.0f);
        });

        event.modify(Items.GOLD_INGOT, (patch, provider, item) -> {
            patch.set(ModDataComponents.ISOTOPE.get(), 197);
            patch.set(ModDataComponents.PROTONS.get(), 79);
            patch.set(ModDataComponents.PURITY.get(), 1.0f);
        });
    }

}
