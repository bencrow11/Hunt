package org.pokesplash.hunt.hunts;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.spawning.SpawnBucket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.config.CustomPrice;
import org.pokesplash.hunt.config.RarityConfig;
import org.pokesplash.hunt.config.RewardConfig;
import org.pokesplash.hunt.util.Utils;

import java.text.DecimalFormat;
import java.util.*;

public class SingleHunt {

	private final UUID id; // Unique ID to reference hunt by.
	private final UUID owner; // Player who owns the hunt
	private double price; // hunt prize amount.
	private ArrayList<String> commands; // Commands for completing the hunt.
	private Pokemon pokemon; // Pokemon being hunted.
	private final long endtime; // The end date for the hunt.
	private Bucket bucket; // The spawn bucket of the Pokemon.

	public SingleHunt(UUID owner) {
		// Creates unique ID and generates random pokemon.
		id = UUID.randomUUID();
		this.owner = owner;

		pokemon = new Pokemon();

		Bucket rarityRequired = Hunt.config.getRarity().getRandomRarity();
		bucket = Hunt.spawnRates.getBucket(pokemon);
		boolean isLegendary = pokemon.isLegendary();

		// Will keep regenerating a Pokemon until one found in the rarity table that isn't a legendary is found.
		while (bucket == null || isLegendary || !bucket.equals(rarityRequired)) {
			pokemon = new Pokemon();
			bucket = Hunt.spawnRates.getBucket(pokemon);
			isLegendary = pokemon.isLegendary();
		}

		RewardConfig reward = Hunt.config.getRewards().get(bucket);
		price = reward.getPrice();
		commands = reward.getCommands();

		// Checks for a custom price.
		List<CustomPrice> customPrices = Hunt.config.getCustomPrices();
		for (CustomPrice item : customPrices) {
			// If species match
			if (item.getSpecies().trim().equalsIgnoreCase(pokemon.getSpecies().getName().trim())) {
				// If no form is given or the form matches, use price.
				if (item.getForm().trim().equalsIgnoreCase("") ||
						item.getForm().trim().equalsIgnoreCase(pokemon.getForm().getName().trim())) {
					price = item.getReward().getPrice();
					commands = item.getReward().getCommands();
					break;
				}
			}
		}

		pokemon.rollAbility();
		pokemon.checkGender();

		// Creates the timer to replace the hunt once it is over.
		int duration = Hunt.config.getHuntDuration() * 60 * 1000;

		// Adds the endtime as the current time + the duration.
		endtime = new Date().getTime() + duration;
	}

	/**
	 * Checks that the hunt is still valid.
	 */
	public void check() {

		if (endtime > new Date().getTime()) {
			return;
		}

		if (Hunt.config.isIndividualHunts()) {
			Hunt.manager.getPlayerHunts(owner).replaceHunt(id, true);
		} else {
			Hunt.hunts.replaceHunt(id, true);
		}
	}

	/**
	 * Getters
	 */
	public UUID getOwner() {
		return owner;
	}

	public UUID getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

	public String getPriceAsString() {
		DecimalFormat df = new DecimalFormat("0.##");
		return df.format(price);
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public long getEndtime() {
		return endtime;
	}

	public ArrayList<String> getCommands() {
		return commands;
	}

	public Bucket getBucket() {
		return bucket;
	}

	/**
	 * Checks that a given pokemon matches the one in the listing.
	 * @param pokemon The pokemon to check.
	 * @return true if the pokemon matches the hunt, or false if it doesn't.
	 */

	public boolean matches(Pokemon pokemon) {
		// Checks the species and form match.
		if (!pokemon.getSpecies().getName().equalsIgnoreCase(this.pokemon.getSpecies().getName())) {
			return false;
		}
		if (!pokemon.getForm().getName().equalsIgnoreCase(this.getPokemon().getForm().getName())) {
			return false;
		}

		// Checks for ability, if enabled.
		if (Hunt.config.getMatchProperties().isAbility()) {
			if (!pokemon.getAbility().getName().equalsIgnoreCase(this.pokemon.getAbility().getName())) {
				return false;
			}
		}

		// Checks gender, if enabled.
		if (Hunt.config.getMatchProperties().isGender()) {
			if (!pokemon.getGender().name().equalsIgnoreCase(this.pokemon.getGender().name())) {
				return false;
			}
		}

		// Checks nature, if enabled.
		if (Hunt.config.getMatchProperties().isNature()) {
			if (!pokemon.getNature().getName().getPath().equalsIgnoreCase(this.pokemon.getNature().getName().getPath())) {
				return false;
			}
		}

		// Checks shiny, if enabled.
		if (Hunt.config.getMatchProperties().isShiny()) {
			return pokemon.getShiny() == this.pokemon.getShiny();
		}
		return true;
	}

}
