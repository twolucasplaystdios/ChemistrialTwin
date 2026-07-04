package net.twolucasplay.chemistrial.blocks;

import net.minecraft.world.level.block.FireBlock;

public class ModSulfurFireBlock extends FireBlock {

    public ModSulfurFireBlock(Properties properties) {
        super(properties.lightLevel(state -> 15));
    }

}
