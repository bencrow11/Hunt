package org.pokesplash.hunt;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.spawning.BestSpawner;
import com.cobblemon.mod.common.api.spawning.CobblemonSpawnPools;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnDetail;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.util.adapters.PokemonPropertiesAdapter;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import org.pokesplash.hunt.command.basecommand.ExampleCommand;
import org.pokesplash.hunt.util.CommandsRegistry;
import org.pokesplash.hunt.util.Permissions;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Hunt
{
	public static final String MOD_ID = "hunt";

	public static final Permissions permissions = new Permissions();
	public static final SpawnRates spawnRates = new SpawnRates();

	public static void init() {
		// Adds command to registry
		CommandsRegistry.addCommand(new ExampleCommand());
		// Registry registers all commands
		CommandRegistrationEvent.EVENT.register(CommandsRegistry::registerCommands);

		spawnRates.init();
	}
}
