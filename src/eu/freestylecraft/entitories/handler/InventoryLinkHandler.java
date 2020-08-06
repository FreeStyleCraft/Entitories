package eu.freestylecraft.entitories.handler;

import java.util.HashMap;

import org.bukkit.inventory.Inventory;

/**
 * Class for connecting inventories to a menu name.
 * This is used to determine settings like allow-inventory-click
 * in their respective events.
 * 
 * @author CraftKekser
 *
 */
public class InventoryLinkHandler {

	private volatile HashMap<Inventory, String> inventoryNames;
	
	public InventoryLinkHandler() {
		this.inventoryNames = new HashMap<Inventory, String>();
	}
	
	public void setInventory(Inventory inventory, String menu) {
		this.inventoryNames.put(inventory, menu);
	}
	
	public void unsetInventory(Inventory inventory) {
		if(this.inventoryNames.containsKey(inventory)) {
			this.inventoryNames.remove(inventory);
		}
	}
	
	public String getMenu(Inventory inventory) {
		if(this.inventoryNames.containsKey(inventory)) {
			return this.inventoryNames.get(inventory);
		}else {
			return "";
		}
	}
	
}
