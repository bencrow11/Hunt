package org.pokesplash.hunt.config;

import com.google.gson.Gson;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.old.LangOld;
import org.pokesplash.hunt.util.Utils;

import java.util.concurrent.CompletableFuture;

/**
 * Class for customization of language.
 */
public class Lang extends Versioned {
	private String title; // Title of the menu
	private String fillerMaterial; // The material used as the border in the UI.
	private String reloadMessage; // Reload message.
	private String captureHuntBroadcast; // Message broadcast to players when a hunt has been caught.
	private String payMessage; // Message sent when a player gets paid.
	private String endedHuntMessage; // The message sent when a hunt ends.
	private String newHuntMessage; // The message sent when a new hunt begins.
	private String reward;
	private String timeRemaining;
	private Lore lore;

	public Lang() {
		super(Hunt.LANG_VERSION);
		title = "Hunt";
		fillerMaterial = "minecraft:white_stained_glass_pane";
		reloadMessage = "§aReloaded Configs!";
		captureHuntBroadcast = "§5[Hunt] §e{player} §ahas successfully hunted §e{pokemon}";
		payMessage = "§5[Hunt] §eYou were paid {price} from hunt!";
		endedHuntMessage = "§5[Hunt] §3The hunt for {pokemon} has ended!";
		newHuntMessage = "§5[Hunt] §bThe hunt for {pokemon} has begun!";
		reward = "§6Reward: §e";
		timeRemaining = "§9Time Remaining: §b";
		lore = new Lore();
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
	public String getReward() {
		return reward;
	}
	public String getTimeRemaining() {
		return timeRemaining;
	}
	public Lore getLore() {
		return lore;
	}

	/**
	 * Reads or creates the lang file.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync("/config/hunt/", "lang.json",
				el -> {
					Gson gson = Utils.newGson();
					Versioned version = gson.fromJson(el, Versioned.class);

					if (version == null ||
							version.getVersion() == null ||
							!version.getVersion().equals(Hunt.LANG_VERSION)) {
						LangOld langOld = new LangOld();
						title = langOld.getTitle();
						fillerMaterial = langOld.getFillerMaterial();
						reloadMessage = langOld.getReloadMessage();
						captureHuntBroadcast = langOld.getCaptureHuntBroadcast();
						payMessage = langOld.getPayMessage();
						endedHuntMessage = langOld.getEndedHuntMessage();
						newHuntMessage = langOld.getNewHuntMessage();
						reward = langOld.getReward();
						timeRemaining = langOld.getTimeRemaining();
						write();
					}

					Lang lang = gson.fromJson(el, Lang.class);
					title = lang.getTitle();
					fillerMaterial = lang.getFillerMaterial();
					reloadMessage = lang.getReloadMessage();
					captureHuntBroadcast = lang.getCaptureHuntBroadcast();
					payMessage = lang.getPayMessage();
					endedHuntMessage = lang.getEndedHuntMessage();
					newHuntMessage = lang.getNewHuntMessage();
					reward = lang.getReward();
					timeRemaining = lang.getTimeRemaining();
					lore = lang.getLore();
				});

		if (!futureRead.join()) {
			Hunt.LOGGER.info("No lang.json file found for Hunt. Attempting to generate one.");

			boolean isSuccess = write();

			if (!isSuccess) {
				Hunt.LOGGER.fatal("Could not write lang.json file for Hunt.");
			}
			Hunt.LOGGER.info("Hunt lang file read successfully.");
		}
	}

	private boolean write() {
		Gson gson = Utils.newGson();
		String data = gson.toJson(this);
		CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/hunt/", "lang.json",
				data);
		return futureWrite.join();
	}
}
