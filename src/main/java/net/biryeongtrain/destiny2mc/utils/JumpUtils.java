package net.biryeongtrain.destiny2mc.utils;

import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class JumpUtils {
    public static void sendVelocityDelta(ServerPlayerEntity player, Vec3d delta) {
        player.networkHandler.sendPacket(new ExplosionS2CPacket(new Vec3d(player.getX(),  player.getY() - 9999, player.getZ()), Optional.of(delta),
                ParticleTypes.BUBBLE, Registries.SOUND_EVENT.getEntry(SoundEvents.INTENTIONALLY_EMPTY)));
    }
}
