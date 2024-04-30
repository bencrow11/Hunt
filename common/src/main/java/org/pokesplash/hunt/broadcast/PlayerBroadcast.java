package org.pokesplash.hunt.broadcast;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class PlayerBroadcast {
    public HashMap<BroadcastType, Long> buffers; // The broadcast buffers.

    public PlayerBroadcast() {
        buffers = new HashMap<>();
    }

}
