package nnchesttracker.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import nnchesttracker.core.Configs;

import java.util.List;

import static nnchesttracker.core.Configs.RenderLabelType.CUSTOM;
import static nnchesttracker.core.Configs.RenderLabelType.VANILLA;

public class Rendering extends CommandBase {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "rendering";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/nnchesttracker rendering <on | off> OR /nnchesttracker rendering type <custom | vanilla>";
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
				Configs.enableRendering = true;
				break;
			case "off":
				Configs.enableRendering = false;
				break;
			default:
				throw new WrongUsageException(getUsage(sender));
			}
			Configs.sync();
			return;
		}
		if (args.length == 2 && args[0].equalsIgnoreCase("type")) {
			switch (args[1].toLowerCase()) {
				case "custom":
					Configs.renderType = CUSTOM;
					break;
				case "vanilla":
					Configs.renderType = VANILLA;
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
			return getListOfStringsMatchingLastWord(args, new String[] { "on", "off", "type" });
		if (args.length == 2 && args[0] == "type") {
			return getListOfStringsMatchingLastWord(args, new String[] { "custom", "vanilla" });
		}
		return super.getTabCompletions(server, sender, args, targetPos);
	}

}
