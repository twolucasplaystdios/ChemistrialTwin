package net.twolucasplay.chemistrial.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;


/**
 * The Chemistrial mod's Magnesium Fire class.
 * @author twolucasplay
 */
public class ModMagnesiumFireBlock extends FireBlock {

    /**
     *
     * @implNote Add damage to player.
     */
    public ModMagnesiumFireBlock(Properties properties) {
        super(properties.lightLevel(state -> 15));

    }
}