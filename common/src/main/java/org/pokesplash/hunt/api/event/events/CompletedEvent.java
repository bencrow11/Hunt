package org.pokesplash.hunt.api.event.events;

import com.cobblemon.mod.common.pokemon.Pokemon;
import org.pokesplash.hunt.hunts.SingleHunt;

import java.util.UUID;

public class CompletedEvent {
	private SingleHunt hunt;
	private UUID player;

	public CompletedEvent(SingleHunt hunt, UUID player) {
		this.hunt = hunt;
		this.player = player;
	}

	public SingleHunt getHunt() {
		return hunt;
	}

	public UUID getPlayer() {
		return player;
	}
}
