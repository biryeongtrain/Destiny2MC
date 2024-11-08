package net.biryeongtrain.destiny2mc.gun;

import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;

public enum ElementType implements StringIdentifiable {
    KINETIC("kinetic", TextColor.fromFormatting(Formatting.WHITE), 0.15f),
    ARC("arc", "#79bbe8"),
    SOLAR("solar", "#f0631e"),
    VOID("void", "#b185df"),
    STASIS("stasis", "#476697"),
    ;

    ElementType(String id, String color) {
        this(id, color, 0);
    }

    ElementType(String id, String hex, float damageIncrement) {
        this(id, getColor(hex), damageIncrement);
    }

    ElementType(String id, TextColor color, float damageIncrement) {
        this.id = id;
        this.color = color;
        this.damageIncrement = damageIncrement;
    }

    private static TextColor getColor(String hex) {
        TextColor color;
        var result = TextColor.parse(hex);
        if (result.isError()) {
            color = TextColor.fromFormatting(Formatting.WHITE);
        } else {
            color = result.result().orElseGet(
                    () -> TextColor.fromFormatting(Formatting.WHITE)
            );
        }

        return color;
    }

    public final String id;
    public final TextColor color;
    public final float damageIncrement;

    @Override
    public String asString() {
        return this.toString().toLowerCase();
    }
}
