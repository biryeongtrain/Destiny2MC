package net.biryeongtrain.destiny2mc.mixin;

import net.biryeongtrain.destiny2mc.duck.ServerPlayerEntityDuck;
import net.biryeongtrain.destiny2mc.jump.AbstractJump;
import net.biryeongtrain.destiny2mc.jump.HunterJump;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements ServerPlayerEntityDuck {

    @Unique
    private AbstractJump destiny2$jump = new HunterJump();

    @Inject(method = "onLanding", at = @At("TAIL"))
    private void destiny2$resetJump(CallbackInfo ci) {
        destiny2$jump.release(((ServerPlayerEntity) (Object) this));
    }


    @Override
    public void destiny2$doDestiny2Jump() {
        this.destiny2$jump.jump((ServerPlayerEntity) (Object) this);
    }
}
