package com.techmafia.mcmods.mrfusion.net.messages;

//import cofh.api.tileentity.IReconfigurableFacing;
import com.techmafia.mcmods.mrfusion.net.messages.base.WorldMessageClient;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Meng on 8/1/2015.
 */
public class DeviceUpdateRotationMessage extends WorldMessageClient {
    private int newOrientation;

    public DeviceUpdateRotationMessage() { super(); newOrientation = 0; }

    public DeviceUpdateRotationMessage(int x, int y, int z, int newOrientation) {
        super(x, y, z);
        this.newOrientation = newOrientation;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        newOrientation = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(newOrientation);
    }

    public static class Handler extends WorldMessageClient.Handler<DeviceUpdateRotationMessage> {
        @Override
        public IMessage handleMessage(DeviceUpdateRotationMessage message, MessageContext ctx, TileEntity te) {
            //if(te instanceof IReconfigurableFacing) {
            if (true) {
                //((IReconfigurableFacing)te).setFacing(message.newOrientation);
                //BlockPos blockPos = new BlockPos(message.x, message.y, message.z);
                //getWorld(ctx).markBlockForUpdate(blockPos);
                te.markDirty();
            }
            return null;
        }
    }
}
