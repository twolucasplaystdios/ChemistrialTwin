package net.twolucasplay.chemistrial.datacomponents;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.twolucasplay.chemistrial.ChemistrialMod;
import net.twolucasplay.chemistrial.datatypes.ChemItemIngredients;

import java.util.HashMap;
import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ChemistrialMod.MODID);

    // Isotopes
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


    // TODO: Rewrite this
    @Deprecated(forRemoval = true)
    public static final Supplier<DataComponentType<Float>> PURITY = COMPONENTS.register(
            "purity",
            () -> DataComponentType.<Float>builder()
                    .networkSynchronized(ByteBufCodecs.FLOAT) // 自動同步客戶端與伺服器
                    .persistent(Codec.FLOAT)                  // 自動儲存到存檔
                    .build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ChemItemIngredients>> INGREDIENTS =
            COMPONENTS.register("chem_ingredient_component", () -> DataComponentType.<ChemItemIngredients>builder()
                    .persistent(ChemItemIngredients.CODEC) // 存檔與 NBT 同步需要
                    .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.fromCodec(ChemItemIngredients.CODEC))
                    .build()
            );

    public static final Supplier<DataComponentType<Float>> CENTER_X = COMPONENTS.register(
            "center_x",
            () -> DataComponentType.<Float>builder()
                    .networkSynchronized(ByteBufCodecs.FLOAT) // 自動同步客戶端與伺服器
                    .persistent(Codec.FLOAT)                  // 自動儲存到存檔
                    .build()
    );

    public static final Supplier<DataComponentType<Float>> CENTER_Y = COMPONENTS.register(
            "center_y",
            () -> DataComponentType.<Float>builder()
                    .networkSynchronized(ByteBufCodecs.FLOAT) // 自動同步客戶端與伺服器
                    .persistent(Codec.FLOAT)                  // 自動儲存到存檔
                    .build()
    );

    public static final Supplier<DataComponentType<Float>> CENTER_Z = COMPONENTS.register(
            "center_z",
            () -> DataComponentType.<Float>builder()
                    .networkSynchronized(ByteBufCodecs.FLOAT) // 自動同步客戶端與伺服器
                    .persistent(Codec.FLOAT)                  // 自動儲存到存檔
                    .build()
    );


    public static final Supplier<DataComponentType<Float>> TEMPERATURE = COMPONENTS.register(
            "temperature",
            () -> DataComponentType.<Float>builder()
                    .networkSynchronized(ByteBufCodecs.FLOAT) // 自動同步客戶端與伺服器
                    .persistent(Codec.FLOAT)                  // 自動儲存到存檔
                    .build()
    );

    public static final Supplier<DataComponentType<Float>> ACIDITY = COMPONENTS.register(
            "acidity",
            () -> DataComponentType.<Float>builder()
                    .networkSynchronized(ByteBufCodecs.FLOAT) // 自動同步客戶端與伺服器
                    .persistent(Codec.FLOAT)                  // 自動儲存到存檔
                    .build()
    );

    public static final Supplier<DataComponentType<String>> ORIGINAL_BIOME = COMPONENTS.register(
            "original_biome",
            () -> DataComponentType.<String>builder()
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8) // 自動同步客戶端與伺服器
                    .persistent(Codec.STRING)                  // 自動儲存到存檔
                    .build()
    );
    public static void register(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }
}

