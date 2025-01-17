package org.pokesplash.hunt.config;

import org.pokesplash.hunt.hunts.Bucket;

/**
 * Stores all rewards.
 */
public class RewardsConfig {
	private RewardConfig common; // Common rewards.
	private RewardConfig uncommon; // Uncommon rewards.
	private RewardConfig rare; // Rare rewards.
	private RewardConfig ultraRare; // UltraRare rewards.

	public RewardsConfig() {
		common = new RewardConfig(100, "give {player} minecraft:diamond 1");
		uncommon = new RewardConfig(500, "give {player} minecraft:diamond 2");
		rare = new RewardConfig(700, "give {player} minecraft:diamond 3");
		ultraRare = new RewardConfig(1000, "give {player} minecraft:diamond 4");
	}

	public RewardConfig getCommon() {
		return common;
	}

	public RewardConfig getUncommon() {
		return uncommon;
	}

	public RewardConfig getRare() {
		return rare;
	}

	public RewardConfig getUltraRare() {
		return ultraRare;
	}

	public RewardConfig get(Bucket bucket) {
		return switch (bucket) {
			case COMMON -> getCommon();
			case UNCOMMON -> getUncommon();
			case RARE -> getRare();
			case ULTRA_RARE -> getUltraRare();
		};
	}
}
