package eu.freestylecraft.entitories.data;

import java.util.UUID;

import org.bukkit.entity.Entity;

public class EntityAttachment {

	private UUID entity;
	private String menu;
	
	public EntityAttachment(UUID entity, String menu) {
		this.setEntity(entity);
		this.setMenu(menu);
	}
	
	public EntityAttachment(Entity entity, String menu) {
		this.setEntity(entity.getUniqueId());
		this.setMenu(menu);
	}
	
	public EntityAttachment(UUID entity, Menu menu) {
		this.setEntity(entity);
		this.setMenu(menu.getName());
	}
	
	public EntityAttachment(Entity entity, Menu menu) {
		this.setEntity(entity.getUniqueId());
		this.setMenu(menu.getName());
	}

	/**
	 * @return the entity
	 */
	public UUID getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(UUID entity) {
		this.entity = entity;
	}

	/**
	 * @return the menu
	 */
	public String getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
}
