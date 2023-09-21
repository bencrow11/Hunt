package org.pokesplash.hunt.fabric;

import net.fabricmc.api.ModInitializer;
import org.pokesplash.hunt.Hunt;

public class HuntFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Hunt.init();
    }
}