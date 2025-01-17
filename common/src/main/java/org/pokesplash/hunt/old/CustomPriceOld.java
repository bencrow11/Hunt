package org.pokesplash.hunt.old;

/**
 * Custom prices
 */
public class CustomPriceOld {
	private String species; // Species of the pokemon
	private String form; // Form of the pokemon
	private double price; // price of hunt.

	public CustomPriceOld() {
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
