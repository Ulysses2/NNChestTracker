package nnchesttracker;

import net.minecraft.block.BlockShulkerBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import nnchesttracker.core.Configs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

public class Utils {
	public static Item tripwire_hook;
	public static Item head;

	public static void init() {
		tripwire_hook = Item.REGISTRY.getObject(new ResourceLocation("minecraft:tripwire_hook"));
		head = Item.REGISTRY.getObject(new ResourceLocation("minecraft:skull"));
	}

	public static String parseSignText(ITextComponent[] text) {
		String result = "";
		for (int i = 0; i < text.length; i++) {
			String line = text[i].getUnformattedText().trim();
			if (!line.isEmpty()) {
				if (result.isEmpty()) {
					result = line;
				} else {
					result += " " + line;
				}
			}
		}
		if (Configs.trimChestNames && result.startsWith("Chest #")) {
			return result.substring("Chest #".length());
		}
		return result;
	}

	public static boolean willBlockBeActivated(PlayerInteractEvent.RightClickBlock event) {
		EntityPlayer player = event.getEntityPlayer();
		World worldIn = event.getWorld();
		BlockPos pos = event.getPos();

		boolean bypass = player.getHeldItemMainhand().doesSneakBypassUse(worldIn, pos, player) && player.getHeldItemOffhand().doesSneakBypassUse(worldIn, pos, player);

		if ((!player.isSneaking() || bypass || event.getUseBlock() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW)) {
			if (event.getUseBlock() != net.minecraftforge.fml.common.eventhandler.Event.Result.DENY)
				return true;
		}
		return false;
	}

	public static class testIfShulkerBox implements Predicate<Marker> {
		@Override
		public boolean test(Marker t) {
			return t.getBlock() instanceof BlockShulkerBox;
		}
	}

	public static enum ChestOpenedStatus {
		UNOPENED, OPENED_UNLOOTED, OPENED_LOOTED;
	}

	public static byte openedStatusToByte(ChestOpenedStatus s) {
		switch (s) {
		case UNOPENED:
			return 0;
		case OPENED_UNLOOTED:
			return 1;
		case OPENED_LOOTED:
			return 2;
		default:
			return 0;
		}
	}

	public static ChestOpenedStatus byteToOpenedStatus(byte i) {
		switch (i) {
		case 0:
			return ChestOpenedStatus.UNOPENED;
		case 1:
			return ChestOpenedStatus.OPENED_UNLOOTED;
		case 2:
			return ChestOpenedStatus.OPENED_LOOTED;
		default:
			return ChestOpenedStatus.UNOPENED;
		}
	}

	public static ItemStack pickItemFromChest(Container openInv) {
		// TODO replace with better code
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (Slot slot : openInv.inventorySlots) {
			if (!(slot.inventory instanceof InventoryPlayer)) {
				ItemStack item = slot.getStack();
				if (!item.isEmpty()) {
					items.add(item);
				}
			}
		}
		if (items.size() == 0) {
			return ItemStack.EMPTY;
		}
		items.sort(new compareValue());

		return items.get(0);
	}

	// TODO replace with better code
	public static class compareValue implements Comparator<ItemStack> {

		@Override
		public int compare(ItemStack o1, ItemStack o2) {
			return Integer.compare(getValue(o1), getValue(o2));
		}

	}

	public static int getValue(ItemStack itemStack) {
		int value = 0;
		if (itemStack.getItem() == tripwire_hook) {
			value = 60;
		} else if (itemStack.getItem() == head) {
			value = 30;
		}
		if (itemStack.isItemEnchanted()) {
			value++;
		}
		if (itemStack.hasDisplayName()) {
			value++;
		}
		if (itemStack.hasTagCompound()) {
			value++;
		}
		return -value;
	}
}