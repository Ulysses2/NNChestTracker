package nnchesttracker;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import nnchesttracker.core.Configs;

import java.io.File;
import java.io.IOException;

public class SaveLoad {
	public static File modDataDir;
	public static File listDataDir;
	
	public static void init() {
		modDataDir = new File(Minecraft.getMinecraft().mcDataDir, Configs.saving.dataDirectory);
		listDataDir = new File(modDataDir, "markers");
	}
	
	public static void save(MarkerList mm) throws IOException {
		save(mm.getNBTCompound(), mm.getName());
	}
	
	public static void load(MarkerList mm) throws IOException {
		mm.loadNBTCompound(load(mm.getName()));
    }
	
	public static void save(NBTTagCompound nbt, String name) throws IOException {
		modDataDir.mkdir();
		listDataDir.mkdir();
		File file = new File(listDataDir, name + ".dat");
		CompressedStreamTools.write(nbt, file);
	}
	public static NBTTagCompound load(String name) throws IOException {
		File file = new File(listDataDir, name + ".dat");
		NBTTagCompound nbt = CompressedStreamTools.read(file);
		return nbt;
	}
}
