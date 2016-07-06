package com.techmafia.mcmods.mrfusion.tileentity;

import cofh.api.energy.*;
import com.techmafia.mcmods.mrfusion.net.CommonPacketHandler;
import com.techmafia.mcmods.mrfusion.net.messages.DeviceUpdateMessage;
import com.techmafia.mcmods.mrfusion.reference.Reference;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.api.tile.IEmcAcceptor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mengy007 on 1/31/2016.
 */
public class TileEntityMrFusion extends TileEntity implements ITickable, IEnergyHandler, IEnergyReceiver, IEnergyProvider, IEmcAcceptor, IInventory {
    final int NUMBER_OF_SLOTS = 1;
    final String DISPLAY_NAME = "Mr. Fusion";

    protected EnergyStorage energyStorage = new EnergyStorage(Reference.MRFUSION_CAPACITY);

    private int ticksSinceLastUpdate = 0;
    private int ticksBetweenUpdates = 3;
    private ItemStack itemStack;
    private Set<EntityPlayer> playersWatching;

    public TileEntityMrFusion() {
        playersWatching = new HashSet<EntityPlayer>();
    }

    /* Server sync */
    @Override
    public SPacketUpdateTileEntity func_189518_D_() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        func_189515_b(nbtTagCompound);
        int metadata = func_145832_p();
        return new SPacketUpdateTileEntity(this.field_174879_c, metadata, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        func_145839_a(pkt.func_148857_g());
    }

    public void beginUpdatingPlayer(EntityPlayer player) {
        playersWatching.add(player);
        sendUpdatePacketToPlayer(player);
    }

    public void stopUpdatingPlayer(EntityPlayer player) {
        playersWatching.remove(player);
    }

    private void sendUpdatePacketToPlayer(EntityPlayer player) {
        if (this.field_145850_b.field_72995_K) { return; }

        CommonPacketHandler.INSTANCE.sendTo(getMyUpdatePacket(), (EntityPlayerMP)player);
    }

    private void sendUpdatePacket() {
        if (this.field_145850_b.field_72995_K) { return; }
        if (this.playersWatching.size() <= 0) { return; }

        for (EntityPlayer player : playersWatching) {
            CommonPacketHandler.INSTANCE.sendTo(getMyUpdatePacket(), (EntityPlayerMP)player);
        }
    }

    protected IMessage getMyUpdatePacket() {
        NBTTagCompound childData = new NBTTagCompound();

        onSendUpdate(childData);

        return new DeviceUpdateMessage(field_174879_c.func_177958_n(), field_174879_c.func_177956_o(), field_174879_c.func_177952_p(), childData);
    }

    /**
     * Sets information to send
     * @param nbt
     */
    public void onSendUpdate(NBTTagCompound nbt) {
        NBTTagCompound energyTag = new NBTTagCompound();
        this.energyStorage.writeToNBT(energyTag);
        nbt.func_74782_a("energyStorage", energyTag);
    }

    /**
     * Called on received update packet from server
     * @param nbt
     */
    public void onReceiveUpdate(NBTTagCompound nbt) {
        this.energyStorage.readFromNBT(nbt.func_74775_l("energyStorage"));
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);

        /* IEnergyContainer */
        if (nbt.func_74764_b("energyStorage")) {
            this.energyStorage.readFromNBT(nbt.func_74775_l("energyStorage"));
        }

        /* IInventory */
        final byte NBT_TYPE_COMPOUND = 10;       // See NBTBase.createNewByType() for a listing
        NBTTagList dataForAllSlots = nbt.func_150295_c("Items", NBT_TYPE_COMPOUND);

