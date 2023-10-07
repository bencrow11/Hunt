package org.pokesplash.hunt;

import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pokesplash.hunt.api.event.HuntEvents;
import org.pokesplash.hunt.command.basecommand.HuntCommand;
import org.pokesplash.hunt.config.Config;
import org.pokesplash.hunt.config.Lang;
import org.pokesplash.hunt.event.EventHandler;
import org.pokesplash.hunt.hunts.CurrentHunts;
import org.pokesplash.hunt.hunts.SpawnRates;
import org.pokesplash.hunt.util.CommandsRegistry;
import org.pokesplash.hunt.util.Permissions;

import java.util.Timer;
import java.util.TimerTask;

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
		EventHandler.registerEvents();

		Timer timer = new Timer();

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				HuntEvents.COMPLETED.subscribe(el -> {
					System.out.println(el.getPlayer());
					System.out.println(el.getHunt().getPokemon().getDisplayName().getString());
				});
			}
		}, 1000 * 30);
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
