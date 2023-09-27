package org.pokesplash.hunt.hunts;

import com.cobblemon.mod.common.api.spawning.CobblemonSpawnPools;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Loads and stores the rarity for each Pokemon to be referenced.
 */
public class SpawnRates {

	// Stores all rarities to easily reference for a price.
	HashMap<String, Float> rarity;

	public SpawnRates() {
		rarity = new HashMap<>();
	}

	public void init() {
		ArrayList<SpawnDetail> spawnDetails = new ArrayList<>(CobblemonSpawnPools.WORLD_SPAWN_POOL.getDetails());
		// Checks for highest value for each key and adds the key with the highest weight.
		for (SpawnDetail detail : spawnDetails ) {
			if (rarity.containsKey(detail.getName().getString())) {
				if (rarity.get(detail.getName().getString()) >= detail.getWeight()) {
					continue;
				}
			}
			rarity.put(detail.getName().getString(), detail.getWeight());
		}
	}

	/**
	 * Gets the rarity hashmap.
	 * @return rarity of given pokemon as a float.
	 */
	public float getRarity(Pokemon pokemon) {
		return rarity.get(pokemon.getDisplayName().getString()) == null ? -1 :
				rarity.get(pokemon.getDisplayName().getString());
	}
}
