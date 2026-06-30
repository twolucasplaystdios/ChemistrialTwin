package net.twolucasplay.chemistrial.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.FireBlock.*;

public class ModMagnesiumFireBlock extends FireBlock {

    public ModMagnesiumFireBlock(Properties properties) {
        super(properties.lightLevel(state -> 15));
    }

}
