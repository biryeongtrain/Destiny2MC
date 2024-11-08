package net.biryeongtrain.destiny2mc.mixin;

import net.biryeongtrain.destiny2mc.ServerPlayerEntityDuck;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public abstract ServerPlayerEntity getPlayer();
    @Unique
    private PlayerInput lastPlayerInput = PlayerInput.DEFAULT;

    @Inject(method = "onPlayerInput", at = @At("TAIL"))
    private void onPlayerInput(PlayerInputC2SPacket packet, CallbackInfo ci) {
        if(!this.getPlayer().isOnGround() && !this.lastPlayerInput.jump() && packet.input().jump()) {
            ServerPlayerEntityDuck duck = (ServerPlayerEntityDuck) getPlayer();
            duck.destiny2$doDestiny2Jump();
        }

        this.lastPlayerInput = packet.input();
    }
}

