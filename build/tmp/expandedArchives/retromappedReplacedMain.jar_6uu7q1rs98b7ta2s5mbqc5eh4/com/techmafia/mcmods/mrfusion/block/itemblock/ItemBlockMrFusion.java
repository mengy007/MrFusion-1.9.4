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

    public void func_77624_a(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
        super.func_77624_a(itemStack, entityPlayer, list, par4);

        NBTTagCompound nbt = itemStack.func_77978_p();

        if (nbt != null) {
            if (!nbt.func_74764_b("Energy")) {
                nbt.func_74768_a("Energy", 0);
            }
            list.add(TextFormatting.WHITE + "" + nbt.func_74762_e("Energy") + " / " + Reference.MRFUSION_CAPACITY + " RF");
        }
    }
}
