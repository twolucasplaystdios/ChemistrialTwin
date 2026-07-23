package net.twolucasplay.chemistrial.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.items.ModItems;
import net.twolucasplay.chemistrial.loot.AddItemStackModifier;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ChemistrialMod.MODID);
    }

    @Override
    protected void start() {
        // Cinnabar to Mercury Sulfide.
        this.add("cinnabar_to_mercury_ii_sulfide",
                new AddItemStackModifier(new LootItemCondition[] {
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.CINNABAR).build(),
                        LootItemRandomChanceCondition.randomChance(1f).build() },new ItemStackTemplate(ModItems.MERCURY_II_SULFIDE.get(), 2)));

        // Bone Block to Calcium Carbonate.
        this.add("bone_block_to_calcium_carbonate",
                new AddItemStackModifier(new LootItemCondition[] {
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.BONE_BLOCK).build(),
                        LootItemRandomChanceCondition.randomChance(0.2f).build() },new ItemStackTemplate(ModItems.CALCIUM_CARBONATE.get(), 1)));

    }
}
