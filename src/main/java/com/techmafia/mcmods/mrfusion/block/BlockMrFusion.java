package com.techmafia.mcmods.mrfusion.block;

import com.techmafia.mcmods.mrfusion.MrFusion;
import com.techmafia.mcmods.mrfusion.creativetab.CreativeTabMrFusion;
import com.techmafia.mcmods.mrfusion.gui.handler.GuiHandlerBlockMrFusion;
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
        super(Material.ROCK);
        this.setCreativeTab(CreativeTabMrFusion.MRFUSION_TAB);
    }

    /*
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.SOLID;
    }
    */

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        // Uses the gui handler registered to your mod to open the gui for the given gui id
        // open on the server side only  (not sure why you shouldn't open client side too... vanilla doesn't, so we better not either)
        if (worldIn.isRemote) return true;

        playerIn.openGui(MrFusion.instance, GuiHandlerBlockMrFusion.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    /* ITileEntityProvider */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMrFusion();
    }

    /* Transfer energy on block break and place */
    @Override
    public void breakBlock(World world, BlockPos blockPos, IBlockState blockState) {
        if (world.isRemote) { return; }

        NBTTagCompound nbt = new NBTTagCompound();
        TileEntity te = world.getTileEntity(blockPos);

        te.writeToNBT(nbt);

        ItemStack itemStack = new ItemStack(Item.getItemFromBlock(this), 1);

        if (te != null && te instanceof TileEntityMrFusion) {
            EntityItem ei = new EntityItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);

            nbt.setInteger("Energy", ((TileEntityMrFusion)te).getEnergyStored(null));
            ei.getEntityItem().setTagCompound(nbt);

            world.spawnEntityInWorld(ei);

            ei = null;

            // Drop items in inv as well
            ItemStack invStack = ((TileEntityMrFusion)te).getStackInSlot(0);
            if (invStack != null && invStack.stackSize > 0) {
                ei = new EntityItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), invStack);
                world.spawnEntityInWorld(ei);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState blockState, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, blockPos, blockState, placer, stack);

        TileEntity te = world.getTileEntity(blockPos);

        if (te != null && te instanceof TileEntityMrFusion) {
            NBTTagCompound nbt = stack.getTagCompound();

            if (nbt != null && nbt.hasKey("Energy")) {
                ((TileEntityMrFusion) te).setEnergy(nbt.getInteger("Energy"));
            }
        }
    }
}
