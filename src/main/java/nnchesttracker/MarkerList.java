package nnchesttracker;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import nnchesttracker.Utils.ChestOpenedStatus;
import nnchesttracker.core.Configs;
import nnchesttracker.core.NNChestTracker;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;

public class MarkerList {
	public static final MarkerList instance = new MarkerList();
	public ArrayList<Marker> markers;
	private String name;

	public MarkerList() {
		this.markers = new ArrayList<Marker>();
	}

	public void setName(@Nonnull String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/*public void loadNBTCompound() throws IOException {

		File file2 = new File(this.dataLocation, this.name + ".dat");
		this.clear();
		NBTTagCompound nbttagcompound = CompressedStreamTools.read(file2);
		if (nbttagcompound == null) {
			return;
		}

		NBTTagList nbttaglist = nbttagcompound.getTagList("markers", 10);

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			Marker m = new Marker(nbttaglist.getCompoundTagAt(i));
			this.markers.add(m);

		}

		return;
	}*/

	public void loadNBTCompound(NBTTagCompound nbttagcompound) {
		this.clear();

		if (nbttagcompound == null) {
			return;
		}

		NBTTagList markers = nbttagcompound.getTagList("markers", 10);
		// NBTTagList properties = nbttagcompound.getTagList("properties", 10);

		for (int i = 0; i < markers.tagCount(); ++i) {
			Marker m = new Marker(markers.getCompoundTagAt(i));
			this.markers.add(m);
		}
	}

	public NBTTagCompound getNBTCompound() {
		NBTTagList markers = new NBTTagList();
		// NBTTagList properties = new NBTTagList();

		for (Marker marker : this.markers) {
			markers.appendTag(marker.getNBTCompound());
		}

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setTag("markers", markers);
		// nbttagcompound.setTag("properties", properties);

		return nbttagcompound;
	}

	/*public void saveMarkerList() throws IOException {
		NBTTagList nbttaglist = new NBTTagList();

		dataLocation.mkdir();

		for (Marker marker : this.markers) {
			nbttaglist.appendTag(marker.getNBTCompound());
		}

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setTag("markers", nbttaglist);
		CompressedStreamTools.write(nbttagcompound, new File(dataLocation, this.name + ".dat"));

		return;
	}*/

	public ArrayList<String> getSavedListNames() {
		SaveLoad.listDataDir.mkdir();
		File[] files = SaveLoad.listDataDir.listFiles();
		ArrayList<String> result = new ArrayList<>();

		for (File file : files) {
			String name = file.getName();

			if (name.endsWith(".dat")) {
				name = name.substring(0, name.length() - 4);
				result.add(name);
			}
		}
		return result;
	}

	public Marker addMarker(String name, BlockPos pos, Block block, ChestOpenedStatus openedStatus, boolean isTemporary) {
		Marker m = new Marker(name, pos, block);
		this.markers.add(m);
		return m;
	}

	public void clear() {
		this.markers.clear();
	}

	public void setOpenedForAllMarkers(ChestOpenedStatus s) {
		for (Marker marker : markers) {
			marker.setOpened(s);
		}
	}

	public boolean contains(BlockPos pos) {
		return indexOf(pos) >= 0;
	}

	public int indexOf(BlockPos pos) {
		for (int i = 0; i < markers.size(); i++)
			if (pos.equals(markers.get(i).getPos())) {
				return i;
			}
		return -1;
	}

	public ArrayList<Marker> getMarkersWithName(String name) {
		ArrayList<Marker> result = new ArrayList<Marker>();
		for (Marker marker : this.markers) {
			if (name.equals(marker.getName()))
				result.add(marker);
		}
		return result;
	}

	public void sort() {
		this.markers.sort(null);
	}

	public void autoSave() {
		if (Configs.saving.autoSave) {
			try {
				SaveLoad.save(this);
			} catch (Exception e) {
				NNChestTracker.logger.catching(e);
			}
		}
	}
	
	public void removeAt(int i) {
		this.markers.remove(i);
	}

	public void removeAt(BlockPos pos) {
		this.removeAt(this.indexOf(pos));
	}

	public Marker markerAt(int i) {
		if (i < 0 || i >= markers.size()) return null;
		return this.markers.get(i);
	}

	public Marker markerAt(BlockPos pos) {
		return markerAt(this.indexOf(pos));
	}

	public int size() {
		return this.markers.size();
	}
}
