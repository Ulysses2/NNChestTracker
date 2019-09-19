package nnchesttracker.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.command.CommandTreeHelp;
import nnchesttracker.commands.lists.*;
import nnchesttracker.core.Configs;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class BaseCommand extends CommandTreeBase implements IClientCommand {

	public BaseCommand() {
		super.addSubcommand(new Clear());
		super.addSubcommand(new Load());
		super.addSubcommand(new Save());
		super.addSubcommand(new Print());
		super.addSubcommand(new Reset());
		super.addSubcommand(new RemoveShulkerBoxes());
		super.addSubcommand(new Tracking());
		super.addSubcommand(new Rendering());
		super.addSubcommand(new MarkerCommands());
		super.addSubcommand(new CommandTreeHelp(this));
		//super.addSubcommand(new Debug());
	}

	@Override
	public String getName() {
		return "nnchesttracker";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/nnchesttracker help";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList(Configs.commandAliases);
	}

	@Override
	public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
		return false;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		return super.getTabCompletions(server, sender, args, getTargetBlockPos());
	}

	// temporary fix for forge bug
	@Nullable
	private static BlockPos getTargetBlockPos() {
		Minecraft mc = Minecraft.getMinecraft();
		BlockPos blockpos = null;

		if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
			blockpos = mc.objectMouseOver.getBlockPos();
		}

		return blockpos;
	}
}
