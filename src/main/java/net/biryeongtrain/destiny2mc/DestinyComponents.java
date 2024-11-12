package net.biryeongtrain.destiny2mc;

import net.biryeongtrain.destiny2mc.component.RecoilManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.item.ItemComponentInitializer;
import org.ladysnake.cca.api.v3.item.ItemComponentMigrationRegistry;
import org.ladysnake.cca.api.v3.level.LevelComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.level.LevelComponentInitializer;

public class DestinyComponents implements LevelComponentInitializer, ItemComponentInitializer, EntityComponentInitializer {
    public static final ComponentKey<RecoilManager> RECOIL_MANAGER = ComponentRegistry.getOrCreate(Destiny2mc.id("recoil_manager"), RecoilManager.class);

    @Override
    public void registerItemComponentMigrations(ItemComponentMigrationRegistry itemComponentMigrationRegistry) {

    }

    @Override
    public void registerLevelComponentFactories(LevelComponentFactoryRegistry levelComponentFactoryRegistry) {

    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.registerForPlayers(RECOIL_MANAGER, RecoilManager::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
