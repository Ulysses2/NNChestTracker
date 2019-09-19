package nnchesttracker.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import nnchesttracker.core.Configs;
import nnchesttracker.core.Configs.ChestTrackingState;

import java.util.List;

public class Tracking extends CommandBase {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "tracking";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/nnchesttracker tracking <on | off | existingOnly>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 1) {
			switch (args[0].toLowerCase()) {
			case "on":
				Configs.enableChestTracking = ChestTrackingState.ENABLED;
				break;
			case "off":
				Configs.enableChestTracking = ChestTrackingState.DISABLED;
				break;
			case "existingonly":
				Configs.enableChestTracking = ChestTrackingState.EXISTING_CHESTS_ONLY;
				break;
			default:
				throw new WrongUsageException(getUsage(sender));
			}
			Configs.sync();
			return;
		}
		throw new WrongUsageException(getUsage(sender));
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		if (args.length == 1)
			return getListOfStringsMatchingLastWord(args, new String[] { "on", "off", "existingOnly" });
		return super.getTabCompletions(server, sender, args, targetPos);
	}

}
