package org.pokesplash.hunt.config;

/**
 * Properties to check a capture for.
 */
public class Properties {
	private boolean ability; // Should the ability be checked
	private boolean gender; // Should the gender be checked
	private boolean nature; // Should the nature be checked
	private boolean shiny; // Should the shiny state be checked

	public Properties() {
		ability = true;
		gender = true;
		nature = true;
		shiny = false;
	}

	/**
	 * Getters
	 */

	public boolean isAbility() {
		return ability;
	}

	public boolean isGender() {
		return gender;
	}

	public boolean isNature() {
		return nature;
	}

	public boolean isShiny() {
		return shiny;
	}
}
