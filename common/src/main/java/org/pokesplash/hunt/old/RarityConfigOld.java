package org.pokesplash.hunt.old;

public class RarityConfigOld {
	private float commonPokemonRarity; // What rarity is classed as common.
	private float uncommonPokemonRarity; // What rarity is classed as uncommon.
	private float rarePokemonRarity; // What rarity is classed as rare.

	public RarityConfigOld() {
		commonPokemonRarity = 7;
		uncommonPokemonRarity = 2.5F;
		rarePokemonRarity = 0.3F;
	}

	public float getCommonPokemonRarity() {
		return commonPokemonRarity;
	}

	public float getUncommonPokemonRarity() {
		return uncommonPokemonRarity;
	}

	public float getRarePokemonRarity() {
		return rarePokemonRarity;
	}
}
