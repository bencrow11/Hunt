package org.pokesplash.hunt;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pokesplash.hunt.command.basecommand.ExampleCommand;
import org.pokesplash.hunt.config.Config;
import org.pokesplash.hunt.hunts.CurrentHunts;
import org.pokesplash.hunt.hunts.SingleHunt;
import org.pokesplash.hunt.util.CommandsRegistry;
import org.pokesplash.hunt.util.Permissions;
import org.pokesplash.hunt.hunts.SpawnRates;

public class Hunt
{
	public static final String MOD_ID = "hunt";

	public static final Logger LOGGER = LogManager.getLogger();
	public static Config config = new Config();
	public static final Permissions permissions = new Permissions();
	public static SpawnRates spawnRates = new SpawnRates();
	public static CurrentHunts hunts = new CurrentHunts();

	public static void init() {
		load();
		// Adds command to registry
		CommandsRegistry.addCommand(new ExampleCommand());
		// Registry registers all commands
		CommandRegistrationEvent.EVENT.register(CommandsRegistry::registerCommands);

		// Removes all hunts and cancels timers when server is stopping.
		LifecycleEvent.SERVER_STOPPING.register((t) -> {
			for (SingleHunt hunt : hunts.getHunts().values()) {
				hunts.removeHunt(hunt.getId());
			}
		});
	}

	/**
	 * Initializes stuff.
	 */
	public static void load() {
		config.init();
		spawnRates.init();
		hunts.init();
	}
}
