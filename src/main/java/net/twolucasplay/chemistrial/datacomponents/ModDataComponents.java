package net.twolucasplay.chemistrial.datacomponents;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.twolucasplay.chemistrial.ChemistrialMod;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ChemistrialMod.MODID);

    // 註冊同位素組件
    public static final Supplier<DataComponentType<Integer>> ISOTOPE = COMPONENTS.register(
            "isotope",
            () -> DataComponentType.<Integer>builder()
                    .networkSynchronized(ByteBufCodecs.INT) // 自動同步客戶端與伺服器
                    .persistent(Codec.INT)                  // 自動儲存到存檔
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> PROTONS = COMPONENTS.register(
            "protons",
            () -> DataComponentType.<Integer>builder()
                    .networkSynchronized(ByteBufCodecs.INT) // 自動同步客戶端與伺服器
                    .persistent(Codec.INT)                  // 自動儲存到存檔
                    .build()
    );

    public static void register(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }
}
