package me.Phitros.MavEAW;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {
	// Check which players are allowed to set the factions territory, including
	// which faction.
	public Map<Player, String> createSelection = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("eaw")) {
			if (sender instanceof Player) {
				if (args.length < 1) {
					if (sender instanceof Player) {

						Player player = (Player) sender;
						if (player.hasPermission("eaw.commands.list"))

							player.sendMessage(ChatColor.AQUA + "EAW >> Avaible commands: \n " + ChatColor.YELLOW
									+ "/eaw setteritory <faction name> or /eaw setteritory off");
						return true;
					}

				} else if (args.length > 1) {
					switch (args[0].toLowerCase()) {

					case "setterritory":

						Player player = (Player) sender;
						if (player.hasPermission("eaw.adminmode.createselection")) {
							if (args.length == 2) {
								if (args[1].equalsIgnoreCase("blue") || args[1].equalsIgnoreCase("green")|| args[1].equalsIgnoreCase("yellow") || args[1].equalsIgnoreCase("red")) {
									createSelection.put(player, args[1].toLowerCase());
									player.sendMessage(ChatColor.AQUA + "EAW >> " + ChatColor.YELLOW
											+ "Selection mode has been turned on for faction/Empire:" + args[1]);

								} else if (args[1].equalsIgnoreCase("off") && createSelection.containsKey(player)) {
									createSelection.remove(player);
									player.sendMessage(ChatColor.AQUA + "EAW >> " + ChatColor.YELLOW
											+ "Selection mode has been turned off");
								} else {
									player.sendMessage(ChatColor.AQUA + "EAW >> " + ChatColor.YELLOW
											+ "Valid form: \n /eaw setteritory <faction name> or \n /eaw setteritory off");
								}

							} else if (args.length != 2)
								player.sendMessage(ChatColor.AQUA + "EAW >> " + ChatColor.YELLOW
										+ "Valid form: \n /eaw setteritory <faction name> or /eaw setteritory off");

						} else {
							player.sendMessage(ChatColor.RED + "Lacking permission node: eaw.admin.createselection");
						}

						break;

					}

				}
			} else {
				// Console Commands
				sender.sendMessage(ChatColor.AQUA + "EAW >> " + ChatColor.RED + "The command: /eaw " + ChatColor.YELLOW
						+ args[0] + ChatColor.RED + " is an in-game command only!");
			}
		}
		return true;
	}

}
