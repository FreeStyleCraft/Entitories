package eu.freestylecraft.entitories.util;

import org.bukkit.ChatColor;

public class ChatUtils {
	
	public static String translateAlternateHexCodes(char code, String input) {
		return input.replaceAll(code + "#([A-F,0-9])([A-F,0-9])([A-F,0-9])([A-F,0-9])([A-F,0-9])([A-F,0-9])", code + "x" + code + "$1" + code + "$2" + code + "$3" + code + "$4" + code + "$5" + code + "$6");
	}
	
	public static String translate(char code, String input) {
		return ChatColor.translateAlternateColorCodes('&', translateAlternateHexCodes(code, input));
	}
	
}
