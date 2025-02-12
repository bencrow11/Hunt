package org.pokesplash.hunt.config;

import com.google.gson.Gson;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.enumeration.Economy;
import org.pokesplash.hunt.old.ConfigOld;
import org.pokesplash.hunt.util.Utils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Config file.
 */
public class Config extends Versioned {
	private Economy economy; // The economy mod to use. Options are Impactor, Pebbles, Blanket.
	private boolean useImpactorDefaultCurrency; // Should Hunt use Impactor's default currency.
	private String currencyName; // The name of the currency that should be used.
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

	public Config() {
		super(Hunt.CONFIG_VERSION);
		economy = Economy.IMPACTOR;
		useImpactorDefaultCurrency = true;
		currencyName = "impactor:huntcoins";
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

	/**
	 * Reads the config or writes one if a config doesn't exist.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync("/config/hunt/", "config.json",
				el -> {
					Gson gson = Utils.newGson();
					Versioned versioned = gson.fromJson(el, Versioned.class);

					Config cfg = gson.fromJson(el, Config.class);
					if (cfg.getHuntAmount() > 28) {
						huntAmount = 28;
						Hunt.LOGGER.error("Hunt amount can not be higher than 28");
					} else {
						huntAmount = cfg.getHuntAmount();
					}
					useImpactorDefaultCurrency = cfg.isUseImpactorDefaultCurrency();
					currencyName = cfg.getCurrencyName();
					huntDuration = cfg.getHuntDuration();
					individualHunts = cfg.isIndividualHunts();
					sendHuntEndMessage = cfg.isSendHuntEndMessage();
					sendHuntBeginMessage = cfg.isSendHuntBeginMessage();
					timerCooldowns = cfg.isTimerCooldowns();
					bufferDuration = cfg.getBufferDuration();
					matchProperties = cfg.getMatchProperties();
					customPrices = cfg.getCustomPrices();
					blacklist = cfg.getBlacklist();
					rarity = cfg.getRarity();
					rewards = cfg.getRewards();
					economy = cfg.getEconomy();

					if (!versioned.getVersion().equals(Hunt.CONFIG_VERSION)) {
						ConfigOld cfgOld = gson.fromJson(el, ConfigOld.class);
						economy = Economy.IMPACTOR;
						currencyName = cfgOld.getImpactorCurrencyName();
						write();
					}


				});

		// If the config couldn't be read, write a new one.
		if (!futureRead.join()) {
			Hunt.LOGGER.info("No config.json file found for Hunt. Attempting to generate one.");
			boolean futureWrite = write();

			// If the write failed, log fatal.
			if (!futureWrite) {
				Hunt.LOGGER.fatal("Could not write config for Hunt.");
			}
			return;
		}
		Hunt.LOGGER.info("Hunt config file read successfully.");
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

	public String getCurrencyName() {
		return currencyName;
	}

	public Economy getEconomy() {
		return economy;
	}
}
