package org.pokesplash.hunt.broadcast;

import java.util.HashMap;

public class PlayerBroadcast {
    public HashMap<BroadcastType, Long> buffers; // The broadcast buffers.

    public PlayerBroadcast() {
        buffers = new HashMap<>();
    }

}
