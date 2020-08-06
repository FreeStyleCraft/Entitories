package eu.freestylecraft.entitories.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import eu.freestylecraft.entitories.Entitories;
import eu.freestylecraft.entitories.data.EntityAttachment;
import eu.freestylecraft.entitories.data.Menu;

public class EntitoriesCommand implements CommandExecutor {

	private Entitories plugin;

	public EntitoriesCommand(Entitories plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(cmd.getName().equalsIgnoreCase("entitories")) {
			if(sender instanceof Player) {
				Player p = (Player)sender;

				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("detach")) {
						if(sender.hasPermission("entitories.detach")) {

							Entity lookingAt = p.getTargetEntity(30);
							if(lookingAt != null) {
								if(!this.plugin.blacklistedTypes.contains(lookingAt.getType())) {
									EntityAttachment attachment;
									if((attachment=this.plugin.attachmentLoader.getAttachmentByEntity(lookingAt.getUniqueId()))!=null) {
										if(this.plugin.attachmentLoader.removeAttachment(attachment)) {
											sender.sendMessage(Entitories.prefix + "§aMenü entfernt: " + attachment.getMenu());
										}else {
											sender.sendMessage(Entitories.prefix + "§cMenü nicht entfernt");
										}
									}else {
										sender.sendMessage(Entitories.prefix + "§cDieses Entity hat kein Menü");
									}
								}else {
									sender.sendMessage(Entitories.prefix + "§cDieses Entity kann nicht verwendet werden");
								}
							}else {
								sender.sendMessage(Entitories.prefix + "§cDu siehst kein Entity an");
							}
						}else {
							sender.sendMessage(Entitories.prefix + "§cKeine Berechtigung");
						}
					}else if(args[0].equalsIgnoreCase("menus")) {
						if(sender.hasPermission("entitories.list")) {
							for(Menu m : this.plugin.menuLoader.getMenus()) {
								sender.sendMessage(Entitories.prefix + m.getName());
							}
						}else {
							sender.sendMessage(Entitories.prefix + "§cKeine Berechtigung");
						}
					}else if(args[0].equalsIgnoreCase("reload")) {
						if(sender.hasPermission("entitories.reload")) {
							this.plugin.itemLoader.reload();
							this.plugin.menuLoader.reload(this.plugin.itemLoader);
							sender.sendMessage(Entitories.prefix + "§aKonfiguration neu geladen");
						}else {
							sender.sendMessage(Entitories.prefix + "§cKeine Berechtigung");
						}
					}else {
						sender.sendMessage(Entitories.prefix + "§c/entitories <attach [menu]|detach|menus>");
					}
				}else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("attach")) {
						if(sender.hasPermission("entitories.detach")) {
							Entity lookingAt = p.getTargetEntity(30);
							if(lookingAt != null) {
								if(!this.plugin.blacklistedTypes.contains(lookingAt.getType())) {
									Menu menu;
									if((menu=this.plugin.menuLoader.getMenuByName(args[1]))!=null) {
										EntityAttachment attachment = new EntityAttachment(lookingAt, menu);
										if(this.plugin.attachmentLoader.addAttachment(attachment)) {
											sender.sendMessage(Entitories.prefix + "§aDas Menü wurde dem Entity zugewiesen (" + menu.getName() + " <-> " + lookingAt.getUniqueId().toString() + ")");
										}else {
											sender.sendMessage(Entitories.prefix + "§cDas Menü konnte nicht zugewiesen werden");
										}
									}else {
										sender.sendMessage(Entitories.prefix + "§cMenü nicht gefunden");
									}
								}else {
									sender.sendMessage(Entitories.prefix + "§cDieses Entity kann nicht verwendet werden");
								}
							}else {
								sender.sendMessage(Entitories.prefix + "§cDu siehst kein Entity an");
							}
						}else {
							sender.sendMessage(Entitories.prefix + "§cKeine Berechtigung");
						}
					}else {
						sender.sendMessage(Entitories.prefix + "§c/entitories <attach [menu]|detach|menus>");
					}
				}else {
					sender.sendMessage(Entitories.prefix + "§c/entitories <attach [menu]|detach|menus>");
				}

			}else {
				Entitories.err("Dieser Befehl kann nur von einem Spieler ausgeführt werden");
			}
		}

		return true;
	}

}
