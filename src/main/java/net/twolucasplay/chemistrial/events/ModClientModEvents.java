package net.twolucasplay.chemistrial.events;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.TextureAtlasStitchedEvent;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.blocks.ModBlocks;

@EventBusSubscriber(modid = ChemistrialMod.MODID, value = Dist.CLIENT)
public class ModClientModEvents {
    @SubscribeEvent
    public static void onTextureStitch(TextureAtlasStitchedEvent event) {
        if (event.getAtlas().location().equals(net.minecraft.client.renderer.texture.TextureAtlas.LOCATION_BLOCKS)) {
            // 載入原版火的貼圖
            event.getAtlas().getSprite(Identifier.parse("block/fire_0"));
            event.getAtlas().getSprite(Identifier.parse("block/fire_1"));
        }
    }

}
