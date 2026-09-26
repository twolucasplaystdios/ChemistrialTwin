package net.twolucasplay.chemistrial.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.items.ModItems;

import java.util.function.Function;

/**
 * The Chemistrial mod's Blocks class. Registers all the blocks.
 * @author twolucasplay
 */
public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ChemistrialMod.MODID);

    public static final DeferredBlock<Block> ZINC_BLOCK = registerBlock(
            "zinc_block",
            properties -> new Block(properties.strength(4f)
                    .requiresCorrectToolForDrops().sound(SoundType.IRON)));

    public static final DeferredBlock<Block> ZINC_ORE = registerBlock("zinc_ore", properties -> new Block(
            properties.strength(4f)
                    .requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> DEEPSLATE_ZINC_ORE = registerBlock("deepslate_zinc_ore", properties -> new Block(
            properties.strength(4f)
                    .requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<FireBlock> SULFUR_FIRE = registerCustomFireBlock(
            "sulfur_fire",
            DyeColor.LIGHT_BLUE,
            ModSulfurFireBlock::new
    );

    public static final DeferredBlock<FireBlock> MAGNESIUM_FIRE = registerCustomFireBlock(
            "magnesium_fire",
            DyeColor.WHITE,
            ModMagnesiumFireBlock::new
    );

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    /**
     * Registers the fire block, mostly from the item burning.
     * @param name Name of the block.
     * @param dyeColor Fire block's color.
     * @param blockFactory Factory of the fire block.
     */
    private static <T extends BaseFireBlock> DeferredBlock<T> registerCustomFireBlock(String name, DyeColor dyeColor, Function<BlockBehaviour.Properties, T> blockFactory   ) {
        return BLOCKS.registerBlock(name,
                properties -> blockFactory.apply(properties.mapColor(dyeColor))
        );
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    /**
     * Registers all the blocks.
     * @param eventBus I'll find out what this means in the future.
     */
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}