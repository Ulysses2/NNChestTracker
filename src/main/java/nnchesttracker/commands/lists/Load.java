package nnchesttracker.commands.lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import nnchesttracker.MarkerList;
import nnchesttracker.SaveLoad;

import java.io.IOException;
import java.util.List;

import static net.minecraft.util.text.TextFormatting.GRAY;
import static net.minecraft.util.text.TextFormatting.WHITE;

public class Load extends CommandBase {

	@Override
	public String getName() {
		return "load";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/nnchesttracker load [name]";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 1) {
			throw new SyntaxErrorException("List names with spaces not supported", new Object[0]);
		}
		if (args.length == 1) {
			MarkerList.instance.setName(args[0]);
		}
		try {
			SaveLoad.load(MarkerList.instance);
			sender.sendMessage(new TextComponentString(GRAY +"Loaded " + WHITE + MarkerList.instance.size() + GRAY + " markers from list " + WHITE + MarkerList.instance.getName()));
		} catch (IOException e) {
			throw new CommandException(e.getMessage(), new Object[0]);
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		if (args.length == 1)
			return getListOfStringsMatchingLastWord(args, MarkerList.instance.getSavedListNames());
		return super.getTabCompletions(server, sender, args, targetPos);
	}

}
