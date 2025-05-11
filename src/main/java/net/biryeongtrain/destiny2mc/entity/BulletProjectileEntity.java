package net.biryeongtrain.destiny2mc.entity;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import net.biryeongtrain.destiny2mc.gun.Gun;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.List;

public class BulletProjectileEntity extends PersistentProjectileEntity implements PolymerEntity {
    private Gun gun;
    private ItemStack displayItemStack;
    private LivingEntity owner;
    public BulletProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.gun = null;
        this.displayItemStack = ItemStack.EMPTY;
        this.owner = null;
    }



    public Gun getGun() {
        return gun;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }

    public ItemStack getDisplayItemStack() {
        return displayItemStack;
    }

    public void setDisplayItemStack(ItemStack displayItemStack) {
        this.displayItemStack = displayItemStack;
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        return owner;
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
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
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
    }



    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (this.gun == null) {
            this.discard();
            return;
        }
        float damage = this.gun.getFinalDamage();
        DamageSource source = this.getEntityWorld().getDamageSources().arrow(this, this.getOwner());
        entityHitResult.getEntity().damage((ServerWorld) this.getEntityWorld(), source, damage);

        this.discard();
    }

    @Override
    public EntityType<?> getPolymerEntityType(PacketContext packetContext) {
        return EntityType.ITEM_DISPLAY;
    }
}
