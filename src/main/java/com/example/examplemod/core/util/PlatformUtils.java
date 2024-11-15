package com.example.examplemod.core.util;

import net.neoforged.fml.loading.FMLEnvironment;

public class PlatformUtils {

    public static boolean isDevelopmentEnvironment () {
        return !FMLEnvironment.production;
    }

}
