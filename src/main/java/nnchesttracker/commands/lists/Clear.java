package nnchesttracker.commands.lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import nnchesttracker.MarkerList;

public class Clear extends CommandBase {

	@Override
	public String getName() {
		return "clear";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/nnchesttracker clear";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0) {
			MarkerList.instance.markers.clear();
			sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "Removed all markers from list " + TextFormatting.WHITE + MarkerList.instance.getName()));
		} else {
			throw new WrongUsageException(this.getUsage(sender));
		}
	}

}
