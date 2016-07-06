package com.techmafia.mcmods.mrfusion;

import com.techmafia.mcmods.mrfusion.proxy.CommonProxy;
import com.techmafia.mcmods.mrfusion.reference.Reference;
import com.techmafia.mcmods.mrfusion.utility.LogHelper;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class MrFusion
{
    @Mod.Instance(Reference.MOD_ID)
    public static MrFusion instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent preInitializationEvent) {
        /* Config */
        //ConfigurationHandler.loadConfig(preInitializationEvent.getSuggestedConfigurationFile());
        //FMLCommonHandler.instance().bus().register(new ConfigurationHandler());

        /* proxy */
        proxy.preInit();
        proxy.registerClientStuff();

        LogHelper.info("Pre Init Complete!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent initializationEvent) {
        proxy.init();
    }
}
