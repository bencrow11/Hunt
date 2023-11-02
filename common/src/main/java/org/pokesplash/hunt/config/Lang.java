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
	private String captureHuntBroadcast; // Message broadcast to players when a hunt has been caught.
	private String payMessage; // Message sent when a player gets paid.
	private String endedHuntMessage; // The message sent when a hunt ends.
	private String newHuntMessage; // The message sent when a new hunt begins.
	private String ability;
	private String gender;
	private String nature;
	private String reward;
	private String timeRemaining;

	public Lang() {
		title = "Hunt";
		fillerMaterial = "minecraft:white_stained_glass_pane";
		reloadMessage = "§aReloaded Configs!";
		captureHuntBroadcast = "§5[Hunt] §e{player} §ahas successfully hunted §e{pokemon}";
		payMessage = "§5[Hunt] §eYou were paid {price} from hunt!";
		endedHuntMessage = "§5[Hunt] §3The hunt for {pokemon} has ended!";
		newHuntMessage = "§5[Hunt] §bThe hunt for {pokemon} has begun!";
		ability = "§2Ability: ";
		gender = "§3Gender: §b";
		nature = "§5Nature: ";
		reward = "§6Reward: §e";
		timeRemaining = "§9Time Remaining: §b";
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
	public String getCaptureHuntBroadcast() {
		return captureHuntBroadcast;
	}
	public String getPayMessage() {
		return payMessage;
	}
	public String getEndedHuntMessage() {
		return endedHuntMessage;
	}

	public String getNewHuntMessage() {
		return newHuntMessage;
	}
	public String getAbility() {
		return ability;
	}
	public String getGender() {
		return gender;
	}
	public String getNature() {
		return nature;
	}
	public String getReward() {
		return reward;
	}
	public String getTimeRemaining() {
		return timeRemaining;
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
					captureHuntBroadcast = lang.getCaptureHuntBroadcast();
					payMessage = lang.getPayMessage();
					endedHuntMessage = lang.getEndedHuntMessage();
					newHuntMessage = lang.getNewHuntMessage();
					ability = lang.getAbility();
					gender = lang.getGender();
					nature = lang.getNature();
					reward = lang.getReward();
					timeRemaining = lang.getTimeRemaining();
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
