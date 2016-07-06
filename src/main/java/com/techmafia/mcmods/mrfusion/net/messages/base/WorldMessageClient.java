package com.techmafia.mcmods.mrfusion.net.messages.base;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.world.World;

/**
 * Created by Meng on 7/30/2015.
 */
public abstract class WorldMessageClient extends WorldMessage {
    protected WorldMessageClient() { super(); }
    protected WorldMessageClient(int x, int y, int z) {
        super(x, y, z);
    }

    public abstract static class Handler<M extends WorldMessageClient> extends WorldMessage.Handler<M> {
        @SideOnly(Side.CLIENT)
        @Override
        protected World getWorld(MessageContext ctx) {
            return FMLClientHandler.instance().getClient().theWorld;
        }
    }
}
