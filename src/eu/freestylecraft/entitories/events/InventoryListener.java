package eu.freestylecraft.entitories.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import eu.freestylecraft.entitories.Entitories;
import eu.freestylecraft.entitories.data.Item;
import eu.freestylecraft.entitories.data.ItemAction;
import eu.freestylecraft.entitories.data.ItemAction.ItemActionType;
import eu.freestylecraft.entitories.data.Menu;

public class InventoryListener implements Listener {

	private Entitories plugin;

	public InventoryListener(Entitories plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		this.plugin.linkHandler.unsetInventory(e.getInventory());
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Inventory inventory = e.getClickedInventory();
		String menuName = this.plugin.linkHandler.getMenu(inventory);
		if(!menuName.isEmpty()) {
			Menu menu;
			if((menu=this.plugin.menuLoader.getMenuByName(menuName))!=null) {
				int slot = e.getSlot();
				if(menu.getItems().containsKey(slot+1)) {
					Item clicked = menu.getItems().get(slot+1);
					if(e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.LEFT) {
						ItemAction action = clicked.getAction();
						switch(action.getType()) {
						case COMMAND:
							this.plugin.getServer().dispatchCommand(e.getWhoClicked(), action.getCommand().replace("{player}", e.getWhoClicked().getName()).replace("{menu}", menu.getName()));
							break;
						case SERVER_COMMAND:
							this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), action.getCommand().replace("{player}", e.getWhoClicked().getName()).replace("{menu}", menu.getName()));
							break;
						case MENU_CONTROL:
							switch(action.getCommand().toLowerCase()) {
							case "close":
								e.getWhoClicked().closeInventory();
								break;
							}
							break;
						case NONE:
							break;
						}

						if(action.getType() != ItemActionType.NONE && menu.isCloseOnClick()) {
							e.getWhoClicked().closeInventory();
						}
						if(action.getType() != ItemActionType.NONE && clicked.isCloseOnClick()) {
							e.getWhoClicked().closeInventory();
						}
					}
				}else if(menu.getPlaceholder() != null) {
					Item clicked = menu.getPlaceholder();
					if(e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.LEFT) {
						ItemAction action = clicked.getAction();
						switch(action.getType()) {
						case COMMAND:
							this.plugin.getServer().dispatchCommand(e.getWhoClicked(), action.getCommand().replace("{player}", e.getWhoClicked().getName()).replace("{menu}", menu.getName()));
							break;
						case SERVER_COMMAND:
							this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), action.getCommand().replace("{player}", e.getWhoClicked().getName()).replace("{menu}", menu.getName()));
							break;
						case MENU_CONTROL:
							switch(action.getCommand().toLowerCase()) {
							case "close":
								e.getWhoClicked().closeInventory();
								break;
							}
							break;
						case NONE:
							break;
						}

						if(action.getType() != ItemActionType.NONE && menu.isCloseOnClick()) {
							e.getWhoClicked().closeInventory();
						}
						if(action.getType() != ItemActionType.NONE && clicked.isCloseOnClick()) {
							e.getWhoClicked().closeInventory();
						}
					}
				}
				
				if(!menu.isAllowInventoryClick()) {
					e.setCancelled(true);
				}
			}
		}
	}

}
