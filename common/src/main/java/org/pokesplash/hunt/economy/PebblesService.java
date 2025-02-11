package org.pokesplash.hunt.economy;

import org.pokesplash.hunt.api.economy.HuntEconomy;
import tech.sethi.pebbleseconomy.Economy;
import tech.sethi.pebbleseconomy.PebblesEconomyInitializer;

import java.util.UUID;

public class PebblesService implements HuntEconomy {

    Economy service = PebblesEconomyInitializer.INSTANCE.getEconomy();

    @Override
    public boolean add(UUID player, double amount) {

        service.deposit(player, amount);

        return true;
    }
}
