package nnchesttracker.commands;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.command.CommandTreeHelp;
import nnchesttracker.commands.markers.Remove;
import nnchesttracker.commands.markers.Rename;

public class MarkerCommands extends CommandTreeBase {

	public MarkerCommands() {
		super.addSubcommand(new Remove());
		super.addSubcommand(new Rename());
		super.addSubcommand(new CommandTreeHelp(this));
	}

	@Override
	public String getName() {
		return "marker";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/nnchesttracker markers help";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

}
