package net.biryeongtrain.destiny2mc.gun;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.biryeongtrain.destiny2mc.DestinyComponents;
import net.biryeongtrain.destiny2mc.component.RecoilManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.List;

public class Gun extends Item implements PolymerItem
{
    private ElementType type;
    private Sounds Sound;

    public Gun(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, PacketContext context) {
        return PolymerItem.super.getPolymerItemStack(itemStack, tooltipType, context);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(type.isAdvanced() ? Text.literal("Test!") : Text.literal("Well... this is basic."));
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext packetContext) {
        return Items.CROSSBOW;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            RecoilManager manager = DestinyComponents.RECOIL_MANAGER.get(player);
            manager.startRecoil(3.0f, 40, 6);
            ItemStack stack = user.getStackInHand(hand);
            player.getItemCooldownManager().set(stack, 20);
        }
        return super.use(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            RecoilManager manager = DestinyComponents.RECOIL_MANAGER.get(player);
            if (manager.isRecoilInProgress()) {
                manager.stopRecoil();
            }
        }
        return super.finishUsing(stack, world, user);
    }

    public void recoil(ServerPlayerEntity user, ItemStack stack) {
        // -180 ~ 180 (yaw, pitch)
        // 방법 1 :
        user.rotate(user.getYaw(), user.getPitch() - 1.5f);
    }

    public static class Sounds {
        Identifier fireSound;
        Identifier[] reloadSound;
        Identifier[] specialSound;
        
        public void playFireSound(ServerPlayerEntity user, ItemStack stack) {
            ServerWorld world = user.getServerWorld();
            world.playSound(null, user.getBlockPos(), SoundEvent.of(fireSound), user.getSoundCategory(), 1.0F, 1.0F);
        }
    }
}
