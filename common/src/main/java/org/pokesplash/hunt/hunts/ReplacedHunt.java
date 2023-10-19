package org.pokesplash.hunt.hunts;

import org.pokesplash.hunt.Hunt;

public class ReplacedHunt {
	private SingleHunt oldHunt;
	private SingleHunt newHunt;

	public ReplacedHunt(SingleHunt oldHunt, SingleHunt newHunt) {
		Hunt.tracker.subtractTracker(oldHunt.getPokemon().getDisplayName().getString());
		this.oldHunt = oldHunt;
		this.newHunt = newHunt;
	}

	public SingleHunt getOldHunt() {
		return oldHunt;
	}

	public SingleHunt getNewHunt() {
		return newHunt;
	}
}
