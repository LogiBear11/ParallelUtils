package parallelmc.parallelutils.commands.custommobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import parallelmc.parallelutils.Constants;
import parallelmc.parallelutils.Parallelutils;
import parallelmc.parallelutils.commands.Commands;
import parallelmc.parallelutils.commands.ParallelCommand;
import parallelmc.parallelutils.commands.permissions.ParallelOrPermission;
import parallelmc.parallelutils.commands.permissions.ParallelPermission;
import parallelmc.parallelutils.custommobs.registry.SpawnerRegistry;
import parallelmc.parallelutils.custommobs.spawners.SpawnTask;

import java.util.logging.Level;

public class ParallelCreateSpawnerCommand extends ParallelCommand {
    public static final String[] SUMMON_MOBS = new String[]{"wisp"};

    public ParallelCreateSpawnerCommand() {
        super("createspawner", new ParallelOrPermission(new ParallelPermission[]
                {new ParallelPermission("parallelutils.spawn"), new ParallelPermission("parallelutils.spawn.spawner"),
                new ParallelPermission("parallelutils.spawn.spawner.create")}));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length <= 1) {
                String options = "Options:\n";
                for (String s : SUMMON_MOBS) {
                    options += s + "\n";
                }
                sender.sendMessage(options);
                return true;
            }

            PluginManager manager = Bukkit.getPluginManager();
            JavaPlugin plugin = (JavaPlugin) manager.getPlugin(Constants.pluginName);

            if (plugin == null) {
                Parallelutils.log(Level.SEVERE, "Unable to execute command 'spawnerCreate'. Plugin " + Constants.pluginName + " does not exist!");
                return false;
            }

            if(args.length < 5){
                if(validMobType(args[1])){
                    sender.sendMessage("Please enter a valid mob type.");
                    return true;
                }
                else {
                    sender.sendMessage("Please enter a full set of coordinates.");
                    return true;
                }
            }
            Location spawnerLocation = null;
            try {
                spawnerLocation = Commands.convertLocation(sender, args[2], args[3], args[4]);
            }
            catch(NumberFormatException e){
                sender.sendMessage("Incorrect coordinate formatting!");
                return true;
            }

            switch (args[1]) {
                case "wisp":
                    SpawnerRegistry.getInstance().registerSpawner("wisp", spawnerLocation, true);
                    BukkitTask task = new SpawnTask(plugin, "wisp", spawnerLocation, 0)
                            .runTaskTimer(plugin, 0, SpawnerRegistry.getInstance().
                                    getSpawnerOptions("wisp").cooldown);
                    SpawnerRegistry.getInstance().addSpawnTaskID(spawnerLocation, task.getTaskId());
                    break;
            }
        } else {
            sender.sendMessage("This command can only be run by a player");
            return false;
        }
        return true;
    }

    private boolean validMobType(String type){
        for(String s : SUMMON_MOBS){
            if(s.equalsIgnoreCase(type)){
                return true;
            }
        }
        return false;
    }
}

