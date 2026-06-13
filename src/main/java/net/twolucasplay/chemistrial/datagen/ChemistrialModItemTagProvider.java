package net.twolucasplay.chemistrial.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.twolucasplay.chemistrial.items.ModItems;

import java.util.concurrent.CompletableFuture;

public class ChemistrialModItemTagProvider extends ItemTagsProvider {
    public ChemistrialModItemTagProvider(PackOutput output,
                                         CompletableFuture<HolderLookup.Provider> lookupProvider,
                                         String modId) {
        super(output, lookupProvider, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ChemistrialModTags.Items.TRANSITION_METALS_INGOTS)
                .add(ModItems.ZINC_INGOT.get())
                .add(Items.COPPER_INGOT)
                .add(Items.IRON_INGOT);

        this.tag(ChemistrialModTags.Items.ACTINIDES_METALS_INGOTS)
                .add(ModItems.URANIUM_INGOT.get());
    }
}
