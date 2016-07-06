package com.techmafia.mcmods.mrfusion.container;

import com.techmafia.mcmods.mrfusion.tileentity.TileEntityMrFusion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by mengy007 on 1/31/2016.
 */
public class ContainerMrFusion extends Container {
    private TileEntityMrFusion tileEntityMrFusion;
    private final int HOTBAR_SLOT_COUNT = 9;
    private final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private final int VANILLA_FIRST_SLOT_INDEX = 0;
    private final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private final int TE_INVENTORY_SLOT_COUNT = 1;

    public ContainerMrFusion(InventoryPlayer inventoryPlayer, TileEntityMrFusion tileEntityMrFusion) {
        this.tileEntityMrFusion = tileEntityMrFusion;

        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;
        final int HOTBAR_XPOS = 8;
        final int HOTBAR_YPOS = 109;

        // Add player hotbar to gui (xpos, ypos) location of each item
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            int slotNumber = x;
            func_75146_a(new Slot(inventoryPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        final int PLAYER_INVENTORY_XPOS = 8;
        final int PLAYER_INVENTORY_YPOS = 51;
        // Add rest of player inventory to gui
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                func_75146_a(new Slot(inventoryPlayer, slotNumber,  xpos, ypos));
            }
        }

        // Add inventory slot
        func_75146_a(new Slot(tileEntityMrFusion, 0, 8+(4*SLOT_X_SPACING), 20));

        // Add player to get tile updates
        tileEntityMrFusion.beginUpdatingPlayer(inventoryPlayer.field_70458_d);
    }

    // Vanilla calls this method every tick to make sure the player is still able to access the inventory, and if not closes the gui

    @Override
    public boolean func_75145_c(EntityPlayer player)
    {
        return tileEntityMrFusion.func_70300_a(player);
    }

    // This is where you specify what happens when a player shift clicks a slot in the gui
    //  (when you shift click a slot in the TileEntity Inventory, it moves it to the first available position in the hotbar and/or
    //    player inventory.  When you you shift-click a hotbar or player inventory item, it moves it to the first available
    //    position in the TileEntity inventory)
    // At the very least you must override this and return null or the game will crash when the player shift clicks a slot
    // returns null if the source slot is empty, or if none of the the source slot items could be moved
    //   otherwise, returns a copy of the source stack
    @Override
    public ItemStack func_82846_b(EntityPlayer player, int sourceSlotIndex)
    {
        Slot sourceSlot = (Slot)field_75151_b.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.func_75216_d()) return null;
        ItemStack sourceStack = sourceSlot.func_75211_c();
        ItemStack copyOfSourceStack = sourceStack.func_77946_l();

        // Check if the slot clicked is one of the vanilla container slots
        if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!func_75135_a(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)){
                return null;
            }
        } else if (sourceSlotIndex >= TE_INVENTORY_FIRST_SLOT_INDEX && sourceSlotIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!func_75135_a(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return null;
            }
        } else {
            System.err.print("Invalid slotIndex:" + sourceSlotIndex);
            return null;
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.field_77994_a == 0) {
            sourceSlot.func_75215_d(null);
        } else {
            sourceSlot.func_75218_e();
        }

        sourceSlot.func_82870_a(player, sourceStack);
        return copyOfSourceStack;
    }

    // pass the close container message to the tileEntityInventory (not strictly needed for this example)
    //  see ContainerChest and TileEntityChest
    @Override
    public void func_75134_a(EntityPlayer playerIn)
    {
        super.func_75134_a(playerIn);
        this.tileEntityMrFusion.func_174886_c(playerIn);

        // Stop sending tile updates to player
        this.tileEntityMrFusion.stopUpdatingPlayer(playerIn);
    }
}
