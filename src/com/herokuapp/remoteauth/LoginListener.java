package com.herokuapp.remoteauth;

import java.net.*;
import java.io.*;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;


public class LoginListener implements Listener  {
	
	private Plugin p;
	
	private static String BaseURL = "remoteauth.herokuapp.com";
	private static int BasePort = 80;
	
//	private static String BaseURL = "127.0.0.1";
//	private static int BasePort = 8000;
	
	public LoginListener(RemoteAuth plugin) {
		p = plugin;
		
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

	@EventHandler
	public void normalLogin(PlayerLoginEvent event) {		
		try {			
			URL RanksURL = new URL("http", BaseURL, BasePort, "/user/" + event.getPlayer().getName());
			
			URLConnection getConn = RanksURL.openConnection();
	        getConn.connect();
	        BufferedReader dis = new BufferedReader(new InputStreamReader(getConn.getInputStream()));
	        
	        String line = dis.readLine().trim();
	        p.getLogger().info("'" + line + "'");
	        
	        if (line.equals("false")) {
	        	Bukkit.getServer().dispatchCommand(
	    			Bukkit.getServer().getConsoleSender(), 
	    			"manudel " + event.getPlayer().getName()
	        	);
	        }
	        else {
	        	Bukkit.getServer().dispatchCommand(
	    			Bukkit.getServer().getConsoleSender(), 
	    			"manuadd " + event.getPlayer().getName() + " " + line
	        	);
	        }
		}
		catch (Exception e) {
			p.getLogger().severe(e.toString());
		}
	}
}
