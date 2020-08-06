package eu.freestylecraft.entitories.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.freestylecraft.entitories.util.ChatUtils;

public class Item {

	private Material material;
	private int amount;
	private String name;
	private String displayName;
	private String description;
	private boolean enchanted;
	private ItemAction action;
	
	public Item(Material material, int amount, String name, String displayName, String description, boolean enchanted, ItemAction action) {
		this.setMaterial(material);
		this.setAmount(amount);
		this.name = name;
		this.setDisplayName(displayName);
		this.setDescription(description);
		this.setEnchanted(enchanted);
		this.setAction(action);
	}
	
	/**
	 * Generates an ItemStack instance from the item
	 * @return the ItemStack
	 */
	public ItemStack asItemStack() {
		ItemStack stack = new ItemStack(this.getMaterial(), this.getAmount());
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§r§f" + ChatUtils.translate('&', this.getDisplayName()));
		if(!this.getDescription().trim().isEmpty()) {
			List<String> lore = new ArrayList<>();
			for(String line : this.getDescription().split("\\\\n")) {
				lore.add("§r§7" + ChatUtils.translate('&', line));
			}
			meta.setLore(lore);
		}
		stack.setItemMeta(meta);
		if(this.isEnchanted()) {
			stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		stack.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		return stack;
	}

	/**
	 * Returns the type of the item
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Sets the type of the item
	 * @param material the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	/**
	 * Returns the amount of the item in an ItemStack
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount of the item in an ItemStack
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Returns the name of the item
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the display name of the item
	 * This name will be shown in the menu
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name of the item
	 * This name will be shown in the menu
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Returns the description of the item
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the item
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns whether the item appears enchanted
	 * @return the enchanted
	 */
	public boolean isEnchanted() {
		return enchanted;
	}

	/**
	 * Sets whether the item appears enchanted
	 * @param enchanted true to enchant the item
	 */
	public void setEnchanted(boolean enchanted) {
		this.enchanted = enchanted;
	}

	/**
	 * Returns the action that will be executed when clicking the item
	 * @return the action
	 */
	public ItemAction getAction() {
		return action;
	}

	/**
	 * Sets the action that will be executed when clicking the item
	 * @param action the action to set
	 */
	public void setAction(ItemAction action) {
		this.action = action;
	}
	
}
