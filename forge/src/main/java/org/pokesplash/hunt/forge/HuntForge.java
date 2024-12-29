package org.pokesplash.hunt.forge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.util.CommandsRegistry;

@Mod(Hunt.MOD_ID)
public class HuntForge {
    public HuntForge() {
        Hunt.init();
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        CommandsRegistry.registerCommands(event.getDispatcher());
    }

    @SubscribeEvent
    public void serverStartedEvent(ServerStartedEvent event) {
        Hunt.load();
    }

    @SubscribeEvent
    public void worldLoadEvent(LevelEvent.Load event) {
        Hunt.server = event.getLevel().getServer();
    }

    @SubscribeEvent
    public void playerJoinEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Hunt.manager.addPlayer(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public void serverTickEvent(ServerTickEvent.Post event) {
        Hunt.check();
    }
}