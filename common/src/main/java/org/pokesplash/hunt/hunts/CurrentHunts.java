package org.pokesplash.hunt.hunts;

import com.cobblemon.mod.common.pokemon.Pokemon;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class CurrentHunts {
	private HashMap<UUID, SingleHunt> hunts; // List of current hunts.

	/**
	 * Constructor that generates a bunch of hunts when the server starts.
	 */
	public CurrentHunts() {
		hunts = new HashMap<>();
	}

	/**
	 * Initializes hashmap with hunts.
	 */
	public void init() {
		for (int x=0; x < Hunt.config.getHuntAmount(); x++) {
			addHunt();
		}
	}

	/**
	 * Adds a new hunt to the current hunts.
	 * @return true if successful.
	 */
	public SingleHunt addHunt() {
		// If the maximum hunt amount is reached, don't add another.
		if (hunts.size() < Hunt.config.getHuntAmount()) {
			SingleHunt hunt = new SingleHunt();

			// If the species already exists, recurse and try again.
			ArrayList<UUID> huntIds = new ArrayList<>(hunts.keySet());
			for (UUID id : huntIds) {
				if (hunts.containsKey(id)) {
					Pokemon currentPokemon = hunts.get(id).getPokemon();
					if (currentPokemon.getSpecies().getName().equalsIgnoreCase(hunt.getPokemon().getSpecies().getName())) {
						return addHunt();
					}
				}
			}

			// If the config setting is enabled, send the broadcast.
			if (Hunt.config.isSendHuntBeginMessage()) {
				Utils.broadcastMessage(Utils.formatPlaceholders(
						Hunt.language.getNewHuntMessage(), null, hunt.getPokemon(), hunt.getPrice()
				));
			}

			// Add the hunt to the list.
			return hunts.put(hunt.getId(), hunt);
		}
		return null;
	}

	/**
	 * Removes a hunt from the list.
	 * @param id the ID of the hunt to remove.
	 * @return true if successfully removed.
	 */
	public SingleHunt removeHunt(UUID id, boolean broadcast) {
		SingleHunt removedHunt = hunts.remove(id);
		if (removedHunt != null) {
			removedHunt.getTimer().cancel(); // Cancel timer on hunt.

			// If broadcasts are enabled and the method call wants it broadcast, send it.
			if (Hunt.config.isSendHuntEndMessage() && broadcast) {
				Utils.broadcastMessage(Utils.formatPlaceholders(
						Hunt.language.getEndedHuntMessage(), null, removedHunt.getPokemon(), removedHunt.getPrice()
				));
			}
		}
		return removedHunt;
	}

	/**
	 * Removes a hunt with the ID and adds a new one.
	 * @param id the ID of the hunt to remove.
	 * @return the new Hunt replacement.
	 */
	public ReplacedHunt replaceHunt(UUID id, boolean broadcast) {
		SingleHunt oldHunt = removeHunt(id, broadcast);

		if (oldHunt != null) {
			return new ReplacedHunt(oldHunt, addHunt());
		}
		return null;
	}

	/**
	 * Checks that a given pokemon matches one from the current hunt pool.
	 * @param pokemon The pokemon to check.
	 * @return UUID of the hunt that matches the hunt.
	 */
	public UUID matches(Pokemon pokemon) {
		for (UUID id : hunts.keySet()) {
			if (hunts.get(id).matches(pokemon)) {
				return id;
			}
		}
		return null;
	}

	/**
	 * Gets the price of the specified hunt.
	 * @param uuid The hunt UUID to check.
	 * @return The price of the hunt.
	 */
	public double getPrice(UUID uuid) {
		SingleHunt hunt = hunts.get(uuid);
		if (hunt != null) {
			return hunt.getPrice();
		}
		return -1;
	}


	/**
	 * Gets all of the hunts.
	 * @return
	 */
	public HashMap<UUID, SingleHunt> getHunts() {
		return hunts;
	}
}
