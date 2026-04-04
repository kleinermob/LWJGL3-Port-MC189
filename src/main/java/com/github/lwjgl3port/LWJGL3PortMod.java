package com.github.lwjgl3port;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "lwjgl3port", name = "LWJGL 3 Port", version = "1.0.0", clientSideOnly = true)
public class LWJGL3PortMod {
    public static final Logger LOGGER = LogManager.getLogger("LWJGL3Port");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("LWJGL 3 Port initialized — Display, Keyboard, and Mouse are now backed by GLFW.");
    }
}
