package net.twolucasplay.chemistrial.datatypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;

public record ChemItemIngredients(String name, float percentage) {
    public static final Codec<ChemItemIngredients> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("chem_name").forGetter(ChemItemIngredients::name),
            Codec.FLOAT.fieldOf("chem_percentage").forGetter(ChemItemIngredients::percentage)
    ).apply(instance, ChemItemIngredients::new));
}
