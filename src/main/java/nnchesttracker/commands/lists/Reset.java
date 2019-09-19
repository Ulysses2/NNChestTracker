package nnchesttracker.commands.lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import nnchesttracker.MarkerList;
import nnchesttracker.Utils.ChestOpenedStatus;

public class Reset extends CommandBase {

	@Override
	public String getName() {
		return "reset";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/nnchesttracker reset";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0) {
			MarkerList.instance.setOpenedForAllMarkers(ChestOpenedStatus.UNOPENED);
			MarkerList.instance.autoSave();
			sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "Set all markers in list " + TextFormatting.WHITE + MarkerList.instance.getName() + TextFormatting.GRAY + " to unopened"));
		} else {
			throw new WrongUsageException(this.getUsage(sender));
		}
	}

}
