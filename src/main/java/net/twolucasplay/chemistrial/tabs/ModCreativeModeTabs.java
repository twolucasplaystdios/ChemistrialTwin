package net.twolucasplay.chemistrial.tabs;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.items.ModItems;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChemistrialMod.MODID);

    public static final Supplier<CreativeModeTab> TRANSITION_METALS_ITEMS_TAB = CREATIVE_MODE_TABS.register("transition_metals_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ZINC_INGOT.get()))
                    .title(Component.translatable("creativetab.chemistrialmod.transition_metals_tab"))
                    .withTabsBefore(CreativeModeTabs.INGREDIENTS)
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ZINC_INGOT);
                        output.accept(ModItems.RAW_ZINC);
                        output.accept(Items.IRON_INGOT);
                        output.accept(Items.COPPER_INGOT);
                        output.accept(Items.GOLD_INGOT);
                    }).build());

    public static final Supplier<CreativeModeTab> ACTINIDES_METALS_ITEMS_TAB = CREATIVE_MODE_TABS.register("actinides_metals_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.URANIUM_INGOT.get()))
                    .title(Component.translatable("creativetab.chemistrialmod.actinides_metals_tab"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(ChemistrialMod.MODID, "transition_metals_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.URANIUM_INGOT);
                    }).build());


    public static final Supplier<CreativeModeTab> OTHERS_ITEMS_TAB = CREATIVE_MODE_TABS.register("other_chemicals_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.URANIUM_INGOT.get()))
                    .title(Component.translatable("creativetab.chemistrialmod.other_chemicals_tab"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(ChemistrialMod.MODID, "reactive_chemicals_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.IRON_OXIDE);
                        output.accept(ModItems.MERCURY_II_SULFIDE);
                    }).build());

    public static final Supplier<CreativeModeTab> REACTIVE_METALS_ITEMS_TAB = CREATIVE_MODE_TABS.register("reactive_chemicals_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.URANIUM_INGOT.get()))
                    .title(Component.translatable("creativetab.chemistrialmod.reactive_chemicals_tab"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(ChemistrialMod.MODID, "actinides_metals_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SODIUM_INGOT);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
