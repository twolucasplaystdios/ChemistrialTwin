package net.twolucasplay.chemistrial.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin {

    @Shadow
    private void setFlammable(Block block, int encouragement, int flammability) {}

    @Inject(method = "bootStrap", at = @At("TAIL"))
    private static void injectCustomFlammables(CallbackInfo ci) {
        // 因為 bootStrap 是 static 的，所以這個 mixin 方法也必須是 static
        // 這裡可以直接呼叫 shadow 的私有方法，Mixin 會自動幫你處理權限。   
        ((FireBlockMixin)(Object)Blocks.FIRE).setFlammable(Blocks.SULFUR, 5, 20);
    }
}
