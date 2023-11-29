package org.pokesplash.hunt.config;

import java.util.ArrayList;

/**
 * Data for a single rarity value.
 */
public class RewardConfig {
	private double price;
	private ArrayList<String> commands;

	public RewardConfig(double price, String command) {
		this.price = price;
		this.commands = new ArrayList<>();
		this.commands.add(command);
	}

	public double getPrice() {
		return price;
	}

	public ArrayList<String> getCommands() {
		return commands;
	}
}
