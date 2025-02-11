package org.pokesplash.hunt;

import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pokesplash.hunt.broadcast.Broadcaster;
import org.pokesplash.hunt.command.basecommand.HuntCommand;
import org.pokesplash.hunt.config.Config;
import org.pokesplash.hunt.config.Lang;
import org.pokesplash.hunt.config.Logs;
import org.pokesplash.hunt.event.CaptureEvent;
import org.pokesplash.hunt.hunts.CurrentHunts;
import org.pokesplash.hunt.hunts.HuntManager;
import org.pokesplash.hunt.hunts.SingleHunt;
import org.pokesplash.hunt.hunts.SpawnRates;
import org.pokesplash.hunt.util.CommandsRegistry;
import org.pokesplash.hunt.util.Permissions;

public class Hunt
{
	public static final String MOD_ID = "hunt";
	public static final String CONFIG_VERSION = "1.2.3";
	public static final String LANG_VERSION = "1.0.0";
	public static final Logger LOGGER = LogManager.getLogger("Hunt");
	public static Config config = new Config();
	public static final Permissions permissions = new Permissions();
	public static final Broadcaster broadcaster = new Broadcaster();
	public static SpawnRates spawnRates = new SpawnRates();
	public static CurrentHunts hunts = new CurrentHunts(null);
	public static HuntManager manager = new HuntManager();
	public static Lang language = new Lang();
	public static final Logs logs = new Logs();
	public static MinecraftServer server;

	public static void init() {
		// Adds command to registry
		CommandsRegistry.addCommand(new HuntCommand());
		CaptureEvent.registerEvents();
	}

	/**
	 * Initializes stuff.
	 */
	public static void load() {
		config.init();
		language.init();
		broadcaster.init();
		spawnRates.init();
		if (!config.isIndividualHunts()) {
			hunts.init();
		}
		logs.init();
		manager.init();
	}

	public static void check() {
		if (config.isIndividualHunts()) {
			manager.getHunts().forEach(SingleHunt::check);
		} else {
			hunts.getHuntValues().forEach(SingleHunt::check);
		}
	}
}
