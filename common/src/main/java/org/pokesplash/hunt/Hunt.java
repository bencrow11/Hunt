package org.pokesplash.hunt;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pokesplash.hunt.command.basecommand.HuntCommand;
import org.pokesplash.hunt.config.Config;
import org.pokesplash.hunt.config.Lang;
import org.pokesplash.hunt.event.EventHandler;
import org.pokesplash.hunt.hunts.CurrentHunts;
import org.pokesplash.hunt.hunts.SingleHunt;
import org.pokesplash.hunt.hunts.SpawnRates;
import org.pokesplash.hunt.util.CommandsRegistry;
import org.pokesplash.hunt.util.Permissions;

public class Hunt
{
	public static final String MOD_ID = "hunt";

	public static final Logger LOGGER = LogManager.getLogger();
	public static Config config = new Config();
	public static final Permissions permissions = new Permissions();
	public static SpawnRates spawnRates = new SpawnRates();
	public static CurrentHunts hunts = new CurrentHunts();
	public static Lang language = new Lang();
	public static MinecraftServer server;

	public static void init() {
		// Adds command to registry
		CommandsRegistry.addCommand(new HuntCommand());
		// Registry registers all commands
		CommandRegistrationEvent.EVENT.register(CommandsRegistry::registerCommands);
		EventHandler.registerEvents();

		LifecycleEvent.SERVER_STARTED.register((t) -> {
			load();
		});

		// Removes all hunts and cancels timers when server is stopping.
		LifecycleEvent.SERVER_STOPPING.register((t) -> {
			for (SingleHunt hunt : hunts.getHunts().values()) {
				hunts.removeHunt(hunt.getId(), false);
			}
		});

		// Just gets the server for easy reference.
		LifecycleEvent.SERVER_LEVEL_LOAD.register((t) -> {
			server = t.getServer();
		});
	}

	/**
	 * Initializes stuff.
	 */
	public static void load() {
		config.init();
		spawnRates.init();
		hunts.init();
		language.init();
	}
}
