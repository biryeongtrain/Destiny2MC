package net.biryeongtrain.destiny2mc.mixin;

import net.biryeongtrain.destiny2mc.DestinyComponents;
import net.biryeongtrain.destiny2mc.duck.ServerPlayerEntityDuck;
import net.biryeongtrain.destiny2mc.component.RecoilManager;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
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
    private void destiny2mc$rememberKeyAndDoJump(PlayerInputC2SPacket packet, CallbackInfo ci) {
        if(!this.getPlayer().isOnGround() && !this.lastPlayerInput.jump() && packet.input().jump()) {
            ServerPlayerEntityDuck duck = (ServerPlayerEntityDuck) getPlayer();
            duck.destiny2$doDestiny2Jump();
        }

        this.lastPlayerInput = packet.input();
    }

    @Inject(method = "onPlayerMove", at = @At("TAIL"))
    private void onPlayerMove(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (!packet.changesLook()) {
            return;
        }

        ServerPlayerEntity player = this.getPlayer();
        RecoilManager manager = DestinyComponents.RECOIL_MANAGER.get(this.getPlayer());
        if (manager.isRecoilInProgress()) {
            if (getMovementGap(player.getYaw(), player.getPitch(), packet) > 1f) {
                manager.stopRecoil();
            }
        }
    }


    private float getMovementGap(float yaw, float pitch, PlayerMoveC2SPacket packet) {
        return Math.abs(yaw - packet.getYaw(yaw)) + Math.abs(pitch - packet.getPitch(pitch));
    }
}

