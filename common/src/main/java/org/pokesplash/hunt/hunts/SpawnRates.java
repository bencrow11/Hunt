package org.pokesplash.hunt.hunts;

import com.cobblemon.mod.common.api.spawning.BestSpawner;
import com.cobblemon.mod.common.api.spawning.CobblemonSpawnPools;
import com.cobblemon.mod.common.api.spawning.SpawnBucket;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Loads and stores the rarity for each Pokemon to be referenced.
 */
public class SpawnRates {

	// Stores all rarities to easily reference for a price.
	private final HashMap<String, Bucket> rarity;
	private final HashMap<Bucket, Float> buckets;

	public SpawnRates() {
		rarity = new HashMap<>();
		buckets = new HashMap<>();
	}

	public void init() {

		BestSpawner.INSTANCE.getConfig().getBuckets().forEach(b -> {

			Bucket bucket = getBucket(b);

			if (bucket != null) {
				buckets.put(bucket, b.getWeight());
			}
		});

		ArrayList<SpawnDetail> spawnDetails = new ArrayList<>(CobblemonSpawnPools.WORLD_SPAWN_POOL.getDetails());

		spawnDetails.forEach(spawnDetail -> {
			if (!isRarerBucket(spawnDetail)) {
				rarity.put(spawnDetail.getName().getString(), getBucket(spawnDetail.getBucket()));
			}
		});
	}

	private boolean isRarerBucket(SpawnDetail spawnDetail) {

		if (!rarity.containsKey(spawnDetail.getName().getString())) {
			return false;
		}

		Float oldWeight = buckets.get(rarity.get(spawnDetail.getName().getString()));
		Float newWeight = buckets.get(getBucket(spawnDetail.getBucket()));

		if (oldWeight == null || newWeight == null) {
			return false;
		}

		return newWeight < oldWeight;
	}

	private Bucket getBucket(SpawnBucket spawnBucket) {
        return switch (spawnBucket.getName()) {
            case "common" -> Bucket.COMMON;
            case "uncommon" -> Bucket.UNCOMMON;
            case "rare" -> Bucket.RARE;
            case "ultra-rare" -> Bucket.ULTRA_RARE;
            default -> null;
        };
	}

	public Bucket getBucket(Pokemon pokemon) {
		if (!rarity.containsKey(pokemon.getSpecies().getName())) {
			return null;
		}

		return rarity.get(pokemon.getSpecies().getName());
	}
}
