package com.herokuapp.remoteauth;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PrintRanks extends BukkitRunnable {
	
	private final JavaPlugin plugin;
	
	public PrintRanks(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void run() {
		// Player player
		plugin.getServer().broadcastMessage("Welcome to Bukkit! Remember to read the documentation!");
	}
	
}