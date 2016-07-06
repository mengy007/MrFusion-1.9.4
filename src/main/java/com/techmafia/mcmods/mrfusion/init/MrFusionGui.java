package com.techmafia.mcmods.mrfusion.init;

import com.techmafia.mcmods.mrfusion.MrFusion;
import com.techmafia.mcmods.mrfusion.gui.handler.GuiHandlerBlockMrFusion;
import com.techmafia.mcmods.mrfusion.gui.handler.GuiHandlerRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by mengy007 on 1/31/2016.
 */
public class MrFusionGui {
    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(MrFusion.instance, GuiHandlerRegistry.getInstance());
        GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerBlockMrFusion(), GuiHandlerBlockMrFusion.getGuiID());
    }
}
