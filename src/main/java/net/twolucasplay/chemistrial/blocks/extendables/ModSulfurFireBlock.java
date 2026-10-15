package net.twolucasplay.chemistrial.blocks.extendables;

import net.minecraft.world.level.block.FireBlock;

public class ModSulfurFireBlock extends FireBlock {

    public ModSulfurFireBlock(Properties properties) {
        super(properties.lightLevel(state -> 15));
    }

}
