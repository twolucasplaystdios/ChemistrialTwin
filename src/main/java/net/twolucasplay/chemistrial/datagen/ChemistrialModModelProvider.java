package net.twolucasplay.chemistrial.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.blocks.ModBlocks;
import net.twolucasplay.chemistrial.blocks.ModMagnesiumFireBlock;
import net.twolucasplay.chemistrial.items.ModItems;

import java.util.Optional;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class ChemistrialModModelProvider extends ModelProvider {


    public ChemistrialModModelProvider(PackOutput output) {
        super(output, ChemistrialMod.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (Holder<Item> itemHolder : ModItems.ITEMS.getEntries()) {
            Item item = itemHolder.value();

            if (item instanceof BlockItem) {
                continue;
            }
            itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        }

        for (DeferredHolder<net.minecraft.world.level.block.Block, ? extends net.minecraft.world.level.block.Block> blockHolder : ModBlocks.BLOCKS.getEntries()) {
            Block block = blockHolder.value();
            if (block instanceof ModMagnesiumFireBlock) {
//                blockModels.createFloorFireModels(block);
//                Identifier modelLoc = TexturedModel.CUBE.create(block, blockModels.modelOutput);
//
//                Variant variant = new Variant(modelLoc);
//                blockModels.blockStateOutput.accept(
//                        MultiVariantGenerator.dispatch(
//                                block,
//                                // Create the basic multi-variant
//                                BlockModelGenerators.variant(variant)
//                        )
//                );
                MultiVariant floorFireModels = blockModels.createFloorFireModels(block);
                MultiVariant sideFireModels = blockModels.createSideFireModels(block);
                blockModels.blockStateOutput
                        .accept(
                                MultiPartGenerator.multiPart(block)
                                        .with(floorFireModels)
                                        .with(sideFireModels)
                                        .with(sideFireModels.with(Y_ROT_90))
                                        .with(sideFireModels.with(Y_ROT_180))
                                        .with(sideFireModels.with(Y_ROT_270))
                        );
                continue;
            }
            blockModels.createTrivialCube(block);
        }
    }


}
