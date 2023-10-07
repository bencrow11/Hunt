package org.pokesplash.hunt.api.event;

import org.pokesplash.hunt.api.event.events.CompletedEvent;

public abstract class HuntEvents {
	public static Event<CompletedEvent> COMPLETED = new Event<>();
}
