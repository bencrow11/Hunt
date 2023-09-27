package org.pokesplash.hunt.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import kotlin.Unit;

public abstract class EventHandler {
	public static void registerEvents() {
		CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, e -> {
			//TODO logic here
			return Unit.INSTANCE;
		});
	}

}
