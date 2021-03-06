package eu.freestylecraft.entitories.data;

public class ItemAction {

	private ItemActionType type;
	private String command;

	public ItemAction(ItemActionType type, String command) {
		this.setType(type);
		this.setCommand(command);
	}

	public static ItemAction parse(String action) {
		if(action.startsWith("$/")) {
			return new ItemAction(ItemActionType.SERVER_COMMAND, action.replaceFirst("\\$\\/", ""));
		}else if(action.startsWith("/")) {
			return new ItemAction(ItemActionType.COMMAND, action.replaceFirst("\\/", ""));
		}else if(action.equalsIgnoreCase("none")||action.trim().isEmpty()) {
			return new ItemAction(ItemActionType.NONE, "");
		}else {
			return new ItemAction(ItemActionType.MENU_CONTROL, action);
		}
	}

	/**
	 * @return the type
	 */
	public ItemActionType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ItemActionType type) {
		this.type = type;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	public static enum ItemActionType {
		/**
		 * Actions that control the behavior of the menu
		 */
		MENU_CONTROL,
		/**
		 * Actions that execute some sort of command, originating from the player
		 */
		COMMAND,
		/**
		 * Actions that execute some sort of command, originating from the console
		 * Warning: dangerous! Think before using this!
		 */
		SERVER_COMMAND,
		/**
		 * Actions that do nothing
		 */
		NONE;
	}

}
