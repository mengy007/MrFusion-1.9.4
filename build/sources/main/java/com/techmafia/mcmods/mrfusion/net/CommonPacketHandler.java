package com.techmafia.mcmods.mrfusion.net;

import com.techmafia.mcmods.mrfusion.net.messages.DeviceUpdateMessage;
import com.techmafia.mcmods.mrfusion.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Meng on 7/30/2015.
 */
public class CommonPacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.CHANNEL.toLowerCase());

    /**
     * Initialize the messages. Note that all messages (server>client and client>server)
     * must be initialized on _both_ the client and the server.
     */
    // Be careful not to reference any client code in your message handlers, such as WorldClient!
    public static void init() {
        // Server >> Client Messages
        INSTANCE.registerMessage(DeviceUpdateMessage.Handler.class, DeviceUpdateMessage.class, 1, Side.CLIENT);
    }
}
