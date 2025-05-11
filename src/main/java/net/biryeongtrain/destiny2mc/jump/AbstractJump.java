package net.biryeongtrain.destiny2mc.jump;

import net.biryeongtrain.destiny2mc.Destiny2mc;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class AbstractJump {
    protected boolean pressed = false;
    protected boolean jumping = false;
    protected boolean isGroundBefore = false;
    public abstract void jump(ServerPlayerEntity player);
    public void release(ServerPlayerEntity player) {
        if (player.isOnGround()) {
            if (!isGroundBefore) {
                isGroundBefore = true;
                return;
            }
            pressed = false;
            SavePath
        }
    }

    public boolean isJumping() {
        return this.jumping;
    }

    public void tick() {}
}
