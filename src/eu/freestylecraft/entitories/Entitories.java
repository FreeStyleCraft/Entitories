package eu.freestylecraft.entitories;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import eu.freestylecraft.entitories.commands.EntitoriesCommand;
import eu.freestylecraft.entitories.data.AttachmentLoader;
import eu.freestylecraft.entitories.data.ItemLoader;
import eu.freestylecraft.entitories.data.MenuLoader;
import eu.freestylecraft.entitories.events.EntityInteractListener;
import eu.freestylecraft.entitories.events.EntityRemovedListener;
import eu.freestylecraft.entitories.events.InventoryListener;
import eu.freestylecraft.entitories.handler.InventoryLinkHandler;

public class Entitories extends JavaPlugin{

	public static String prefix = "§8[§7Entitories§8] §7";
	
	public volatile ItemLoader itemLoader;
	public volatile MenuLoader menuLoader;
	public volatile AttachmentLoader attachmentLoader;
	public volatile InventoryLinkHandler linkHandler;
	public List<EntityType> blacklistedTypes;
	
	@Override
	public void onEnable() {
		log("Starte...");
		
		if(!this.getDataFolder().exists()) {
			this.getDataFolder().mkdirs();
		}
		this.blacklistedTypes = new ArrayList<>();
		
		log("Lade Items...");
		File itemStorage = new File(this.getDataFolder(), "items.yml");
		if(!itemStorage.exists()) {
			try {
				itemStorage.createNewFile();
				Files.copy(Entitories.class.getResourceAsStream("/items.yml"), itemStorage.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				err("Item-Speicher nicht erstellt", e);
				this.getServer().getPluginManager().disablePlugin(this);
				return;
			}
		}
		this.itemLoader = new ItemLoader(itemStorage);
		if(this.itemLoader.reload()) {
			log("Items geladen");
		}else {
			err("Items nicht geladen");
		}
		
		log("Lade Menus...");
		File menuStorage = new File(this.getDataFolder(), "menus.yml");
		if(!menuStorage.exists()) {
			try {
				menuStorage.createNewFile();
				Files.copy(Entitories.class.getResourceAsStream("/menus.yml"), menuStorage.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}catch(IOException e) {
				err("Menu-Speicher nicht erstellt", e);
				this.getServer().getPluginManager().disablePlugin(this);
				return;
			}
		}
		this.menuLoader = new MenuLoader(menuStorage);
		if(this.menuLoader.reload(this.itemLoader)) {
			log("Menus geladen");
		}else {
			err("Menus nicht geladen");
		}
		
		log("Lade Attachments...");
		File attachmentStorage = new File(this.getDataFolder(), "attachments.yml");
		if(!attachmentStorage.exists()) {
			try {
				attachmentStorage.createNewFile();
			}catch(IOException e) {
				err("Attachment-Speicher nicht erstellt", e);
				this.getServer().getPluginManager().disablePlugin(this);
				return;
			}
		}
		this.attachmentLoader = new AttachmentLoader(attachmentStorage);
		if(this.attachmentLoader.reload()) {
			log("Attachments geladen");
		}else {
			err("Attachments nicht geladen");
		}
		
		log("Initialisiere Link Handler...");
		this.linkHandler = new InventoryLinkHandler();
		
		log("Registriere Listener...");
		// Register events
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new EntityInteractListener(this), this);
		pm.registerEvents(new InventoryListener(this), this);
		pm.registerEvents(new EntityRemovedListener(this), this);
		
		log("Registriere Befehl...");
		this.getCommand("entitories").setExecutor(new EntitoriesCommand(this));
	}
	
	@Override
	public void onDisable() {

		log("Speichere Attachments...");
		if(this.attachmentLoader.flush()) {
			log("Attachments gespeichert");
		}else {
			err("Attachments nicht gespeichert");
		}
	}
	
	public static void log(String message) {
		Bukkit.getConsoleSender().sendMessage(prefix + message);
	}
	public static void err(String message) {
		Bukkit.getConsoleSender().sendMessage(prefix + "§c" + message);
	}
	public static void err(String message, Throwable th) {
		Bukkit.getConsoleSender().sendMessage(prefix + "§c" + message + "§c: " + th.getMessage() + " (" + th.getStackTrace()[0].getFileName() + ":" + th.getStackTrace()[0].getLineNumber() + ")");
	}
	
}
