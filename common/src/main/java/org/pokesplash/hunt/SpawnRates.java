package org.pokesplash.hunt;

import com.cobblemon.mod.common.api.spawning.CobblemonSpawnPools;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;

import java.util.HashMap;
import java.util.List;

public class SpawnRates {

	HashMap<String, Float> rarity;

	public SpawnRates() {
		rarity = new HashMap<>();
	}

	public void init() {
		List<SpawnDetail> spawnDetails = CobblemonSpawnPools.WORLD_SPAWN_POOL.getDetails();
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

}
