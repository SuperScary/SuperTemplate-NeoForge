package com.example.examplemod.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = com.example.examplemod.core.Mod.MOD_ID, dist = Dist.CLIENT)
public class ModClient extends ModBase {

    public ModClient (IEventBus eventBus) {
        super(eventBus);
    }

    @Override
    public Level getClientLevel () {
        return Minecraft.getInstance().level;
    }

}
