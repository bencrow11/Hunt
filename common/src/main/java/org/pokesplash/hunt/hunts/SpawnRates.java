package org.pokesplash.hunt.hunts;

import com.cobblemon.mod.common.api.spawning.BestSpawner;
import com.cobblemon.mod.common.api.spawning.CobblemonSpawnPools;
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

		// Cobblemon 1.8 replaced the SpawnBucket object with the bucket id as a
		// plain String, and split the single bucket list into one map per spawner
		// (world, fishing, habitats, PokeSnack). Hunts are priced off wild world
		// spawns, so that is the map to read.
		BestSpawner.INSTANCE.getConfig().getWorldBuckets().forEach((name, weight) -> {

			Bucket bucket = getBucket(name);

			if (bucket != null) {
				buckets.put(bucket, weight);
			}
		});

		ArrayList<SpawnDetail> spawnDetails = new ArrayList<>(CobblemonSpawnPools.WORLD_SPAWN_POOL.getDetails());

		spawnDetails.forEach(spawnDetail -> {

			// 1.8 added a "boss" bucket for the alpha herds, which has no rarity
			// tier here. Skipping it keeps the four tiers behaving exactly as
			// before; without this an alpha entry would overwrite the species'
			// real rarity with null and quietly drop it from every hunt.
			if (getBucket(spawnDetail.getBucket()) == null) {
				return;
			}

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

	private Bucket getBucket(String spawnBucket) {
        return switch (spawnBucket) {
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
