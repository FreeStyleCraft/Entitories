package eu.freestylecraft.entitories.handler;

import java.util.Collections;
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

	private HashMap<Inventory, String> inventoryNames;
	
	public InventoryLinkHandler() {
		this.inventoryNames = (HashMap<Inventory, String>) Collections.synchronizedMap(new HashMap<Inventory, String>());
	}
	
	public void setInventory(Inventory inventory, String menu) {
		this.inventoryNames.put(inventory, menu);
	}
	
	public void unsetInventory(Inventory inventory) {
		if(this.inventoryNames.containsKey(inventory)) {
			this.inventoryNames.remove(inventory);
		}
	}
	
}
