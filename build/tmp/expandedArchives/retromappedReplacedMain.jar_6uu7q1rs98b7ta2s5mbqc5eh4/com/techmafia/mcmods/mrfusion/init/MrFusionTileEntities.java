package com.techmafia.mcmods.mrfusion.init;

import com.techmafia.mcmods.mrfusion.tileentity.TileEntityMrFusion;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by mengy007 on 1/31/2016.
 */
public class MrFusionTileEntities {
    public static TileEntityMrFusion tileEntityMrFusion;

    public static void init() {
        tileEntityMrFusion = new TileEntityMrFusion();
        GameRegistry.registerTileEntity(TileEntityMrFusion.class, "mrfusion_te");
    }
}
