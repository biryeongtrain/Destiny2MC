package net.biryeongtrain.destiny2mc.entity;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import net.biryeongtrain.destiny2mc.gun.Gun;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.List;

public class BulletProjectileEntity extends PersistentProjectileEntity implements PolymerEntity {
    private Gun gun;
    private ItemStack displayItemStack;
    public BulletProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void modifyRawTrackedData(List<DataTracker.SerializedEntry<?>> data, ServerPlayerEntity player, boolean initial) {
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.TELEPORTATION_DURATION, 2));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.SCALE, new Vector3f(1f)));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.BILLBOARD, (byte)DisplayEntity.BillboardMode.CENTER.ordinal()));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.Item.ITEM, this.displayItemStack));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.Item.ITEM_DISPLAY, ModelTransformationMode.FIXED.getIndex()));
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return null;
    }

    @Override
    public EntityType<?> getPolymerEntityType(PacketContext packetContext) {
        return EntityType.ITEM_DISPLAY;
    }
}
