package nnchesttracker.core;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;

@Config(modid = NNChestTracker.MODID)
public class Configs {
	public static ConfigSaving saving = new ConfigSaving();
	
	//@Comment(value = "Render labels for opened chests as well as unopened ones")
	//public static boolean showOpenedChests = true;
	@Comment(value = "Trim chest names starting with \"Chest #\"")
	public static boolean trimChestNames = true;
	@Comment(value = "Sort all markers with integer names in order at the top of the list")
	public static boolean autoSort = true;
	public static ChestTrackingState enableChestTracking = ChestTrackingState.ENABLED;
	public static boolean enableRendering = true;
	@Comment(value = "Aliases for /nnchesttracker")
	@RequiresMcRestart
	public static String[] commandAliases = {"nn"};
	@Comment(value = "If set to VANILLA, will use vanilla player nameplates to show chest tags")
	public static RenderLabelType renderType = RenderLabelType.CUSTOM;
	
	public static class ConfigSaving {
		//public boolean saveLastListName = true;
		@Comment(value = "The list file with this name will be loaded when starting Minecraft.")
		public String nameOnStart = "default";
		@Comment(value = "Save when an unopened chest is opened.\nAlso save when opened chests are reset.")
		public boolean autoSave = true;
		@RequiresMcRestart
		@Comment(value = "Name of the folder in your Minecraft instance where list files are stored")
		public String dataDirectory = "nnchesttracker";
	}
	public enum RenderLabelType {
		VANILLA, CUSTOM
	}
	public enum ChestTrackingState {
		ENABLED, DISABLED, EXISTING_CHESTS_ONLY
	}
	public static void sync() {
		ConfigManager.sync(NNChestTracker.MODID, Type.INSTANCE);
	}
}
