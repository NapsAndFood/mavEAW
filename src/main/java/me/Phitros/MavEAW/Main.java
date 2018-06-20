package me.Phitros.MavEAW;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.lucko.luckperms.api.LuckPermsApi;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
   public LuckPermsApi luckApi = null;
   public File factionGreen = new File(getDataFolder(), "GreensChuncks.yml");
   public File factionRed = new File(getDataFolder(), "RedsChuncks.yml");
   public File factionBlue = new File(getDataFolder(), "BluesChuncks.yml");
   public File factionYellow = new File(getDataFolder(), "YellowsChuncks.yml");
   private  FileConfiguration config = this.getConfig();
   // Serves as a file loader, and a file creator.
   
 	public FileConfiguration loadFile(File getFile) {
 		if (!(getFile.exists())) {
 			try {
 				Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Successfull created file : " + getFile.getName());
 				getFile.createNewFile();
 			} catch (IOException e) {
 				
 				e.printStackTrace();
 			}
 		} 
 		FileConfiguration config = YamlConfiguration.loadConfiguration(getFile);
 		
 		return config;
 		
 	}
   public void saveConfigGUI(FileConfiguration config, File saveFile) {
 	  try {
 		config.save(saveFile);
 	} catch (IOException e) {
 	
 		e.printStackTrace();
 	}
   }
   public void reloadConfigGUI(FileConfiguration config, File getFile) {
 	config = YamlConfiguration.loadConfiguration(getFile);
 	
   }
	public void onEnable() {
		RegisteredServiceProvider<LuckPermsApi> luckyProvider = Bukkit.getServicesManager().getRegistration(LuckPermsApi.class);
		if (luckyProvider != null) {
		    luckApi  = luckyProvider.getProvider();	    
		    config.options().copyDefaults();
			final FileConfiguration config = this.getConfig();
		    config.options().copyDefaults(true);
		    saveConfig();
		    loadFile(factionGreen);
		    loadFile(factionRed);
		    loadFile(factionBlue);
		    loadFile(factionYellow);
		} else if (luckyProvider == null) {
			  Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Luckperms api isn't loaded, disabling the plugin =(");
		       Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
	}
}
