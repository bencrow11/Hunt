package org.pokesplash.hunt.hunts;

public class ReplacedHunt {
	private SingleHunt oldHunt;
	private SingleHunt newHunt;

	public ReplacedHunt(SingleHunt oldHunt, SingleHunt newHunt) {
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
