package eu.freestylecraft.entitories.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;

import eu.freestylecraft.entitories.Entitories;
import eu.freestylecraft.entitories.data.EntityAttachment;

public class EntityRemovedListener implements Listener {

	private Entitories plugin;
	
	public EntityRemovedListener(Entitories plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityRemoved(EntityRemoveFromWorldEvent e) {
		EntityAttachment attachment;
		if((attachment=this.plugin.attachmentLoader.getAttachmentByEntity(e.getEntity().getUniqueId()))!=null) {
			this.plugin.attachmentLoader.removeAttachment(attachment);
		}
	}
	
}
