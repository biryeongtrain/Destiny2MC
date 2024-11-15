package net.biryeongtrain.destiny2mc.entity;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import xyz.nucleoid.packettweaker.PacketContext;

public class BulletProjectileEntity extends ProjectileEntity implements PolymerEntity {
    public BulletProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        // TODO HIDE VANILLA ENTITY DATA
    }

    @Override
    public EntityType<?> getPolymerEntityType(PacketContext packetContext) {
        return EntityType.AREA_EFFECT_CLOUD;
    }
}
