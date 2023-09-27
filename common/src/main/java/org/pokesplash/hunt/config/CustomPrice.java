package org.pokesplash.hunt.config;

/**
 * Custom prices
 */
public class CustomPrice {
	private String species; // Species of the pokemon
	private String form; // Form of the pokemon
	private double price; // price of hunt.

	public CustomPrice() {
		species = "magikarp";
		form = "";
		price = 500;
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

	public double getPrice() {
		return price;
	}
}
