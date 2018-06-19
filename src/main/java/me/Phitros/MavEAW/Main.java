package me.Phitros.MavEAW;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.lucko.luckperms.api.LuckPermsApi;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
   public LuckPermsApi luckApi = null;
	public void onEnable() {
		RegisteredServiceProvider<LuckPermsApi> luckyProvider = Bukkit.getServicesManager().getRegistration(LuckPermsApi.class);
		if (luckyProvider != null) {
		    luckApi  = luckyProvider.getProvider();	    

		} else if (luckyProvider == null) {
			  Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Luckperms api isn't loaded, disabling the plugin =(");
		       Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
	}
}
