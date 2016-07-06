package com.techmafia.mcmods.mrfusion.block.itemblock;

import cofh.api.energy.EnergyStorage;
import com.techmafia.mcmods.mrfusion.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.*;

import java.util.List;

/**
 * Created by myang on 2/3/16.
 */
public class ItemBlockMrFusion extends ItemBlock {
    public ItemBlockMrFusion(Block block) {
        super(block);
    }

    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
        super.addInformation(itemStack, entityPlayer, list, par4);

        NBTTagCompound nbt = itemStack.getTagCompound();

        if (nbt != null) {
            if (!nbt.hasKey("Energy")) {
                nbt.setInteger("Energy", 0);
            }
            list.add(TextFormatting.WHITE + "" + nbt.getInteger("Energy") + " / " + Reference.MRFUSION_CAPACITY + " RF");
        }
    }
}
