package nnchesttracker.commands.lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import nnchesttracker.MarkerList;

public class Debug extends CommandBase {

	@Override
	public String getName() {
		return "debug";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		sender.sendMessage(new TextComponentString(MarkerList.instance.markers.get(parseInt(args[0])).debug().toString()));
	}

}
