package org.pokesplash.hunt.automation;

import com.google.gson.Gson;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.util.Utils;

import java.util.concurrent.CompletableFuture;

/**
 * Config for the tracker
 */
public class TrackerConfig {
	private double multiplier; // How much should be added / removed to the value;
	private int frequency; // How often should the price be changed.
	private double maximum; // Maximum multiplier value.
	private double minimum; // Minimum multiplier value.

	/**
	 * Constructor to create object.
	 */
	public TrackerConfig() {
		multiplier = 0.1;
		frequency = 10;
		maximum = 2;
		minimum = 0.2;
	}

	/**
	 * Gets the multiplier
	 * @return the multiplier
	 */
	public double getMultiplier() {
		return multiplier;
	}

	/**
	 * Gets the frequency
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * Gets the maximum
	 * @return the maximum
	 */
	public double getMaximum() {
		return maximum;
	}

	/**
	 * Gets the minimum
	 * @return the minimum
	 */
	public double getMinimum() {
		return minimum;
	}

	/**
	 * Initialises class.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync("/config/hunt/", "tracker-config.json",
				el -> {
					Gson gson = Utils.newGson();
					TrackerConfig cfg = gson.fromJson(el, TrackerConfig.class);
					multiplier = cfg.getMultiplier();
					frequency = cfg.getFrequency();
					maximum = cfg.getMaximum();
					minimum = cfg.getMinimum();
				});

		if (!futureRead.join()) {
			Hunt.LOGGER.info("No tracker-config.json file found for Hunt. Attempting to generate " +
					"one");
			Gson gson = Utils.newGson();
			String data = gson.toJson(this);
			CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/hunt/",
					"tracker-config.json", data);

			if (!futureWrite.join()) {
				Hunt.LOGGER.fatal("Could not write tracker-config for Hunt.");
			}
			return;
		}
		Hunt.LOGGER.info("Hunt tracker file read successfully");
	}
}
