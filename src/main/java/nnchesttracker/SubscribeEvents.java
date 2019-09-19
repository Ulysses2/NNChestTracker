package nnchesttracker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import nnchesttracker.Utils.ChestOpenedStatus;
import nnchesttracker.core.Configs;
import nnchesttracker.core.Configs.ChestTrackingState;
import nnchesttracker.core.NNChestTracker;

@EventBusSubscriber
public class SubscribeEvents {

	static Marker lastMarker;
	static Container openInv;
	//private static GuiScreen guidebug;

	@SubscribeEvent
	public static void usedBlock(PlayerInteractEvent.RightClickBlock event) {

		if (event.getSide() != Side.CLIENT || Configs.enableChestTracking == ChestTrackingState.DISABLED)
			return;

		openInv = null;
		lastMarker = null;

		BlockPos location = new BlockPos(event.getPos());
		IBlockState clickedBlockState = event.getWorld().getBlockState(location);
		Block clickedBlock = clickedBlockState.getBlock();
		if (((clickedBlock instanceof BlockChest) || (clickedBlock instanceof BlockShulkerBox)) && Utils.willBlockBeActivated(event)) {

			if (!MarkerList.instance.contains(location)) {
				if (Configs.enableChestTracking == ChestTrackingState.EXISTING_CHESTS_ONLY)
					return;
				String str = "";
				BlockPos signLoc = location.up();
				TileEntity e = event.getWorld().getTileEntity(signLoc);
				if (e instanceof TileEntitySign) {
					TileEntitySign signEnt = (TileEntitySign) e;
					str = Utils.parseSignText(signEnt.signText);
				}
				if (str.isEmpty()) {
					str = clickedBlock.getLocalizedName();
				}
				lastMarker = MarkerList.instance.addMarker(str, location, clickedBlock, ChestOpenedStatus.UNOPENED, false);
				if (Configs.autoSort)
					MarkerList.instance.sort();
			} else {
				lastMarker = MarkerList.instance.markers.get(MarkerList.instance.indexOf(location));
			}
		}
	}

	@SubscribeEvent
	public static void usedItem(PlayerInteractEvent.RightClickItem event) {
		if (event.getSide() != Side.CLIENT)
			return;
		lastMarker = null;
	}

	@SubscribeEvent
	public static void openGui(GuiOpenEvent event) {
		
		/*
		 * System.out.println(event.getGui() + " opened");
		 * System.out.println("Previous gui was:"); System.out.println(guidebug); if
		 * (guidebug instanceof GuiContainer) {
		 * System.out.println("and it contained the container:"); Container contaidebug
		 * = ((GuiContainer) guidebug).inventorySlots; System.out.println(contaidebug);
		 * System.out.println("with slots:"); for (Slot iterable_element :
		 * contaidebug.inventorySlots) {
		 * 
		 * System.out.println(iterable_element.getStack().toString() + " in inventory "
		 * + iterable_element.inventory.toString());
		 * 
		 * } }
		 * 
		 * guidebug = event.getGui();
		 */
		
		if (lastMarker != null && Configs.enableChestTracking != ChestTrackingState.DISABLED) {
			if (openInv != null) {
				ItemStack item = Utils.pickItemFromChest(openInv);

				if (!item.isEmpty()) {
					lastMarker.setOpened(ChestOpenedStatus.OPENED_UNLOOTED);
				} else {
					lastMarker.setOpened(ChestOpenedStatus.OPENED_LOOTED);
				}
				
				lastMarker.setDisplayItem(item);
				openInv = null;
				lastMarker = null;
				MarkerList.instance.autoSave();
			} else {
				GuiScreen gui = event.getGui();
				if ((gui instanceof GuiChest)) {
					openInv = ((GuiChest) gui).inventorySlots;
				}
			}
		}
	}

	@SubscribeEvent
	public static void renderEvent(RenderWorldLastEvent event) {
		if (!Configs.enableRendering)
			return;
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		FontRenderer fontRendererIn = rm.getFontRenderer();

		if (rm.options == null || fontRendererIn == null)
			return;

		for (Marker marker : MarkerList.instance.markers) {
			String str = marker.getFormattedName();
			ItemStack item = marker.getDisplayItem();
			// LabelRender.renderItem(rm, item, mark.getPos());
			LabelRender.drawLabel(rm, fontRendererIn, str, marker.getPos(), item);
		}
	}
	@SubscribeEvent
    public static void onConfigChangedEvent(OnConfigChangedEvent event)
    {
        if (event.getModID().equals(NNChestTracker.MODID))
        {
            Configs.sync();
        }
    }
}
