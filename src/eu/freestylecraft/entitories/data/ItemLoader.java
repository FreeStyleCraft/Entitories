package eu.freestylecraft.entitories.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class ItemLoader {

	private YamlConfiguration storage;
	private File storageFile;
	private List<Item> cache;

	public ItemLoader(File yamlStorage) {
		this.storage = new YamlConfiguration();
		this.storageFile = yamlStorage;
		this.cache = Collections.synchronizedList(new ArrayList<Item>());
	}

	/**
	 * Reloads the cache from items.yml
	 * @return true if the cache was updated, false otherwise
	 */
	public boolean reload() {
		try {
			this.storage.load(this.storageFile);

			List<Item> newCache = Collections.synchronizedList(new ArrayList<Item>());
			for(String name : storage.getKeys(false)) {
				String matname = storage.getString(name + ".material", "RED_STAINED_GLASS_PANE");
				Material material = Material.getMaterial(matname);
				if(material == null) {
					material = Material.RED_STAINED_GLASS_PANE;
				}
				Item item = new Item(
						material, 
						storage.getInt(name + ".amount", 1), 
						name, 
						storage.getString(name + ".name", "???"), 
						storage.getString(name + ".description", ""), 
						storage.getBoolean(name + ".enchanted", false), 
						storage.getBoolean(name + ".close-on-click", false), 
						ItemAction.parse(storage.getString(name + ".action", "none")));
				newCache.add(item);
			}
			this.cache.clear();
			this.cache = newCache;

			return true;
		} catch (IOException | InvalidConfigurationException e) {
			return false;
		}
	}

	/**
	 * Returns all currently loaded items
	 * @return a list of all items
	 */
	public List<Item> getItems() {
		return this.cache;
	}

	/**
	 * Returns an item by its name
	 * @param name the name to search for
	 * @return the item matching the name, null if the item was not found
	 */
	public Item getItemByName(String name) {
		for(Item i : this.cache) {
			if(i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Returns an item by its name, but case-insensitive
	 * @param name the name to search for
	 * @return the item matching the name, null if the item was not found
	 */
	public Item getItemByNameIgnoreCase(String name) {
		for(Item i : this.cache) {
			if(i.getName().equalsIgnoreCase(name)) {
				return i;
			}
		}
		return null;
	}

}
