package net.twolucasplay.chemistrial.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChemistrialMod.MODID);

    public static final DeferredItem<Item> ZINC_INGOT = ITEMS.registerSimpleItem("zinc_ingot", properties -> properties);

    public static final DeferredItem<Item> URANIUM_INGOT = ITEMS.register(
            "uranium_ingot",
            id -> new RadioactiveItem(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, id))  // 完美對應，再也不用手動敲字串、也不會報錯了！
                            .component(ModDataComponents.ISOTOPE.get(), 238)
                            .component(ModDataComponents.PROTONS.get(), 92)
            )
    );

    public static final DeferredItem<Item> MERCURY_II_SULFIDE = ITEMS.registerSimpleItem(
            "mercury_ii_sulfide",
            properties -> properties
    );

    public static final DeferredItem<Item> RAW_ZINC = ITEMS.registerSimpleItem("raw_zinc", properties -> properties);


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
