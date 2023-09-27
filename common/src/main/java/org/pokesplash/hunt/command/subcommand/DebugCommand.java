package org.pokesplash.hunt.command.subcommand;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.hunts.SingleHunt;
import org.pokesplash.hunt.util.Subcommand;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class DebugCommand extends Subcommand {

	public DebugCommand() {
		super("§9Usage:\\n§3- hunt debug");
	}

	@Override
	public LiteralCommandNode<CommandSourceStack> build() {
		return Commands.literal("debug")
				.requires(ctx -> {
					if (ctx.isPlayer()) {
						return Hunt.permissions.hasPermission(ctx.getPlayer(),
								Hunt.permissions.getPermission("HuntDebug"));
					} else {
						return true;
					}
				})
				.executes(this::showUsage)
				.then(Commands.argument("slot", IntegerArgumentType.integer())
						.suggests((ctx, builder) -> {
							for (int i = 1; i < 7; i++) {
								builder.suggest(i);
							}
							return builder.buildFuture();
						})
						.executes(this::run))
				.build();
	}

	/**
	 * Method to perform the logic when the command is executed.
	 *
	 * @param context the source of the command.
	 * @return integer to complete command.
	 */
	@Override
	public int run(CommandContext<CommandSourceStack> context) {

		if (!context.getSource().isPlayer()) {
			context.getSource().sendSystemMessage(Component.literal("This command must be ran by a player."));
			return 1;
		}

		ServerPlayer player = context.getSource().getPlayer();

		int slot = IntegerArgumentType.getInteger(context, "slot") - 1;

		PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
		Pokemon pokemon = party.get(slot);

		if (pokemon == null) {
			context.getSource().sendSystemMessage(Component.literal("§cNo Pokemon in slot " + slot));
			return 1;
		}

		String output = "Pokemon: " + pokemon.getSpecies().getName() + "\n" +
				"Form: " + pokemon.getForm().getName() + "\n" +
				"Ability: " + pokemon.getAbility().getName() + "\n" +
				"Gender: " + pokemon.getGender().name() + "\n" +
				"Nature: " + pokemon.getNature().getName().getPath() + "\n\n" +
				checkListings(pokemon);

		context.getSource().sendSystemMessage(Component.literal(output));
		return 1;
	}

	private String checkListings(Pokemon playerMon) {
		String total = "";

		Collection<SingleHunt> hunts = Hunt.hunts.getHunts().values();
		for (SingleHunt hunt : hunts) {

			Pokemon huntMon = hunt.getPokemon();

			String output = "Listing: " + huntMon.getSpecies().getName() + "\n";

			output += "Species: " + huntMon.getSpecies().getName().equalsIgnoreCase(playerMon.getSpecies().getName()) +
					"\n";

			output += "Form: " + huntMon.getForm().getName().equalsIgnoreCase(playerMon.getForm().getName()) + "\n";

			output += "Ability: " + huntMon.getAbility().getName().equalsIgnoreCase(playerMon.getAbility().getName()) +
					"\n";

			output += "Gender: " +huntMon.getGender().name().equalsIgnoreCase(playerMon.getGender().name()) + "\n";

			output += "Nature: " +huntMon.getNature().getName().getPath().equalsIgnoreCase(playerMon.getNature().getName().getPath()) + "\n";

			output += "Shiny: " + (huntMon.getShiny() == playerMon.getShiny()) + "\n";

			output += "Matches: " + hunt.matches(playerMon) + "\n\n";

			total += output;
		}
		return total;
	}
}