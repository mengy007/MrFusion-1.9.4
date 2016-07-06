package com.techmafia.mcmods.mrfusion.gui.inventory;

import com.techmafia.mcmods.mrfusion.container.ContainerMrFusion;
import com.techmafia.mcmods.mrfusion.tileentity.TileEntityMrFusion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.Random;

/**
 * Created by mengy007 on 1/31/2016.
 */
public class GuiInventoryMrFusion extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation("mrfusion", "textures/gui/mrfusion_gui.png");
    private TileEntityMrFusion tileEntityMrFusion;
    private int energyBarLeft = field_147003_i+152;
    private int energyBarTop = field_147009_r+8;
    private int energyBarWidth = 16;
    private int energyBarHeight = 37;

    public GuiInventoryMrFusion(InventoryPlayer inventoryPlayer, TileEntityMrFusion tileEntityMrFusion) {
        super(new ContainerMrFusion(inventoryPlayer, tileEntityMrFusion));
        this.tileEntityMrFusion = tileEntityMrFusion;

        field_146999_f = 176;
        field_147000_g = 133;
    }

    // draw the background for the GUI - rendered first
    @Override
    protected void func_146976_a(float partialTicks, int x, int y) {
        // Bind the image texture of our custom container
        Minecraft.func_71410_x().func_110434_K().func_110577_a(texture);
        // Draw the image
        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
        func_73729_b(field_147003_i, field_147009_r, 0, 0, field_146999_f, field_147000_g);

        // Draw energy bar
        int max = tileEntityMrFusion.getMaxEnergyStored(null);
        int energy = tileEntityMrFusion.getEnergyStored(null);

        int eyOffset = (int)(energyBarHeight - (((double)energy/(double)max)*energyBarHeight));

        func_73729_b(field_147003_i+energyBarLeft, field_147009_r+energyBarTop+eyOffset, field_146999_f, eyOffset, energyBarWidth, energyBarHeight);
    }

    // draw the foreground for the GUI - rendered after the slots, but before the dragged items and tooltips
    // renders relative to the top left corner of the background
    @Override
    protected void func_146979_b(int mouseX, int mouseY) {
        final int LABEL_XPOS = 5;
        final int LABEL_YPOS = 5;

        int max = tileEntityMrFusion.getMaxEnergyStored(null);
        int energy = tileEntityMrFusion.getEnergyStored(null);

        field_146289_q.func_78276_b(tileEntityMrFusion.func_145748_c_().func_150260_c(), LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());

        if (mouseX >= (energyBarLeft+field_147003_i) && mouseX <= (energyBarLeft+energyBarWidth+field_147003_i) && mouseY >= (energyBarTop+field_147009_r) && mouseY <= (energyBarTop+energyBarHeight+field_147009_r)) {
            String energyString = energy + " / " + max + " RF";

            func_146279_a(energyString, mouseX - field_147003_i, mouseY - field_147009_r);
        }
    }
}
