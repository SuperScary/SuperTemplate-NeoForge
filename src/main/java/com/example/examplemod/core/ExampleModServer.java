package com.example.examplemod.core;

import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = ExampleMod.MOD_ID, dist = Dist.DEDICATED_SERVER)
public class ExampleModServer extends ExampleModBase {

    public ExampleModServer (IEventBus eventBus) {
        super(eventBus);
    }

    @Override
    public Level getClientLevel () {
        return null;
    }

}
