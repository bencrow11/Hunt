package org.pokesplash.hunt.automation;

import com.google.gson.Gson;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.util.Utils;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * Tracks how often pokemon are successfully hunted or removed;
 */
public class Tracker {
	// Holds the current Pokemon values.
	private HashMap<String, TrackerPokemon> tracker = new HashMap<>();

	/**
	 * Method to add to the tracker (Successfully hunted)
	 * @param pokemon The Pokemon to add to.
	 */
	public void addTracker(String pokemon) {
		if (!tracker.containsKey(pokemon)) {
			tracker.put(pokemon, new TrackerPokemon(pokemon));
		}
		tracker.get(pokemon).addTracker();
		writeToFile();
	}

	/**
	 * Method to remove the tracker (Unsuccessfully hunted)
	 * @param pokemon The Pokemon to remove from.
	 */
	public void subtractTracker(String pokemon) {
		if (!tracker.containsKey(pokemon)) {
			tracker.put(pokemon, new TrackerPokemon(pokemon));
		}
		tracker.get(pokemon).subtractTracker();
		writeToFile();
	}

	/**
	 * Gets a value of a single Pokemon.
	 * @param pokemon The pokemon to get the value for.
	 * @return The value of the tracker.
	 */
	public TrackerPokemon getTracker(String pokemon) {
		return tracker.get(pokemon);
	}

	/**
	 * Gets all of the trackers.
	 * @return The hashmap of the trackers.
	 */
	public HashMap<String, TrackerPokemon> getTrackers() {
		return tracker;
	}

	/**
	 * Initialises class.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync("/config/hunt/", "tracker.json",
				el -> {
					Gson gson = Utils.newGson();
					Tracker cfg = gson.fromJson(el, Tracker.class);
					tracker = cfg.getTrackers();
				});

		if (!futureRead.join()) {
			Hunt.LOGGER.info("No tracker.json file found for Hunt. Attempting to generate " +
					"one");
			Gson gson = Utils.newGson();
			String data = gson.toJson(this);
			CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/hunt/",
					"tracker.json", data);

			if (!futureWrite.join()) {
				Hunt.LOGGER.fatal("Could not write tracker for Hunt.");
			}
			return;
		}
		Hunt.LOGGER.info("Hunt tracker file read successfully");
	}

	private void writeToFile() {
		Gson gson = Utils.newGson();
		String data = gson.toJson(this);

		CompletableFuture<Boolean> success = Utils.writeFileAsync("/config/hunt/",
				"tracker.json", data);

		if (!success.join()) {
			Hunt.LOGGER.fatal("Could not update tracker for Hunt.");
		}
	}
}
