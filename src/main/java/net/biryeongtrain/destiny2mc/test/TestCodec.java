package net.biryeongtrain.destiny2mc.test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentChanges;
import net.minecraft.util.Identifier;

public class TestCodec {
    public static final Codec<TestItemCodec> CODEC = Codec.lazyInitialized(() -> RecordCodecBuilder.create(instance -> instance.group(
                            Identifier.CODEC.fieldOf("id").forGetter(TestItemCodec::id),
                            Codec.INT.fieldOf("count").forGetter(TestItemCodec::count),
                            ComponentChanges.CODEC.optionalFieldOf("changes", ComponentChanges.EMPTY).forGetter(TestItemCodec::changes)
                    ).apply(instance, TestItemCodec::new)
            )
    );
    public record TestItemCodec(Identifier id, int count, ComponentChanges changes) {
        Ident
    }
}
