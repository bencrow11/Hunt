package org.pokesplash.hunt.util;

import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.transactions.EconomyTransaction;
import net.kyori.adventure.key.Key;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.api.event.economy.HuntEconomy;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Class to interact with the Impactor API
 */
public class ImpactorService implements HuntEconomy {

	// The impactor service
	private EconomyService service = EconomyService.instance();

	/**
	 * Method to get the account of a player.
	 * @param uuid The UUID of the player.
	 * @return The account of the player.
	 */

	private Account getAccount(UUID uuid) {
		if (!service.hasAccount(uuid).join()) {
			return service.account(uuid).join();
		}

		Currency currency = service.currencies().primary();

		try {
			if (!Hunt.config.isUseImpactorDefaultCurrency()) {
				currency = service.currencies().currency(Key.key(Hunt.config.getImpactorCurrencyName())).get();
			}
		} catch (Exception e) {
            Hunt.LOGGER.error("Could not find currency: {} from Impactor. Using default currency.", Hunt.config.getImpactorCurrencyName());
		}
		return service.account(currency, uuid).join();
	}

	/**
	 * Method to add to the balance of an account.
	 * @param account The account to add the balance to.
	 * @param amount The amount to add.
	 * @return true if the transaction was successful.
	 */
	@Override
	public boolean add(UUID account, double amount) {

		Account acc = getAccount(account);

		EconomyTransaction transaction = acc.deposit(new BigDecimal(amount));

		return transaction.successful();
	}
}