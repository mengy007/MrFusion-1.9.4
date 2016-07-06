package com.techmafia.mcmods.mrfusion.net.messages;

import com.techmafia.mcmods.mrfusion.net.messages.base.WorldMessageClient;
import com.techmafia.mcmods.mrfusion.tileentity.TileEntityMrFusion;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Meng on 7/30/2015.
 */
public class DeviceUpdateMessage extends WorldMessageClient {
    private NBTTagCompound compound;

    public DeviceUpdateMessage() { super(); compound = null; }

    public DeviceUpdateMessage(int x, int y, int z, NBTTagCompound compound) {
        super(x, y, z);
        this.compound = compound;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        ByteBufUtils.writeTag(buf, compound);
    }

    public static class Handler extends WorldMessageClient.Handler<DeviceUpdateMessage> {
        @Override
        public IMessage handleMessage(DeviceUpdateMessage message, MessageContext ctx, TileEntity te) {

            if (te instanceof TileEntityMrFusion) {
                ((TileEntityMrFusion)te).onReceiveUpdate(message.compound);
            }

            return null;
        }
    }
}