        itemStack = null;
        NBTTagCompound dataForOneSlot = dataForAllSlots.func_150305_b(0);
        this.itemStack = ItemStack.func_77949_a(dataForOneSlot);
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);

        /* IEnergyContainer */
        NBTTagCompound energyTag = new NBTTagCompound();
        this.energyStorage.writeToNBT(energyTag);
        nbt.func_74782_a("energyStorage", energyTag);

        /* IInventory */
        if (itemStack != null) {
            NBTTagList dataForAllSlots = new NBTTagList();
            NBTTagCompound dataForThisSlot = new NBTTagCompound();
            dataForThisSlot.func_74774_a("Slot", (byte) 0);
            this.itemStack.func_77955_b(dataForThisSlot);
            dataForAllSlots.func_74742_a(dataForThisSlot);

            nbt.func_74782_a("Items", dataForAllSlots);
        }

        return nbt;
    }

    /* ITickable */
    public void func_73660_a() {
        if (!this.func_145830_o()) return;
        World world = this.func_145831_w();
        if (world.field_72995_K) {
            return; // Don't do anything on client side
        }

        ItemStack itemStack = func_70301_a(0);

        // Only run if there are items to burn and energy is not full
        if (itemStack != null && itemStack.field_77994_a > 0 && this.getEnergyStored(null) < this.getMaxEnergyStored(null)) {
            int rfPerItem = 1;

            // munch munch much one stack at a time
            for (int i=0; i<itemStack.field_77994_a; i++) {
                // Break if energy is full or itemStack is depleted
                if (this.getEnergyStored(null) == this.getMaxEnergyStored(null) || itemStack == null) {
                    break;
                }

                // 1 item at a time
                ItemStack removedItemStack = func_70298_a(0, 1);
                if (removedItemStack != null && removedItemStack.field_77994_a == 1) {
                    // EMC support
                    if (Loader.isModLoaded("ProjectE")) {
                        if (ProjectEAPI.getEMCProxy().hasValue(removedItemStack)) {
                            rfPerItem = ProjectEAPI.getEMCProxy().getValue(removedItemStack);
                        }
                    }
                    this.itemIntoEnergy(rfPerItem, false);
                }
            }
        }

        // Distribute power
        if (isActive()) {
            for (EnumFacing direction : EnumFacing.field_82609_l) {
                TileEntity adjacentTile = field_145850_b.func_175625_s(new BlockPos(field_174879_c.func_177958_n() + direction.func_82601_c(), field_174879_c.func_177956_o() + direction.func_96559_d(), field_174879_c.func_177952_p() + direction.func_82599_e()));

                if (adjacentTile instanceof IEnergyReceiver) {
                    IEnergyReceiver handler = (IEnergyReceiver) adjacentTile;
                    energyStorage.extractEnergy(handler.receiveEnergy(direction.func_176734_d(), energyStorage.extractEnergy(energyStorage.getMaxExtract(), true), false), false);
                }
            }
        }

        // Checks for players near tile
        double range = 6.0d;
        List l = field_145850_b.func_72872_a(EntityPlayer.class, new AxisAlignedBB(field_174879_c.func_177958_n()-range, field_174879_c.func_177956_o()-range, field_174879_c.func_177952_p()-range, field_174879_c.func_177958_n()+range, field_174879_c.func_177956_o()+range, field_174879_c.func_177952_p()+range));

        for (int i=0; i<l.size(); i++) {
            EntityPlayerMP player = (EntityPlayerMP)l.get(i);
            beginUpdatingPlayer(player);
        }

        // Removes players out of range
        for (EntityPlayer player : playersWatching) {
            if (player.func_70011_f(field_174879_c.func_177958_n(), field_174879_c.func_177956_o(), field_174879_c.func_177952_p()) > range) {
                stopUpdatingPlayer(player);
            }
        }

        // Send update to players watching
        if (this.playersWatching.size() > 0) {
            ticksSinceLastUpdate++;
            if (ticksSinceLastUpdate >= ticksBetweenUpdates) {
                sendUpdatePacket();
                ticksSinceLastUpdate = 0;
            }
        }
    }

    public boolean isActive() {
        return true;
    }

    /* IWorldNameable */
    @Override
    public String func_70005_c_() {
        return DISPLAY_NAME;
    }

    @Override
    public boolean func_145818_k_() {
        return true;
    }

    @Override
    public ITextComponent func_145748_c_() {
        return (this.func_145818_k_() ? new TextComponentString(this.func_70005_c_()) : new TextComponentTranslation(this.func_70005_c_(), new Object[0]));
    }

    /* IInventory */
    // Gets the number of slots in the inventory
    @Override
    public int func_70302_i_() {
        return NUMBER_OF_SLOTS;
    }

    // Gets the stack in the given slot
    @Override
    public ItemStack func_70301_a(int slotIndex) {
        return itemStack;
    }

    /**
     * Removes some of the units from itemstack in the given slot, and returns as a separate itemstack
     * @param slotIndex the slot number to remove the items from
     * @param count the number of units to remove
     * @return a new itemstack containing the units removed from the slot
     */
    @Override
    public ItemStack func_70298_a(int slotIndex, int count) {
        ItemStack itemStackInSlot = func_70301_a(slotIndex);
        if (itemStackInSlot == null) return null;

        ItemStack itemStackRemoved;
        if (itemStackInSlot.field_77994_a <= count) {
            itemStackRemoved = itemStackInSlot;
            func_70299_a(slotIndex, null);
        } else {
            itemStackRemoved = itemStackInSlot.func_77979_a(count);
            if (itemStackInSlot.field_77994_a == 0) {
                func_70299_a(slotIndex, null);
            }
        }
        func_70296_d();
        return itemStackRemoved;
    }

    // overwrites the stack in the given slotIndex with the given stack
    @Override
    public void func_70299_a(int slotIndex, ItemStack itemstack) {
        itemStack = itemstack;
        if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
            itemstack.field_77994_a = func_70297_j_();
        }
        func_70296_d();
    }

    // This is the maximum number if items allowed in each slot
    // This only affects things such as hoppers trying to insert items you need to use the container to enforce this for players
    // inserting items via the gui
    @Override
    public int func_70297_j_() {
        return 64;
    }

    @Override
    public ItemStack func_70304_b(int slot) {
        return itemStack;
    }

    // Return true if the given player is able to use this block. In this case it checks that
    // 1) the world tileentity hasn't been replaced in the meantime, and
    // 2) the player isn't too far away from the centre of the block
    @Override
    public boolean func_70300_a(EntityPlayer player) {
        if (this.field_145850_b.func_175625_s(this.field_174879_c) != this) return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.func_70092_e(field_174879_c.func_177958_n() + X_CENTRE_OFFSET, field_174879_c.func_177956_o() + Y_CENTRE_OFFSET, field_174879_c.func_177952_p() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }

    // Return true if the given stack is allowed to go in the given slot.  In this case, we can insert anything.
    // This only affects things such as hoppers trying to insert items you need to use the container to enforce this for players
    // inserting items via the gui
    @Override
    public boolean func_94041_b(int slotIndex, ItemStack itemstack) {
        return true;
    }

    @Override
    public void func_174889_b(EntityPlayer player) {}

    @Override
    public void func_174886_c(EntityPlayer player) {}

    @Override
    public int func_174887_a_(int id) {
        return 0;
    }

    @Override
    public void func_174885_b(int id, int value) {}

    @Override
    public int func_174890_g() {
        return 0;
    }

    @Override
    public void func_174888_l() {
        itemStack = null;
    }

    /* IEnergyHandler */
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return 0; // Only receive energy from internal method
        //return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return energyStorage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    /**
     * Used to over write energy level "on block placed"
     * @param newEnergy
     */
    public void setEnergy(int newEnergy) {
        this.energyStorage.modifyEnergyStored(newEnergy);
    }

    public int itemIntoEnergy(int energy, boolean simulate) {
        return energyStorage.receiveEnergy(energy, simulate);
    }

    /**
     * IEMCAcceptor
     */
    @Override
    public double acceptEMC(EnumFacing side, double toAccept) {
        return (double)this.energyStorage.receiveEnergy((int)toAccept, false);
    }

    /**
     * IEMCStorage
     */
    /**
     * Gets the current amount of EMC in this IEMCStorage
     * @return The current EMC stored
     */
    @Override
    public double getStoredEmc() {
        return (double)this.energyStorage.getEnergyStored();
    }

    /**
     * Gets the maximum amount of EMC this IEMCStorage is allowed to contain
     * @return The maximum EMC allowed
     */
    @Override
    public double getMaximumEmc() {
        return (double)this.energyStorage.getMaxEnergyStored();
    }
}
