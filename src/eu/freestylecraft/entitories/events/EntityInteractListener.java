package eu.freestylecraft.entitories.events;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;

import eu.freestylecraft.entitories.Entitories;
import eu.freestylecraft.entitories.data.EntityAttachment;
import eu.freestylecraft.entitories.data.Menu;

public class EntityInteractListener implements Listener {

	private Entitories plugin;
	
	public EntityInteractListener(Entitories plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityClick(PlayerInteractAtEntityEvent e) {
		UUID entityUUID = e.getRightClicked().getUniqueId();
		EntityAttachment attachment;
		if((attachment=this.plugin.attachmentLoader.getAttachmentByEntity(entityUUID))!=null) {
			Menu menu;
			if((menu=this.plugin.menuLoader.getMenuByName(attachment.getMenu()))!=null) {
				if(menu.isEnabled()) {
					Inventory inventory = menu.asInventory();
					e.getPlayer().openInventory(inventory);
					this.plugin.linkHandler.setInventory(inventory, menu.getName());
				}
			}
		}
	}
	
}
