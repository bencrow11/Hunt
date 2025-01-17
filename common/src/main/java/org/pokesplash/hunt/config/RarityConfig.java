package org.pokesplash.hunt.config;

import org.pokesplash.hunt.hunts.Bucket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RarityConfig {
	private int common; // What rarity is classed as common.
	private int uncommon; // What rarity is classed as uncommon.
	private int rare; // What rarity is classed as rare.
	private int ultraRare;

	public RarityConfig() {
		common = 60;
		uncommon = 30;
		rare = 9;
		ultraRare = 1;
	}

	public int getCommon() {
		return common;
	}

	public int getUncommon() {
		return uncommon;
	}

	public int getRare() {
		return rare;
	}

	public int getUltraRare() {
		return ultraRare;
	}

	public Bucket getRandomRarity() {
		List<Bucket> list = new ArrayList<Bucket>();
		list.addAll(Collections.nCopies(getCommon(), Bucket.COMMON));
		list.addAll(Collections.nCopies(getUncommon(), Bucket.UNCOMMON));
		list.addAll(Collections.nCopies(getRare(), Bucket.RARE));
		list.addAll(Collections.nCopies(getUltraRare(), Bucket.ULTRA_RARE));

		Random rand = new Random();
		return list.get(rand.nextInt(list.size()));
	}
}
