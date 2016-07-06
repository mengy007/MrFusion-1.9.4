package com.techmafia.mcmods.mrfusion.proxy;

import com.techmafia.mcmods.mrfusion.init.MrFusionGui;
import com.techmafia.mcmods.mrfusion.init.MrFusionTileEntities;
import com.techmafia.mcmods.mrfusion.init.MrFusionBlocks;
import com.techmafia.mcmods.mrfusion.init.MrFusionItems;
import com.techmafia.mcmods.mrfusion.net.CommonPacketHandler;

/**
 * Created by Meng on 7/31/2015.
 */
public class CommonProxy {
    public void preInit() {
        /* Network stuff */
        CommonPacketHandler.init();

        /* Items */
        MrFusionItems.init();

        /* Blocks */
        MrFusionBlocks.init();

        /* Tile Entities */
        MrFusionTileEntities.init();

        /* GUI */
        MrFusionGui.init();
    }

    public void registerClientStuff() {}

    public void init() {}
}
