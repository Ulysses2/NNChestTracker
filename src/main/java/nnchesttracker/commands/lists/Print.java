package nnchesttracker.commands.lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import nnchesttracker.Marker;
import nnchesttracker.MarkerList;

import static net.minecraft.util.text.TextFormatting.GRAY;
import static net.minecraft.util.text.TextFormatting.WHITE;

public class Print extends CommandBase {

	@Override
	public String getName() {
		return "print";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/nnchesttracker print";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		sender.sendMessage(new TextComponentString(GRAY + "List " + WHITE + MarkerList.instance.getName() + GRAY + " with " + MarkerList.instance.markers.size() + " markers: "));
		for (int i = 0; i < MarkerList.instance.markers.size(); i++) {
			Marker marker = MarkerList.instance.markers.get(i);
			sender.sendMessage(new TextComponentString("[" + i + "] " + marker.toString()));
		}
	}

}
