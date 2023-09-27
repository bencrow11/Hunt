package org.pokesplash.hunt.command.basecommand;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.item.PokemonItem;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.command.subcommand.ReloadCommand;
import org.pokesplash.hunt.hunts.SingleHunt;
import org.pokesplash.hunt.util.BaseCommand;
import org.pokesplash.hunt.util.Utils;

import java.util.*;

/**
 * Creates the mods base command.
 */
public class HuntCommand extends BaseCommand {

	/**
	 * Constructor to create a new comand.
	 */
	public HuntCommand() {
		// Super needs the command, a list of aliases, permission and array of subcommands.
		super("hunt", Arrays.asList("hunts"),
				Hunt.permissions.getPermission("HuntBase"), Arrays.asList(new ReloadCommand()));
	}

	// Runs when the base command is run with no subcommands.
	@Override
	public int run(CommandContext<CommandSourceStack> context) {

		if (!context.getSource().isPlayer()) {
			context.getSource().sendSystemMessage(Component.literal("A player must run this command!"));
			return 1;
		}

		ServerPlayer sender = context.getSource().getPlayer();

		/**
		 *  Creates the UI
		 */


		List<Button> hunts = new ArrayList<>();
		for (SingleHunt hunt : Hunt.hunts.getHunts().values()) {
			Collection<String> lore = new ArrayList<>();

			boolean isShiny = false;

			if (Hunt.config.getMatchProperties().isAbility()) {
				lore.add("§2Ability: §a" + Utils.capitaliseFirst(hunt.getPokemon().getAbility().getName()));
			}

			if (Hunt.config.getMatchProperties().isGender()) {
				lore.add("§3Gender: §b" + Utils.capitaliseFirst(hunt.getPokemon().getGender().toString()));
			}

			if (Hunt.config.getMatchProperties().isNature()) {
				lore.add("§5Nature: §d" + Utils.capitaliseFirst(hunt.getPokemon().getNature().getName().getPath()));
			}

			lore.add("§6Reward: §e" + hunt.getPrice());

			if (Hunt.config.getMatchProperties().isShiny()) {
				isShiny = true;
			}

			lore.add("§9Time Remaining: §b" + Utils.parseLongDate(hunt.getEndtime() - new Date().getTime()));

			String title = hunt.getPokemon().getSpecies().getName() +
					(hunt.getPokemon().getForm().getName().equalsIgnoreCase("normal") ? "" :
							" - " + hunt.getPokemon().getForm().getName());

			GooeyButton button = GooeyButton.builder()
					.display(PokemonItem.from(hunt.getPokemon(), 1))
					.title(isShiny ? "§e" + title : "§b" + title)
					.lore(lore)
					.build();

			hunts.add(button);
		}

		Button filler = GooeyButton.builder()
				.display(Utils.parseItemId(Hunt.language.getFillerMaterial()))
				.hideFlags(FlagType.All)
				.lore(new ArrayList<>())
				.title("")
				.build();

		PlaceholderButton placeholder = new PlaceholderButton();

		int rows = (int) Math.ceil((double) Hunt.config.getHuntAmount() / 7) + 2;

		ChestTemplate template = ChestTemplate.builder(rows)
				.rectangle(1, 1, rows - 2, 7, placeholder)
				.fill(filler)
				.build();

		LinkedPage page = PaginationHelper.createPagesFromPlaceholders(template, hunts, null);
		page.setTitle(Hunt.language.getTitle());

		UIManager.openUIForcefully(sender, page);

		return 1;
	}
}
