package org.pokesplash.hunt.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.Pokemon;
import kotlin.Unit;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.api.event.HuntEvents;
import org.pokesplash.hunt.api.event.events.CompletedEvent;
import org.pokesplash.hunt.hunts.ReplacedHunt;
import org.pokesplash.hunt.hunts.SingleHunt;
import org.pokesplash.hunt.util.ImpactorService;
import org.pokesplash.hunt.util.Utils;

import java.util.UUID;

public abstract class EventHandler {
	public static void registerEvents() {
		// Capture event checks the current hunts.
		CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, e -> {

			ServerPlayer player = e.getPlayer();
			Pokemon pokemon = e.getPokemon();

			UUID matchedUUID;

			if (Hunt.config.isIndividualHunts()) {
				matchedUUID = Hunt.manager.getPlayerHunts(player.getUUID()).matches(pokemon);
			} else {
				matchedUUID = Hunt.hunts.matches(pokemon);
			}

			// If the pokemon doesn't match a hunt pokemon, matchedUUID will be null, otherwise it'll be a UUID.
			if (matchedUUID != null) {
				SingleHunt hunt;

				if (Hunt.config.isIndividualHunts()) {
					hunt = Hunt.manager.getPlayerHunts(player.getUUID()).getHunt(matchedUUID);
				} else {
					hunt = Hunt.hunts.getHunt(matchedUUID);
				}

				double price = hunt.getPrice();

				// Checks there's a price. This should always be true.
				if (price != -1) {
					try {
						// Performs the transaction.
						boolean success = ImpactorService.add(ImpactorService.getAccount(player.getUUID()), price);

						// Runs commands
						Utils.runCommands(hunt.getCommands(), player, pokemon, price);

						// If the transaction was successful, replace the caught pokemon in hunt and send some messages.
						if (success) {

							ReplacedHunt replacedHunt;
							if (Hunt.config.isIndividualHunts()) {
								replacedHunt = Hunt.manager.getPlayerHunts(player.getUUID())
										.replaceHunt(matchedUUID, false);
							} else {
								replacedHunt = Hunt.hunts.replaceHunt(matchedUUID, false);
							}

							player.sendSystemMessage(Component.literal(Utils.formatPlaceholders(
									Hunt.language.getPayMessage(), player, pokemon, price
							)));

							if (!Hunt.config.isIndividualHunts()) {
								Utils.broadcastMessage(Utils.formatPlaceholders(
										Hunt.language.getCaptureHuntBroadcast(), player, pokemon, price
								));
							}

							if (replacedHunt != null) {
								HuntEvents.COMPLETED.trigger(new CompletedEvent(replacedHunt.getOldHunt(), player.getUUID()));
							}

							Hunt.logs.addValue(player.getUUID(), price);

							return Unit.INSTANCE;
						}
					} catch (NullPointerException ex) {
						// Just in case playerlist is empty for some random reason.
						ex.printStackTrace();
					}
				}
				// If any errors occur, send log to console.
				Hunt.LOGGER.error("Could not process hunt " + matchedUUID + " for " + player.getName().getString());
			}
			return Unit.INSTANCE;
		});
	}
}
