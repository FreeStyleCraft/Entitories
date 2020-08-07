package eu.freestylecraft.entitories.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class MenuLoader {
	private YamlConfiguration storage;
	private File storageFile;
	private List<Menu> cache;

	public MenuLoader(File yamlStorage) {
		this.storage = new YamlConfiguration();
		this.storageFile = yamlStorage;
		this.cache = Collections.synchronizedList(new ArrayList<Menu>());
	}

	/**
	 * Reloads the cache from menus.yml
	 * @return true if the cache was updated, false otherwise
	 */
	public boolean reload(ItemLoader itemLoader) {
		try {
			this.storage.load(this.storageFile);

			List<Menu> newCache = Collections.synchronizedList(new ArrayList<Menu>());
			for(String name : storage.getKeys(false)) {
				HashMap<Integer, Item> items = new HashMap<>();
				ConfigurationSection itemsSection = storage.getConfigurationSection(name + ".items");
				for(String position : itemsSection.getKeys(false)) {
					if(itemsSection.isString(position)) {
						Item item;
						if((item = itemLoader.getItemByName(itemsSection.getString(position)))!=null)
							items.put(Integer.valueOf(position), item);
					}else {
						String matname = itemsSection.getString(position + ".material", "RED_STAINED_GLASS_PANE");
						Material material = Material.getMaterial(matname);
						if(material == null) {
							material = Material.RED_STAINED_GLASS_PANE;
						}
						Item item = new Item(
								material, 
								itemsSection.getInt(position + ".amount", 1), 
								"itm"+String.valueOf(new Random().nextInt(9999) + 1000), 
								itemsSection.getString(position + ".name", "???"), 
								itemsSection.getString(position + ".description", ""), 
								itemsSection.getBoolean(position + ".enchanted", false), 
								itemsSection.getBoolean(position + ".close-on-click", false), 
								ItemAction.parse(itemsSection.getString(position + ".action", "none")));
						items.put(Integer.valueOf(position), item);
					}
				}
				Menu menu = new Menu(name, 
						storage.getString(name + ".title", "Menü"),
						storage.getBoolean(name + ".enabled", true), 
						storage.getInt(name + ".size", 3), 
						itemLoader.getItemByName(storage.getString(name + ".placeholder")), 
						storage.getBoolean(name + ".allow-inventory-click", false),
						storage.getBoolean(name + ".close-on-click", true),
						items);
				newCache.add(menu);
			}
			this.cache.clear();
			this.cache = newCache;

			return true;
		} catch (IOException | InvalidConfigurationException e) {
			return false;
		}
	}

	/**
	 * Returns all currently loaded menus
	 * @return a list of all menus
	 */
	public List<Menu> getMenus() {
		return this.cache;
	}

	/**
	 * Returns a menu by its name
	 * @param name the name to search for
	 * @return the menu matching the name, null if the menu was not found
	 */
	public Menu getMenuByName(String name) {
		for(Menu i : this.cache) {
			if(i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Returns a menu by its name, but case-insensitive
	 * @param name the name to search for
	 * @return the menu matching the name, null if the menu was not found
	 */
	public Menu getMenuByNameIgnoreCase(String name) {
		for(Menu i : this.cache) {
			if(i.getName().equalsIgnoreCase(name)) {
				return i;
			}
		}
		return null;
	}
}
