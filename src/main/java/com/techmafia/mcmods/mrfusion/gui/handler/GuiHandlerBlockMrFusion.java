package com.techmafia.mcmods.mrfusion.gui.handler;

import com.techmafia.mcmods.mrfusion.container.ContainerMrFusion;
import com.techmafia.mcmods.mrfusion.gui.inventory.GuiInventoryMrFusion;
import com.techmafia.mcmods.mrfusion.tileentity.TileEntityMrFusion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by mengy007 on 1/31/2016.
 */
public class GuiHandlerBlockMrFusion implements IGuiHandler {
    private static final int GUIID = 1;
    public static int getGuiID() { return GUIID; }

    // Gets the server side element for the given gui id - this should return a container
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID != getGuiID()) {
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity instanceof TileEntityMrFusion) {
            TileEntityMrFusion tileEntityMrFusion = (TileEntityMrFusion)tileEntity;
            return new ContainerMrFusion(player.inventory, tileEntityMrFusion);
        }
        return null;
    }

    // Gets the client side element for the given gui id- this should return a gui
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID != getGuiID()) {
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity instanceof TileEntityMrFusion) {
            TileEntityMrFusion tileEntityInventoryBasic = (TileEntityMrFusion) tileEntity;
            return new GuiInventoryMrFusion(player.inventory, tileEntityInventoryBasic);
        }
        return null;
    }
}
