package org.pokesplash.hunt;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import org.pokesplash.hunt.command.basecommand.ExampleCommand;
import org.pokesplash.hunt.util.CommandsRegistry;
import org.pokesplash.hunt.util.Permissions;

public class Hunt
{
	public static final String MOD_ID = "hunt";

	public static final Permissions permissions = new Permissions();

	public static void init() {
		// Adds command to registry
		CommandsRegistry.addCommand(new ExampleCommand());
		// Registry registers all commands
		CommandRegistrationEvent.EVENT.register(CommandsRegistry::registerCommands);
	}
}
