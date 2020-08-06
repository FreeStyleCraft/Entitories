package eu.freestylecraft.entitories.data;

import java.util.HashMap;

public class Menu {

	private String name;
	private String title;
	private boolean enabled;
	private int size;
	private Item placeholder;
	private boolean allowInventoryClick;
	private boolean itemsTakeable;
	private HashMap<Integer, Item> items;

	public Menu(String name, boolean enabled, int size, Item placeholder, boolean allowInventoryClick, boolean itemsTakeable, HashMap<Integer, Item> items) {
		this.name = name;
		this.setEnabled(enabled);
		this.setSize(size);
		this.setPlaceholder(placeholder);
		this.setAllowInventoryClick(allowInventoryClick);
		this.setItemsTakeable(itemsTakeable);
		this.setItems(items);
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
	 * Returns whether items can be taken out of the menu
	 * @return whether items can be taken out of the menu
	 */
	public boolean areItemsTakeable() {
		return itemsTakeable;
	}

	/**
	 * Sets whether items can be taken out if the menu
	 * @param itemsTakeable true to allow players to take items
	 */
	public void setItemsTakeable(boolean itemsTakeable) {
		this.itemsTakeable = itemsTakeable;
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
