package org.pokesplash.hunt.hunts;

import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.hunt.Hunt;

import java.util.ArrayList;
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

	public ArrayList<SingleHunt> getHunts() {
		ArrayList<SingleHunt> hunts = new ArrayList<>();
		for (UUID uuid : playerHunts.keySet()) {
			hunts.addAll(playerHunts.get(uuid).getHunts().values());
		}
		return hunts;
	}

	public void addPlayer(UUID player) {
		Hunt.ASYNC_EXEC.submit(() -> {
			if (playerHunts.get(player) == null && Hunt.config.isIndividualHunts()) {
				CurrentHunts hunts = new CurrentHunts(player);
				hunts.init();
				playerHunts.put(player, hunts);
			}
		});
	}

	public void init() {
		for (ServerPlayer player : Hunt.server.getPlayerList().getPlayers()) {
			addPlayer(player.getUUID());
		}
	}
}
