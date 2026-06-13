package net.twolucasplay.chemistrial;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue OVERWRITE_ITEMS_NAMES = BUILDER
            .comment("Overrides vanilla items' names.")
            .define("overwriteItemNames", true);

    public static final ModConfigSpec.BooleanValue OVERWRITE_ITEMS_PROPS = BUILDER
            .comment("Overrides vanilla items' properties.")
            .define("overwriteItemProps", true);

    static final ModConfigSpec SPEC = BUILDER.build();
}
