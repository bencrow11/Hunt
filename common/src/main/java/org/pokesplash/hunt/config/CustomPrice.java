package org.pokesplash.hunt.config;

import java.util.ArrayList;

/**
 * Custom prices
 */
public class CustomPrice {
	private String species; // Species of the pokemon
	private String form; // Form of the pokemon
	private RewardConfig reward; // price of hunt.

	public CustomPrice() {
		species = "magikarp";
		form = "";
		reward = new RewardConfig(500, "give {player} minecraft:diamond 1");
	}

	public CustomPrice(String species, String form, double price) {
		this.species = species;
		this.form = form;
		reward = new RewardConfig(price, null);
	}

	/**
	 * Getters
	 */

	public String getSpecies() {
		return species;
	}

	public String getForm() {
		return form;
	}

	public RewardConfig getReward() {
		return reward;
	}
}
