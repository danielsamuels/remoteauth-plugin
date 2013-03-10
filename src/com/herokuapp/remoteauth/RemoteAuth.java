package com.herokuapp.remoteauth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.craftbukkit.libs.com.google.gson.*;

public final class RemoteAuth extends JavaPlugin {

	private static String BaseURL = "remoteauth.herokuapp.com";
	private static int BasePort = 80;
	
//	private static String BaseURL = "127.0.0.1";
//	private static int BasePort = 8000;
	
	@Override
	public void onEnable() {
		new LoginListener(this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ranks")) {
			if (args.length == 0) {
				try {
					// We're listing all ranks.
					URL RanksURL = new URL("http", BaseURL, BasePort, "/ranks/");
					
					URLConnection getConn = RanksURL.openConnection();
			        getConn.connect();
			        BufferedReader dis = new BufferedReader(new InputStreamReader(getConn.getInputStream()));
			        
			        String json_string = dis.readLine().trim();
			        
			        String message = "";
			        		
			        message += "§E======================================\n";
			        message += "§E===========         RANKS         ==========\n";
			        message += "§E======================================\n";
			        
			        Gson gson = new Gson();
					RankListing details = gson.fromJson(json_string, RankListing.class);
					
					for (RankListingItem item: details.ranks) {
						String rankString = "";
						
						rankString += "§" + item.colour + item.name + " - ";
						
						if (item.price.equals("Free")) {
							rankString += "Free";
						}
						else {
							rankString += "£" + item.price;
						}
						
						rankString += "\n";
						
						message += rankString;
					}
					
					sender.sendMessage(message);
				}
				catch (Exception e) {
					getLogger().severe(e.toString());
				}
			}
			else if (args.length == 1) {
				// We're checking a rank.
				try {
					URL RanksURL = new URL("http", BaseURL, BasePort, "/ranks/" + args[0]);
					
					URLConnection getConn = RanksURL.openConnection();
			        getConn.connect();
			        BufferedReader dis = new BufferedReader(new InputStreamReader(getConn.getInputStream()));
			        
			        String json_string = dis.readLine().trim();
			        
			        Gson gson = new Gson();
					RankDetail details = gson.fromJson(json_string, RankDetail.class);
			        
			        String message = "";
			        		
			        message += "§E======================================\n";
			        message += "§E========== " + details.name + " - ";
			        
			        if (details.price.equals("Free")) {
			        	message += "Free";
			        }
			        else {
			        	message += "£" + details.price;
			        }
			        
			        message += " =========\n";
			        message += "§E======================================\n\n";
			        
					for (RankDetailItem item: details.description) {
						String rankString = item.text + "\n";
						message += rankString;
					}
					
					sender.sendMessage(message);
				}
				catch (Exception e) {
					getLogger().severe(e.toString());
				}
			}
			else {
				return false;
			}
			
			return true;
		}
		
		return false;
	}		
}