package net.twolucasplay.chemistrial.blocks.extendables;

import net.minecraft.world.level.block.FireBlock;


/**
 * The Chemistrial mod's Magnesium Fire class.
 * @author twolucasplay
 */
public class ModMagnesiumFireBlock extends FireBlock {

    /**
     *
     * Future Addition: Add more damage to player.
     */
    public ModMagnesiumFireBlock(Properties properties) {
        super(properties.lightLevel(state -> 15));

    }
}