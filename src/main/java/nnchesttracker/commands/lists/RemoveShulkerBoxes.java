package nnchesttracker.commands.lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import nnchesttracker.MarkerList;
import nnchesttracker.Utils.testIfShulkerBox;

public class RemoveShulkerBoxes extends CommandBase {

	@Override
	public String getName() {
		return "removeshulkerboxes";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/nnchesttracker removeshulkerboxes";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		MarkerList.instance.markers.removeIf(new testIfShulkerBox());
		sender.sendMessage(new TextComponentString("Removed all shulker boxes"));
	}
}
