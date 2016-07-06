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
        public Item getTabIconItem() {
            return new ItemStack(MrFusionBlocks.blockMrFusion).getItem();
        }

        @Override
        public String getTranslatedTabLabel() {
            return "Mr. Fusion";
        }
    };
}