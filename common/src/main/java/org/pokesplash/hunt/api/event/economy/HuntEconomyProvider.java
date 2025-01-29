package org.pokesplash.hunt.api.event.economy;

import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.enumeration.Economy;
import org.pokesplash.hunt.enumeration.Priority;
import org.pokesplash.hunt.util.ImpactorService;

import java.util.HashMap;

public abstract class HuntEconomyProvider {

    // List of providers and their priorities.
    private static HashMap<Priority, HuntEconomy> providers = new HashMap<>();

    // Used for economy mods to insert an economy bridge to use.
    public static void putEconomy(Priority priority, HuntEconomy economy) {
        providers.put(priority, economy);
    }

    // Gets an economy provider using a given priority.
    public static HuntEconomy getEconomy(Priority priority) {
        return providers.get(priority);
    }

    // Finds the economy provider with the highest priority.
    public static HuntEconomy getHighestEconomy() throws Exception {

        if (providers.get(Priority.HIGHEST) != null) {
            return providers.get(Priority.HIGHEST);
        }

        if (providers.get(Priority.HIGH) != null) {
            return providers.get(Priority.HIGH);
        }

        if (providers.get(Priority.MEDIUM) != null) {
            return providers.get(Priority.MEDIUM);
        }

        if (providers.get(Priority.LOW) != null) {
            return providers.get(Priority.LOW);
        }

        if (providers.get(Priority.LOWEST) != null) {
            return providers.get(Priority.LOWEST);
        }

        if (Hunt.config.getEconomy().equals(Economy.IMPACTOR)) {
            return new ImpactorService();
        }

        if (Hunt.config.getEconomy() == Economy.PEBBLES) {
            return null;
        }

        throw new Exception("No economy could be found.");
    }
}
