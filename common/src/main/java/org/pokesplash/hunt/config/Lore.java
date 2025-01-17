package org.pokesplash.hunt.config;

import org.pokesplash.hunt.hunts.Bucket;

import java.util.ArrayList;
import java.util.List;

public class Lore {
    private List<String> common;
    private List<String> uncommon;
    private List<String> rare;
    private List<String> ultraRare;

    public Lore() {
        common = new ArrayList<>();
        common.add("§2§lCommon");

        uncommon = new ArrayList<>();
        uncommon.add("§6§lUncommon");

        rare = new ArrayList<>();
        rare.add("§9§lRare");

        ultraRare = new ArrayList<>();
        ultraRare.add("§5§lUltra Rare");
    }

    public List<String> getCommon() {
        return common;
    }

    public List<String> getUncommon() {
        return uncommon;
    }

    public List<String> getRare() {
        return rare;
    }

    public List<String> getUltraRare() {
        return ultraRare;
    }

    public List<String> get(Bucket bucket) {
        return switch (bucket) {
            case COMMON -> common;
            case UNCOMMON -> uncommon;
            case RARE -> rare;
            case ULTRA_RARE -> ultraRare;
        };
    }
}
