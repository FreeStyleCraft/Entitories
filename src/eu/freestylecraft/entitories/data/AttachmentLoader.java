package eu.freestylecraft.entitories.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class AttachmentLoader {

	private YamlConfiguration storage;
	private File storageFile;
	private List<EntityAttachment> cache;

	public AttachmentLoader(File yamlStorage) {
		this.storage = new YamlConfiguration();
		this.storageFile = yamlStorage;
		this.cache = Collections.synchronizedList(new ArrayList<EntityAttachment>());
	}

	/**
	 * Reloads the cache from attachments.yml
	 * @return true if the cache was updated, false otherwise
	 */
	public boolean reload() {
		try {
			this.storage.load(this.storageFile);

			List<EntityAttachment> newCache = Collections.synchronizedList(new ArrayList<EntityAttachment>());
			for(String name : storage.getKeys(true)) {
				EntityAttachment attachment = new EntityAttachment(
						UUID.fromString(name), 
						storage.getString(name));
				newCache.add(attachment);
			}
			this.cache.clear();
			this.cache = newCache;

			return true;
		} catch (IOException | InvalidConfigurationException e) {
			return false;
		}
	}

	/**
	 * Writes the current cache to attachments.yml
	 * @return true if the cache was written, false otherwise
	 */
	public boolean flush() {
		try {
			YamlConfiguration empty = new YamlConfiguration();
			for(EntityAttachment attachment : this.cache) {
				empty.set(attachment.getEntity().toString(), attachment.getMenu());
			}
			empty.save(this.storageFile);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Adds an attachment
	 * 
	 * If the entity had an attachment already, it will be replaced with the new one.
	 * After adding the attachment to the cache, it is also saved directly to attachments.yml,
	 * but in case the writing fails, all changes to the cache will be reverted.
	 * 
	 * @param attachment the attachment to add
	 * @return true if the attachment was added and saved, false otherwise
	 */
	public boolean addAttachment(EntityAttachment attachment) {
		EntityAttachment previous = getAttachmentByEntity(attachment.getEntity());
		try {
			if(previous!=null) {
				this.cache.remove(previous);
				this.storage.set(previous.getEntity().toString(), null);
			}
			this.cache.add(attachment);
			this.storage.set(attachment.getEntity().toString(), attachment.getMenu());
			this.storage.save(this.storageFile);
			return true;
		}catch(IOException ex) {
			if(previous!=null) {
				this.cache.remove(attachment);
				this.storage.set(attachment.getEntity().toString(), null);
				this.cache.add(previous);
				this.storage.set(previous.getEntity().toString(), previous.getMenu());
			}
			return false;
		}
	}

	/**
	 * Removes an attachment
	 * 
	 * After removing the attachment from the cache, it is also removed directly from attachments.yml,
	 * but in case the writing fails, all changes to the cache will be reverted.
	 * 
	 * @param attachment the attachment to remove
	 * @return true if the attachment was removed and saved, false otherwise
	 */
	public boolean removeAttachment(EntityAttachment attachment) {
		try {
			this.cache.remove(attachment);
			this.storage.set(attachment.getEntity().toString(), null);
			this.storage.save(storageFile);
			return true;
		}catch(IOException ex) {
			this.cache.add(attachment);
			this.storage.set(attachment.getEntity().toString(), attachment.getMenu());
			return false;
		}
	}

	/**
	 * Returns all currently loaded attachments
	 * @return a list of all attachments
	 */
	public List<EntityAttachment> getAttachments() {
		return this.cache;
	}

	/**
	 * Returns an attachment by its entity uuid
	 * @param entityUUID the uuid to search for
	 * @return the attachment matching the uuid, null if the attachment was not found
	 */
	public EntityAttachment getAttachmentByEntity(UUID entityUUID) {
		for(EntityAttachment i : this.cache) {
			if(i.getEntity().toString().equals(entityUUID.toString())) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Returns all attachments for a menu
	 * @param menu the menu name to search for
	 * @return all attachments including the menu
	 */
	public List<EntityAttachment> getAttachmentByMenu(String menu) {
		List<EntityAttachment> attachments = new ArrayList<>();
		for(EntityAttachment i : this.cache) {
			if(i.getMenu().equals(menu)) {
				attachments.add(i);
			}
		}
		return attachments;
	}

	/**
	 * Returns all attachments for a menu, but case-insensitive
	 * @param menu the menu name to search for
	 * @return all attachments including the menu
	 */
	public List<EntityAttachment> getAttachmentByEntityIgnoreCase(String menu) {
		List<EntityAttachment> attachments = new ArrayList<>();
		for(EntityAttachment i : this.cache) {
			if(i.getMenu().equalsIgnoreCase(menu)) {
				attachments.add(i);
			}
		}
		return attachments;
	}


}
