package org.pokesplash.hunt.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.util.CommandsRegistry;
import org.pokesplash.hunt.util.Utils;

@Mod(Hunt.MOD_ID)
public class HuntForge {
    public HuntForge() {
        Hunt.init();
        MinecraftForge.EVENT_BUS.register(this);
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
    public void serverStoppingEvent(ServerStoppingEvent event) {
        Utils.removeAllHunts();
    }

    @SubscribeEvent
    public void worldLoadEvent(LevelEvent.Load event) {
        Hunt.server = event.getLevel().getServer();
    }

    @SubscribeEvent
    public void playerJoinEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Hunt.manager.addPlayer(event.getEntity().getUUID());
    }
}