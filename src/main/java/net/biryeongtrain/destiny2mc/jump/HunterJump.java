package net.biryeongtrain.destiny2mc.jump;

import net.biryeongtrain.destiny2mc.utils.JumpUtils;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class HunterJump extends AbstractJump {

    @Override
    public void jump(ServerPlayerEntity player) {
        if (pressed) {
            return;
        }

        double d = player.getVelocity().lengthSquared();
        float yaw = player.getHeadYaw();
        float f = MathHelper.sin(yaw * ((float)Math.PI / 180));
        float g = MathHelper.cos(yaw * ((float)Math.PI / 180));
        double yVelocity = player.getVelocity().y;
        yVelocity = -yVelocity + 0.63;

        int speedLevel = player.getStatusEffect(StatusEffects.SPEED) != null ? player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1 : 0;

        Vec3d vec3d = (d > 1.0 ? player.getVelocity().normalize() : player.getVelocity()).multiply(0.25 + 0.25 * speedLevel);
        Vec3d velocity = new Vec3d((vec3d.x * (double)g - vec3d.z * (double)f) * 1, yVelocity, (vec3d.z * (double)g + vec3d.x * (double)f) * 1)
                .add(player.getVelocity().x, 0, player.getVelocity().z);
        
        JumpUtils.sendVelocityDelta(player, velocity);
        player.getWorld().playSound(null, player.getPos().x, player.getPos().y - 1, player.getPos().z, SoundEvents.ENTITY_BREEZE_WIND_BURST, SoundCategory.PLAYERS, 1, 1);
        player.setVelocity(player.getVelocity().add(velocity));

        pressed = true;
    }
}
