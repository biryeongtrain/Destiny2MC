package net.biryeongtrain.destiny2mc.jump;

import net.minecraft.server.network.ServerPlayerEntity;

public abstract class AbstractJump {
    protected boolean pressed = false;
    protected boolean jumping = false;
    public abstract void jump(ServerPlayerEntity player);
    public void release(ServerPlayerEntity player) {
        if (player.isOnGround()) {
            pressed = false;
        }
    }

    public boolean isJumping() {
        return this.jumping;
    }

    public void tick() {}
}
