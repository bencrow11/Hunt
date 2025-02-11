package org.pokesplash.hunt.api.economy;

import java.util.UUID;

public interface HuntEconomy {
    boolean add(UUID player, double amount);
}
