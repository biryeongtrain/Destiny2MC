package net.biryeongtrain.destiny2mc.component;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.core.jmx.Server;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class RecoilManager implements ServerTickingComponent {
    private final ServerPlayerEntity player;
    private float recoilStrength = 0.0f;
    private int recoilDirection = -1;
    private int ticksUntilRecoilEnds = 0;
    private float recoilPitchPerTick = -1f;
    private float recoilYawPerTick = -1;
    private int recoilHardness = -1;

    public RecoilManager(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            this.player = (ServerPlayerEntity) player;
        } else {
            throw new IllegalArgumentException("RecoilManager can only be attached to ServerPlayerEntity");
        }
    }

    @Override
    public void serverTick() {
//        if (player.age % 2 != 0) {
//            return;
//        }

        if (this.ticksUntilRecoilEnds > 0) {
            this.ticksUntilRecoilEnds--;
            this.player.rotate(player.getYaw() +  this.recoilYawPerTick, player.getPitch() - this.recoilPitchPerTick);
            this.player.setYaw(this.player.getYaw() + this.recoilYawPerTick);
            this.player.setPitch(this.player.getPitch() - this.recoilPitchPerTick);
        }
        if (this.ticksUntilRecoilEnds == 0) {
            this.resetRecoil();
        }
    }

    public void resetRecoil() {
        this.recoilStrength = 0.0f;
        this.recoilDirection = -1;
        this.recoilPitchPerTick = -1f;
        this.recoilHardness = -1;
        this.recoilYawPerTick = -1;
    }

    public boolean isRecoilInProgress() {
        return this.ticksUntilRecoilEnds > 0;
    }

    public void startRecoil(float strength, int direction, int duration) {
        this.recoilStrength = strength;
        this.recoilDirection = direction;
        this.ticksUntilRecoilEnds = duration;
        this.recoilHardness = (this.recoilDirection / 10);
        this.recoilPitchPerTick =  (20f * this.recoilStrength / recoilHardness ) / duration ;
        this.recoilYawPerTick = getYawRecoil();
    }

    private float getYawRecoil() {
        return (float) (80 * Math.sin(Math.PI/20 * (recoilDirection - 10))) / 10 / this.ticksUntilRecoilEnds;
    }


    public void stopRecoil() {
        this.recoilStrength = 0.0f;
        this.ticksUntilRecoilEnds = 0;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        // NOOP
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        // NOOP
    }
}
