package nnchesttracker;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import nnchesttracker.Utils.ChestOpenedStatus;
import org.apache.commons.lang3.math.NumberUtils;

import static net.minecraft.util.text.TextFormatting.*;
import static nnchesttracker.Utils.byteToOpenedStatus;

public class Marker implements Comparable<Marker> {
	private String name;
	private BlockPos pos;
	private Block block;
	private ChestOpenedStatus openedStatus;
	private ItemStack displayItem;

	public Marker(String name, BlockPos pos, Block block) {
		super();
		this.name = name;
		this.pos = pos;
		this.block = block;
		this.openedStatus = ChestOpenedStatus.UNOPENED;
		this.displayItem = ItemStack.EMPTY;
	}

	public Marker(NBTTagCompound markerNbt) {
		this.name = markerNbt.getString("name");
		int x = markerNbt.getInteger("x");
		int y = markerNbt.getInteger("y");
		int z = markerNbt.getInteger("z");
		this.pos = new BlockPos(x, y, z);
		ResourceLocation blockName = new ResourceLocation(markerNbt.getString("block"));
		this.block = Block.REGISTRY.getObject(blockName);
		this.openedStatus = byteToOpenedStatus(markerNbt.getByte("opened"));
		NBTBase item = markerNbt.getTag("displayitem");
		if (item instanceof NBTTagCompound) {
		this.displayItem = new ItemStack((NBTTagCompound) item);
		} else {
			this.displayItem = ItemStack.EMPTY;
		}
	}

	public NBTTagCompound getNBTCompound() {
		NBTTagCompound markerNbt = new NBTTagCompound();
		markerNbt.setString("name", this.name);
		markerNbt.setInteger("x", getPos().getX());
		markerNbt.setInteger("y", getPos().getY());
		markerNbt.setInteger("z", getPos().getZ());
		ResourceLocation blockName = this.block.getRegistryName();
		markerNbt.setString("block", blockName.toString());
		markerNbt.setByte("opened", Utils.openedStatusToByte(openedStatus));
		if (!displayItem.isEmpty()) {
			NBTTagCompound item = this.displayItem.serializeNBT();
			markerNbt.setTag("displayitem", item);
		}
		return markerNbt;
	}

	public NBTTagCompound debug() {
		return displayItem.writeToNBT(new NBTTagCompound());
	}

	@Override
	public String toString() {
		return getFormattedName() + ": " + getPos().getX() + ", " + getPos().getY() + ", " + getPos().getZ();

	}

	public String getFormattedName() {
		switch (openedStatus) {
		case OPENED_LOOTED:
			return GREEN + name;
		case OPENED_UNLOOTED:
			return GOLD + name;
		case UNOPENED:
			return RED + name;
		default:
			return name;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public ChestOpenedStatus getOpened() {
		return this.openedStatus;
	}

	public void setOpened(ChestOpenedStatus s) {
		this.openedStatus = s;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public ItemStack getDisplayItem() {
		return displayItem;
	}

	public void setDisplayItem(ItemStack displayItem) {
		this.displayItem = displayItem;
	}

	@Override
	public int compareTo(Marker m) {
		boolean a = NumberUtils.isParsable(this.name);
		boolean b = NumberUtils.isParsable(m.getName());
		if (a) {
			if (b) {
				return Integer.compare(Integer.parseInt(this.name), Integer.parseInt(m.getName()));
			}
			return -1;
		}
		if (b)
			return 1;
		return 0;
	}
}
