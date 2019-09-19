package nnchesttracker.core;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nnchesttracker.MarkerList;
import nnchesttracker.SaveLoad;
import nnchesttracker.Utils;
import nnchesttracker.commands.BaseCommand;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = NNChestTracker.MODID, version = NNChestTracker.VERSION, name = NNChestTracker.NAME, clientSideOnly = true)
public class NNChestTracker {
	public static final String MODID = "nnchesttracker";
    public static final String NAME = "NNChestTracker";
    public static final String VERSION = "alpha-1";
	
	@Instance(NNChestTracker.MODID)
	public static NNChestTracker instance;
	public static Logger logger;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ClientCommandHandler.instance.registerCommand(new BaseCommand());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MarkerList.instance.setName(Configs.saving.nameOnStart);
    	SaveLoad.init();
    	Utils.init();
        try {
            SaveLoad.load(MarkerList.instance);
        } catch (IOException e) {
            NNChestTracker.logger.catching(e);
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        
    }
    
}

