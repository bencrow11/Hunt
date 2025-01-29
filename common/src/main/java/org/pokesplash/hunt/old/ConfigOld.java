package org.pokesplash.hunt.old;

import com.google.gson.Gson;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.config.*;
import org.pokesplash.hunt.enumeration.Economy;
import org.pokesplash.hunt.util.Utils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Config file.
 */
public class ConfigOld extends Versioned {
	private boolean useImpactorDefaultCurrency; // Should Hunt use Impactor's default currency.
	private String impactorCurrencyName; // The name of the currency Impactor should use.
	private boolean individualHunts; // if hunts should be individual for each player.
	private boolean sendHuntEndMessage; // Should the mod send a message when a hunt ends.
	private boolean sendHuntBeginMessage; // Should the mod send a message when a hunt begins.
	private boolean timerCooldowns; // Should the mod wait before sending the broadcasts.
	private int bufferDuration; // The duration the buffer should wait to collect broadcasts.
	private int huntDuration; // How long each hunt should last, in minutes.
	private int huntAmount; // How many hunts should there be at once.
	private RarityConfig rarity; // The rarity borders.
	private RewardsConfig rewards; // The rewards for the hunts.
	private Properties matchProperties; // What properties should be checked to complete the hunt.
	private ArrayList<CustomPrice> customPrices; // List of custom prices.
	private ArrayList<String> blacklist; // List if Pokemon that shouldn't be added to Hunt.

	public ConfigOld() {
		super(Hunt.CONFIG_VERSION);
		useImpactorDefaultCurrency = true;
		impactorCurrencyName = "impactor:huntcoins";
		individualHunts = false;
		sendHuntEndMessage = true;
		sendHuntBeginMessage = true;
		timerCooldowns = true;
		bufferDuration = 5;
		huntDuration = 60;
		huntAmount = 7;
		rarity = new RarityConfig();
		rewards = new RewardsConfig();
		matchProperties = new Properties();
		customPrices = new ArrayList<>();
		customPrices.add(new CustomPrice());
		blacklist = new ArrayList<>();
	}

	private boolean write() {
		Gson gson = Utils.newGson();
		String data = gson.toJson(this);
		CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/hunt/", "config.json",
				data);
		return futureWrite.join();
	}


	public int getBufferDuration() {
		return bufferDuration;
	}

	public boolean isTimerCooldowns() {
		return timerCooldowns;
	}

	public boolean isIndividualHunts() {
		return individualHunts;
	}

	public int getHuntDuration() {
		return huntDuration;
	}

	public int getHuntAmount() {
		return huntAmount;
	}


	public Properties getMatchProperties() {
		return matchProperties;
	}

	public ArrayList<CustomPrice> getCustomPrices() {
		return customPrices;
	}

	public boolean isSendHuntEndMessage() {
		return sendHuntEndMessage;
	}

	public boolean isSendHuntBeginMessage() {
		return sendHuntBeginMessage;
	}

	public ArrayList<String> getBlacklist() {
		return blacklist;
	}

	public RarityConfig getRarity() {
		return rarity;
	}

	public RewardsConfig getRewards() {
		return rewards;
	}

	public boolean blacklistContains(String pokemon) {
		for (String name : blacklist) {
			if (name.equalsIgnoreCase(pokemon)) return true;
		}
		return false;
	}

	public boolean isUseImpactorDefaultCurrency() {
		return useImpactorDefaultCurrency;
	}

	public String getImpactorCurrencyName() {
		return impactorCurrencyName;
	}
}
