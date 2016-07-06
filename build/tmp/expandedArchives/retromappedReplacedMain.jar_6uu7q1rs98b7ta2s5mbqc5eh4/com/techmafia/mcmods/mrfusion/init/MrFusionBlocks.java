package com.techmafia.mcmods.mrfusion.init;

import com.techmafia.mcmods.mrfusion.block.BlockMrFusion;
import com.techmafia.mcmods.mrfusion.block.itemblock.ItemBlockMrFusion;
import com.techmafia.mcmods.mrfusion.creativetab.CreativeTabMrFusion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by mengy007 on 1/31/2016.
 */
public class MrFusionBlocks {
    public static final BlockMrFusion blockMrFusion = new BlockMrFusion();

    public static void init() {
        blockMrFusion.func_149663_c("mrfusion");

        /* Register Blocks */
        GameRegistry.registerBlock(blockMrFusion, ItemBlockMrFusion.class, "mrfusion");

        /* Crafting recipes */
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockMrFusion, 1), new Object[]{
                "DID",
                "ISI",
                "DOD",
                'S', Items.field_151156_bN,
                'D', Blocks.field_150484_ah,
                'O', Blocks.field_150343_Z,
                'I', Blocks.field_150339_S
        }));

        /* Waila */
        FMLInterModComms.sendMessage("Waila", "register", "com.techmafia.mcmods.mrfusion.block.handler.WailaTileHandler.callbackRegister");
    }
}
