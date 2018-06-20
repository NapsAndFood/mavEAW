package Territory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import me.Phitros.MavEAW.Commands;
import me.Phitros.MavEAW.Main;
import net.md_5.bungee.api.ChatColor;;

public class ChunkHandler implements Listener {

	private Main mainInstance = null;
	private Commands commandInstance = null;
	private Map<Player, List<Location>> markedLocations = new HashMap<>();

	public ChunkHandler(Main getMainInstance, Commands getCommandInstance) {
		this.mainInstance = getMainInstance;
		this.commandInstance = getCommandInstance;
	}

	@EventHandler
	private void markRegion(PlayerInteractEvent ev) {
		if (commandInstance.createSelection.containsKey(ev.getPlayer())) {
			Player player = ev.getPlayer();

			List<Location> markedPositions = markedLocations.get(player);
			
			if (markedPositions == null)
				markedPositions = new ArrayList<Location>();
			
			if (markedPositions.size() < 2) {
				if (ev.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					player.sendMessage(ChatColor.AQUA + "EAW >> 1st Positioned marked!");
					if (!(markedPositions.contains(ev.getClickedBlock().getLocation())))
						markedPositions.add(ev.getClickedBlock().getLocation());
						markedLocations.put(player, markedPositions);
				} else if (ev.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					player.sendMessage(ChatColor.AQUA + "EAW >> 2nd Positioned marked!");
					if (!(markedPositions.contains(ev.getClickedBlock().getLocation())))
						markedPositions.add(ev.getClickedBlock().getLocation());
						markedLocations.put(player, markedPositions);
				}
			} else if (markedPositions.size() == 2) {
				List<String> chunks = new ArrayList<>();
				int startAtX, endAtX, startAtZ, endAtZ;
				startAtX = 0;
				startAtZ = 0;
				endAtX = 0;
				endAtZ = 0;
				if (markedPositions.get(0).getBlockX() < markedPositions.get(1).getBlockX()) {
					startAtX = markedPositions.get(0).getBlockX();
					endAtX = markedPositions.get(1).getBlockX();
				} else if (markedPositions.get(0).getBlockX() > markedPositions.get(1).getBlockX()) {
					startAtX = markedPositions.get(1).getBlockX();
					endAtX = markedPositions.get(0).getBlockX();
				}
				if (markedPositions.get(0).getBlockZ() < markedPositions.get(1).getBlockZ()) {
					startAtZ = markedPositions.get(0).getBlockZ();
					endAtZ = markedPositions.get(1).getBlockZ();
				} else if (markedPositions.get(0).getBlockZ() > markedPositions.get(1).getBlockZ()) {
					startAtZ = markedPositions.get(1).getBlockZ();
					endAtZ = markedPositions.get(0).getBlockZ();
				}
				for (int x = startAtX; x < endAtX; x++) {
					for (int z = startAtZ; z < endAtZ; z++) {
						String chunkCords = x + " " + z;
						chunks.add(chunkCords);
					}
				}
				player.sendMessage(ChatColor.AQUA + "EAW >> " + ChatColor.YELLOW + "Territory has been updated!");
				// Determine which config to save the input to.
				switch (commandInstance.createSelection.get(player)) {
				case "blue":
					mainInstance.blueConfig.set("chunks.Worlds." + ev.getClickedBlock().getWorld().getName(), chunks);
					mainInstance.csaveConfig(mainInstance.blueConfig, mainInstance.factionBlue);
					break;
				case "green":
					mainInstance.greenConfig.set("chunks.Worlds." + ev.getClickedBlock().getWorld().getName(), chunks);
					mainInstance.csaveConfig(mainInstance.greenConfig, mainInstance.factionGreen);
					break;
				case "yellow":
					mainInstance.yellowConfig.set("chunks.Worlds." + ev.getClickedBlock().getWorld().getName(), chunks);
					mainInstance.csaveConfig(mainInstance.yellowConfig, mainInstance.factionYellow);
					break;
				case "red":
					mainInstance.redConfig.set("chunks.Worlds." + ev.getClickedBlock().getWorld().getName(), chunks);
					mainInstance.csaveConfig(mainInstance.redConfig, mainInstance.factionRed);
					break;
				}

				markedLocations.remove(player);
			}
		}

	}

}
