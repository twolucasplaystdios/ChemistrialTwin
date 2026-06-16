package net.twolucasplay.chemistrial.events;


import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.datacomponents.ModDataComponents;

@EventBusSubscriber(modid = ChemistrialMod.MODID, value = Dist.CLIENT)
public class TooltipEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.has(ModDataComponents.ISOTOPE.get()) && stack.has(ModDataComponents.PROTONS.get())) {
            int isotopeValue = stack.get(ModDataComponents.ISOTOPE);

            Component tooltipText = Component.literal(Component.translatable("tip.chemistrial.isotope").getString() + isotopeValue)
                    .withStyle(ChatFormatting.AQUA);

            if (event.getToolTip().size() > 1) {
                event.getToolTip().add(1, tooltipText); // 插在第二行
            } else {
                event.getToolTip().add(tooltipText);
            }
        }
    }

}
