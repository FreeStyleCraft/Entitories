# Entitories
Plugin for custom inventory menus.

---
## Usage
Drop the plugin jar in your plugins folder and restart your server.

### Adding items
You can add predefined items in items.yml to use them later in your menus.
However, you can also add items directly to your menus, but more on that later.
Defining items here will make them easily available to all menus, so you could define
things like placeholders here.

To create a new item, add a new configuration section to items.yml, with the section name being the item name.
In this configuration section, you can define the properties of the item as follows:
```yaml
your-item:
   material: 'STONE'
   amount: 1
   name: '&6Some Item'
   description: '&#22FF77First line\n&fSecond line'
   enchanted: true
   action: '/me has clicked an item in {menu}'
   close-on-click: false
```
```material``` defines the item that will be used. For a list of options, have a look at spigots [Material enum](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)

```amount```sets the amount of the item

```name``` is the name displayed when hovering over the item. You can use color codes: &0-9/a-f or &#RRGGBB

```description``` is the description of the item displayed when hovering over it (lore). You can use color codes: &0-9/a-f or &#RRGGBB and separate lines using ```\n```

```enchantet``` enchants the item with unbreaking I if set to true. However, players will not see the enchantment when hovering over the item

```action``` sets the action to be executed when a player clicks on the item.

Possible actions are:
* ```/command arg1 arg2 ...``` to execute a command as the player who clicked
* ```$/command arg1 arg2 ...``` to execute a command as console (dangerous)
* ```close``` to close the current menu
* ```none or empty string``` to do nothing

You can also use placeholders:
* ```{player}``` for the name of the player who clicked
* ```{menu}``` for the name of the current menu

```close-on-click``` will close the inventory after the action has been executed if set to true (This setting will be prioritized when set to false for the whole menu)

### Adding menus
To add a menu, create a new configuration section in menus.yml (like when adding an item) and set the properties as follows:
```yaml
awesome-menu:
   enabled: true
   title: '&2Awesome Menu'
   size: 3
   placeholder: 'default-placeholder'
   allow-inventory-click: false
   close-on-click: false
   items:
      12:
         material: 'GRASS_BLOCK'
         amount: 1
         name: 'Creative'
         description: 'Sets your gamemode to creative'
         enchanted: false
         action: '$/gamemode creative {player}'
         close-on-click: false
      16: 'your-item'
```
```enabled``` defines if this menu can be opened

```title``` sets the title (inventory name) of the menu. You can use color codes: &0-9/a-f or &#RRGGBB

```size``` sets the amount of inventory rows (9 slots) for this menu. It must be at least 1 but may not exceed 6 (double chest)

```placeholder``` sets the item that will be used for every empty slot in the menu. You can use any item defined in items.yml. To disable placeholders for a menu, leave this option empty.

```allow-inventory-click``` controls if a player can take or move the items of a menu

```close-on-click``` will close the inventory after any action that is not none has been executed if set to true (This setting will be prioritized when set to false for single items but to true here)

```items``` contains the items of the menu. The number sets the position of the item in the menu, starting at 1 in the upper-left corner. Then you can either provide the name of a predefined item (items.yml) or define an entirely new one.

### Attaching a menu
Before you can use your prevoiusly definde menus, you must reload the plugin. You can either restart the server, or use the command ```/entitories reload```.

After that, pick an entity of your choise, look at it and execute ```/entitories attach <name-of-your-menu>``` to attach a menu to that entity. If everything worked, rightclicking it should open the corresponding inventory. Any menus prevously attached to that entity will be detached.

If you need a list of all defined menus, you can use ```/entitories menus```.

### Detaching a menu
To detach a menu from an entity, look at it and execute ```/entitories detach```. Menus will be removed automatically when an entity dies or gets removed otherwise.

## Permissions

```entitories.attach``` to use ```/entitories attach <menu>``` (attach a menu)

```entitories.detach``` to use ```/entitories detach``` (detach a menu)

```entitories.list``` to use ```/entitories menus``` (list available menus)

```entitories.reload``` to use ```/entitories reload``` (reload the plugin)

## Notes

This plugin only handles the menus, not the actual entity behavior. If you want to have static entities and more control over them, you could use the [Citizens plugin](https://www.spigotmc.org/resources/citizens.13811/) for creating them.

Maybe a small feature like creating non-movable entities will be added in the future, but do not wait for it.
