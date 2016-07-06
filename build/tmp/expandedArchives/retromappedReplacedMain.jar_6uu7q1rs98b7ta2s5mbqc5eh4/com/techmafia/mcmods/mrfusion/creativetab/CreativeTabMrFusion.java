package com.techmafia.mcmods.mrfusion.creativetab;

import com.techmafia.mcmods.mrfusion.init.MrFusionBlocks;
import com.techmafia.mcmods.mrfusion.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by mengy007 on 1/31/2016.
 */
public class CreativeTabMrFusion {
    public static final CreativeTabs MRFUSION_TAB = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item func_78016_d() {
            return new ItemStack(MrFusionBlocks.blockMrFusion).func_77973_b();
        }

        @Override
        public String func_78024_c() {
            return "Mr. Fusion";
        }
    };
}
