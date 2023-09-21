package org.pokesplash.hunt.forge;

import net.minecraftforge.fml.common.Mod;
import org.pokesplash.hunt.Hunt;

@Mod(Hunt.MOD_ID)
public class HuntForge {
    public HuntForge() {
        Hunt.init();
    }
}