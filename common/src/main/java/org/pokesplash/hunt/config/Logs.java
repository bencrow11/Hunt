package org.pokesplash.hunt.config;

import com.google.gson.Gson;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.util.Utils;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Config file.
 */
public class Logs {
	private HashMap<UUID, Double> data;

	public Logs() {
		data = new HashMap<>();
	}

	/**
	 * Reads the config or writes one if a config doesn't exist.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync("/config/hunt/", "logs.json",
				el -> {
					Gson gson = Utils.newGson();
					Logs cfg = gson.fromJson(el, Logs.class);
					data = cfg.getData();
				});

		// If the config couldn't be read, write a new one.
		if (!futureRead.join()) {
			Hunt.LOGGER.info("No logs.json file found for Hunt. Attempting to generate one.");
			Gson gson = Utils.newGson();
			String data = gson.toJson(this);
			CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/hunt/", "logs.json",
					data);

			// If the write failed, log fatal.
			if (!futureWrite.join()) {
				Hunt.LOGGER.fatal("Could not write logs for Hunt.");
			}
			return;
		}
		Hunt.LOGGER.info("Hunt logs file read successfully.");
	}

	/**
	 * Bunch of Getters
	 */
	public HashMap<UUID, Double> getData() {
		return data;
	}

	public void addValue(UUID player, double value) {
		if (!data.containsKey(player)) {
			data.put(player, 0.0);
		}

		double current = data.get(player);
		data.put(player, current + value);

		Gson gson = Utils.newGson();
		String data = gson.toJson(this);
		CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/hunt/", "logs.json",
				data);

		// If the write failed, log fatal.
		if (!futureWrite.join()) {
			Hunt.LOGGER.fatal("Could not write logs for Hunt.");
		}
	}
}
