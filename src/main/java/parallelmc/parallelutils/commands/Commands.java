package parallelmc.parallelutils.commands;

import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_16_R3.command.ServerCommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import parallelmc.parallelutils.Parallelutils;
import parallelmc.parallelutils.commands.custommobs.ParallelWorldCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {

	private final Parallelutils plugin;

	public Commands(Parallelutils plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (command.getName().equalsIgnoreCase("parallelutils") || command.getName().equalsIgnoreCase("pu")) {
			if (!hasPermission(sender, "parallelutils.basic")) {
				sender.sendMessage("You do not have permission");
				return true;
			}
			if (args.length == 0) {
				//Give version information
			} else {
				switch (args[0]) {
					case "test":
						new ParallelTestCommand().execute(sender, command, args);
						break;
					case "world":
						new ParallelWorldCommand().execute(sender, command, args);
						break;
					case "reset":
						/*CraftZombie mob = (CraftZombie)Bukkit.getEntity(UUID.fromString("29cff125-d7b0-4a20-a03f-97c0b16149b2"));
						mob.setShouldBurnInDay(false);
						if (mob != null) {
							EntityWisp.setupNBT(plugin, mob);

							EntityZombie wisp = (mob).getHandle();
							NMSWisp.initPathfinder(wisp);
						} else {
							System.out.println("Mob is null!");
						}*/
						break;
					default:
						sender.sendMessage("PU: Command not found");
				}
			}
		}
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		ArrayList<String> list = new ArrayList<>();

		if (!hasPermission(sender, "parallelutils.basic")) {
			return list;
		}

		if (command.getName().equalsIgnoreCase("parallelutils") || command.getName().equalsIgnoreCase("pu") && args.length == 1) {
			// List every sub-command
			list.add("test");
			list.add("world");
		}

		if (args.length == 2) {
			if (args[0].equals("world")) {
				list.addAll(Arrays.asList(ParallelWorldCommand.WORLD_MOBS));
			}
		}

		return list;
	}

	public static boolean hasPermission(CommandSender sender, String permission) {
		return sender instanceof ServerCommandSender || sender.isOp() || sender.hasPermission(permission);
	}
}
