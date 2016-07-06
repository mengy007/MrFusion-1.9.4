package com.techmafia.mcmods.mrfusion.block;

import com.techmafia.mcmods.mrfusion.MrFusion;
import com.techmafia.mcmods.mrfusion.creativetab.CreativeTabMrFusion;
import com.techmafia.mcmods.mrfusion.gui.handler.GuiHandlerBlockMrFusion;
import com.techmafia.mcmods.mrfusion.tileentity.TileEntityMrFusion;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//import com.techmafia.mcmods.mrfusion.init.MrFusionBlocks;
import com.techmafia.mcmods.mrfusion.tileentity.TileEntityMrFusion;
//import moze_intel.projecte.api.item.IItemEmc;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
//import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumWorldBlockLayer;
//import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mengy007 on 1/31/2016.
 */
public class BlockMrFusion extends Block implements ITileEntityProvider {
    public BlockMrFusion() {
        super(Material.field_151576_e);
        this.func_149647_a(CreativeTabMrFusion.MRFUSION_TAB);
    }

    /*
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.SOLID;
    }
    */

    @Override
    public boolean func_149662_c(IBlockState state) {
        return true;
    }

    @Override
    public boolean func_149686_d(IBlockState state) {
        return true;
    }

    @Override
    public EnumBlockRenderType func_149645_b(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        // Uses the gui handler registered to your mod to open the gui for the given gui id
        // open on the server side only  (not sure why you shouldn't open client side too... vanilla doesn't, so we better not either)
        if (worldIn.field_72995_K) return true;

        playerIn.openGui(MrFusion.instance, GuiHandlerBlockMrFusion.getGuiID(), worldIn, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());

        return true;
    }

    /* ITileEntityProvider */
    @Override
    public TileEntity func_149915_a(World worldIn, int meta) {
        return new TileEntityMrFusion();
    }

    /* Transfer energy on block break and place */
    @Override
    public void func_180663_b(World world, BlockPos blockPos, IBlockState blockState) {
        if (world.field_72995_K) { return; }

        NBTTagCompound nbt = new NBTTagCompound();
        TileEntity te = world.func_175625_s(blockPos);

        te.func_189515_b(nbt);

        ItemStack itemStack = new ItemStack(Item.func_150898_a(this), 1);

        if (te != null && te instanceof TileEntityMrFusion) {
            EntityItem ei = new EntityItem(world, blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p(), itemStack);

            nbt.func_74768_a("Energy", ((TileEntityMrFusion)te).getEnergyStored(null));
            ei.func_92059_d().func_77982_d(nbt);

            world.func_72838_d(ei);

            ei = null;

            // Drop items in inv as well
            ItemStack invStack = ((TileEntityMrFusion)te).func_70301_a(0);
            if (invStack != null && invStack.field_77994_a > 0) {
                ei = new EntityItem(world, blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p(), invStack);
                world.func_72838_d(ei);
            }
        }
    }

    @Override
    public Item func_180660_a(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void func_180633_a(World world, BlockPos blockPos, IBlockState blockState, EntityLivingBase placer, ItemStack stack) {
        super.func_180633_a(world, blockPos, blockState, placer, stack);

        TileEntity te = world.func_175625_s(blockPos);

        if (te != null && te instanceof TileEntityMrFusion) {
            NBTTagCompound nbt = stack.func_77978_p();

            if (nbt != null && nbt.func_74764_b("Energy")) {
                ((TileEntityMrFusion) te).setEnergy(nbt.func_74762_e("Energy"));
            }
        }
    }
}
