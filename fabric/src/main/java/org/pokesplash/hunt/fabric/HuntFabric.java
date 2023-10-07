package org.pokesplash.hunt.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.util.CommandsRegistry;
import org.pokesplash.hunt.util.Utils;

public class HuntFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Hunt.init();
        CommandRegistrationCallback.EVENT.register(CommandsRegistry::registerCommands);
        ServerLifecycleEvents.SERVER_STARTED.register(t -> Hunt.load());
        ServerLifecycleEvents.SERVER_STOPPING.register(t -> Utils.removeAllHunts());
        ServerWorldEvents.LOAD.register((t, e) -> Hunt.server = t);
    }
}