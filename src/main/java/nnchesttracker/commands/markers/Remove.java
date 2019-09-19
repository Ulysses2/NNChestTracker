package nnchesttracker.commands.markers;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import nnchesttracker.Marker;
import nnchesttracker.MarkerList;

import java.util.Collections;
import java.util.List;

public class Remove extends CommandBase {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "remove";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/nnchesttracker marker remove <x> <y> <z> OR /nnchesttracker marker remove <index>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		int i;
		if (args.length == 3) {
			 BlockPos pos = parseBlockPos(sender, args, 0, true);
			 i = MarkerList.instance.indexOf(pos);
			 
			} else if (args.length == 1) {
				i = parseInt(args[0]);
			} else {
				throw new WrongUsageException(getUsage(sender), new Object[0]);
			}
		Marker m = MarkerList.instance.markerAt(i);
		if (m == null) throw new CommandException("Marker not found");
		MarkerList.instance.removeAt(i);
		sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "Removed: " + m.toString()));
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		if (args.length > 0 && args.length <= 3)
        {
            return getTabCompletionCoordinate(args, 0, targetPos);
        }	
		return Collections.emptyList();
	}
}
