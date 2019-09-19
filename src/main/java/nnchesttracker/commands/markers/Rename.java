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

public class Rename extends CommandBase {

	@Override
	public String getName() {
		return "rename";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/nnchesttracker marker rename <x> <y> <z> <name> OR /nnchesttracker marker rename <index> <name>";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		Marker m;
		if (args.length == 4) {
			 BlockPos pos = parseBlockPos(sender, args, 0, true);
			 m = MarkerList.instance.markerAt(pos);
			 
			} else if (args.length == 2) {
				int i = parseInt(args[0]);
				m = MarkerList.instance.markerAt(i);
			} else {
				throw new WrongUsageException(getUsage(sender), new Object[0]);
			}
		if (m == null) throw new CommandException("Marker not found");
		m.setName(args[1]);
		sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "Updated: " + m.toString()));
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
