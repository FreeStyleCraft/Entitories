package eu.freestylecraft.entitories.data;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import eu.freestylecraft.entitories.util.ChatUtils;

public class Menu {

	private String name;
	private String title;
	private boolean enabled;
	private int size;
	private Item placeholder;
	private boolean allowInventoryClick;
	private boolean closeOnClick;
	private HashMap<Integer, Item> items;

	public Menu(String name, String title, boolean enabled, int size, Item placeholder, boolean allowInventoryClick, boolean closeOnClick, HashMap<Integer, Item> items) {
		this.name = name;
		this.setTitle(title);
		this.setEnabled(enabled);
		this.setSize(size);
		this.setPlaceholder(placeholder);
		this.setAllowInventoryClick(allowInventoryClick);
		this.setCloseOnClick(closeOnClick);
		this.setItems(items);
	}
	
	public Inventory asInventory() {
		Inventory inventory = Bukkit.createInventory(null, this.size, ChatUtils.translate('&', this.getTitle()));
		// Set Placeholder
		if(this.getPlaceholder() != null) {
			for(int i = 0; i < inventory.getSize(); i++) {
				inventory.setItem(i, this.getPlaceholder().asItemStack());
			}
		}
		// Add items
		for(int pos : this.getItems().keySet()) {
			if(pos<=inventory.getSize()) { // to prevent "overflow"
				inventory.setItem(pos-1, this.getItems().get(pos).asItemStack());
			}
		}
		return inventory;
	}

	/**
	 * Returns the name of the inventory
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the name of the inventory
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the name of the inventory
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}	

	/**
	 * Returns the size of the menu
	 * @return the number of inventory rows
	 */
	public int getSize() {
		return size / 9;
	}

	/**
	 * Sets the size of the menu
	 * @param size the number of inventory rows to set
	 */
	public void setSize(int size) {
		this.size = size * 9;
	}

	/**
	 * Returns the default item for empty slots
	 * @return the default item
	 */
	public Item getPlaceholder() {
		return placeholder;
	}

	/**
	 * Sets the default item for empty slots
	 * @param placeholder the default item to set
	 */
	public void setPlaceholder(Item placeholder) {
		this.placeholder = placeholder;
	}

	/**
	 * Returns whether players are able to interact with other items in their inventory
	 * @return whether players are able to interact with other items in their inventory
	 */
	public boolean isAllowInventoryClick() {
		return allowInventoryClick;
	}

	/**
	 * Sets whether players are able to interact with other items in their inventory
	 * @param allowInventoryClick true to enable interactions
	 */
	public void setAllowInventoryClick(boolean allowInventoryClick) {
		this.allowInventoryClick = allowInventoryClick;
	}

	/**
	 * Returns whether to close the menu automatically after executing an action
	 * @return whether to close the menu automatically after executing an action
	 */
	public boolean isCloseOnClick() {
		return closeOnClick;
	}

	/**
	 * Sets whether to close the menu automatically after executing an action
	 * @param closeOnClick true to automatically close the menu
	 */
	public void setCloseOnClick(boolean closeOnClick) {
		this.closeOnClick = closeOnClick;
	}

	/**
	 * Returns a map containing the content of the menu
	 * as slot number (counting from 1) - item at that position
	 * @return the content
	 */
	public HashMap<Integer, Item> getItems() {
		return items;
	}

	/**
	 * Sets the contents of the menu
	 * as slot number (counting from 1) - item at that position
	 * @param items the contents to set
	 */
	public void setItems(HashMap<Integer, Item> items) {
		this.items = items;
	}

	/**
	 * Sets the item at a given position in the menu
	 * @param position the slot to place the item in, starting at 1 (top left)
	 * @param item the item to set
	 */
	public void setItem(int position, Item item) {
		this.items.put(position, item);
	}

	/**
	 * Removes the item at position, if present
	 * @param position the position of the item, starting at 1 (top left)
	 */
	public void removeItem(int position) {
		if(this.items.containsKey(position))
			this.items.remove(position);
	}


}
