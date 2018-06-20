package me.Phitros.MavEAW;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import Territory.ChunkHandler;
import me.lucko.luckperms.api.LuckPermsApi;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	public LuckPermsApi luckApi = null;
	public WorldEditPlugin worldEditApi = null;
	public FileConfiguration config = this.getConfig();
	public FileConfiguration greenConfig = null;
	public FileConfiguration redConfig = null;
	public FileConfiguration blueConfig = null;
	public FileConfiguration yellowConfig = null;
	public File factionGreen = new File(getDataFolder(), "GreensChuncks.yml");
	public File factionRed = new File(getDataFolder(), "RedsChuncks.yml");
	public File factionBlue = new File(getDataFolder(), "BluesChuncks.yml");
	public File factionYellow = new File(getDataFolder(), "YellowsChuncks.yml");
	public Map<String, ArrayList<Chunk>> factionTerritory = new HashMap<>();

	// Serves as a file loader, and a file creator.

	public FileConfiguration loadFile(File getFile) {
		if (!(getFile.exists())) {
			try {
				Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Successfully created file : " + getFile.getName());
				getFile.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(getFile);
		return config;
	}

	public void csaveConfig(FileConfiguration config, File saveFile) {
		try {
			config.save(saveFile);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void creloadConfig(FileConfiguration config, File getFile) {
		config = YamlConfiguration.loadConfiguration(getFile);
	}

	@Override
	public void onEnable() {
		RegisteredServiceProvider<LuckPermsApi> luckyProvider = Bukkit.getServicesManager().getRegistration(LuckPermsApi.class);

		if (luckyProvider == null) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Luckperms api isn't loaded, disabling the plugin =(");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}

		Plugin worldEdit = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

		if (!(worldEdit instanceof WorldEditPlugin)) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Couldn't find the World edit plugin, disabling the plugin =/");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}

		if (luckyProvider != null && worldEdit instanceof WorldEditPlugin) {
			luckApi = luckyProvider.getProvider();
			worldEditApi = (WorldEditPlugin) worldEdit;

			config.options().copyDefaults();

			final FileConfiguration config = this.getConfig();

			config.options().copyDefaults(true);
			if (!config.contains("Factions.Green.GroupName"))
				config.set("Factions.Green.GroupName", "Green");
			if (!config.contains("Factions.Red.GroupName"))
				config.set("Factions.Red.GroupName", "Red");
			if (!config.contains("Factions.Blue.GroupName"))
				config.set("Factions.Blue.GroupName", "Blue");
			if (!config.contains("Factions.Yellow.GroupName"))
				config.set("Factions.Yellow.GroupName", "Yellow");
			saveConfig();
			greenConfig = loadFile(factionGreen);
			redConfig = loadFile(factionRed);
			blueConfig = loadFile(factionBlue);
			yellowConfig = loadFile(factionYellow);

			Commands commandClass = new Commands();
			getCommand("eaw").setExecutor(commandClass);
			this.getServer().getPluginManager().registerEvents(new ChunkHandler(this, commandClass), this);

		}

	}
}
