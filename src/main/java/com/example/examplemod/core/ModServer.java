package com.example.examplemod.core;

import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = com.example.examplemod.core.Mod.MOD_ID, dist = Dist.DEDICATED_SERVER)
public class ModServer extends ModBase {

    public ModServer (IEventBus eventBus) {
        super(eventBus);
    }

    @Override
    public Level getClientLevel () {
        return null;
    }

}
