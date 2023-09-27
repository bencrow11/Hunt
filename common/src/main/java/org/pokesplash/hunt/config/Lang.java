package org.pokesplash.hunt.config;

import com.google.gson.Gson;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.util.Utils;

import java.util.concurrent.CompletableFuture;

/**
 * Class for customization of language.
 */
public class Lang {
	private String title; // Title of the menu
	private String fillerMaterial; // The material used as the border in the UI.
	private String reloadMessage; // Reload message.

	public Lang() {
		title = "Hunt";
		fillerMaterial = "minecraft:white_stained_glass_pane";
		reloadMessage = "§2Reloaded Configs!";
	}

	public String getTitle() {
		return title;
	}
	public String getFillerMaterial() {
		return fillerMaterial;
	}
	public String getReloadMessage() {
		return reloadMessage;
	}

	/**
	 * Reads or creates the lang file.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync("/config/hunt/", "lang.json",
				el -> {
					Gson gson = Utils.newGson();
					Lang lang = gson.fromJson(el, Lang.class);
					title = lang.getTitle();
					fillerMaterial = lang.getFillerMaterial();
					reloadMessage = lang.getReloadMessage();
				});

		if (!futureRead.join()) {
			Hunt.LOGGER.info("No lang.json file found for Hunt. Attempting to generate one.");
			Gson gson = Utils.newGson();
			String data = gson.toJson(this);
			CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/hunt/", "lang.json",
					data);

			if (!futureWrite.join()) {
				Hunt.LOGGER.fatal("Could not write lang.json file for Hunt.");
			}
			Hunt.LOGGER.info("Hunt lang file read successfully.");
		}
	}
}