package org.pokesplash.hunt.economy;

import org.blanketeconomy.api.BlanketEconomy;
import org.blanketeconomy.api.EconomyAPI;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.api.economy.HuntEconomy;

import java.math.BigDecimal;
import java.util.UUID;

public class BlanketService implements HuntEconomy {

    EconomyAPI service = BlanketEconomy.INSTANCE.getAPI();

    @Override
    public boolean add(UUID player, double amount) {

        service.addBalance(player, new BigDecimal(amount), Hunt.config.getCurrencyName());

        return true;
    }
}
