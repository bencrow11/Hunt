package org.pokesplash.hunt.hunts;

import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.hunt.Hunt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class HuntManager {
	private HashMap<UUID, CurrentHunts> playerHunts;

	public HuntManager() {
		playerHunts = new HashMap<>();
	}

	public CurrentHunts getPlayerHunts(UUID player) {
		return playerHunts.get(player);
	}

	public HashSet<UUID> getPlayers() {
		return new HashSet<>(playerHunts.keySet());
	}

	public void addPlayer(UUID player) {
		if (playerHunts.get(player) == null && Hunt.config.isIndividualHunts()) {
			CurrentHunts hunts = new CurrentHunts(player);
			hunts.init();
			playerHunts.put(player, hunts);
		}
	}

	public void init() {
		for (ServerPlayer player : Hunt.server.getPlayerList().getPlayers()) {
			addPlayer(player.getUUID());
		}
	}
}
