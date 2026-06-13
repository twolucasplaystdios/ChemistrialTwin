package net.twolucasplay.chemistrial.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ChemistrialModTags {
    public static class Items {
        private static Identifier ResourceLocation;
        public static final TagKey<Item> TRANSITION_METALS_INGOTS = TagKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath("c", "ingots/transition_metals_ingots")
        );
        public static final TagKey<Item> ACTINIDES_METALS_INGOTS = TagKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath("c", "ingots/actinides_metals_ingots")
        );
    }
}
