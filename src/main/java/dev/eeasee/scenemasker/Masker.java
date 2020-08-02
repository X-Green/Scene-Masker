package dev.eeasee.scenemasker;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Masker implements ModInitializer {

    public static final Identifier MASKER_CHANNEL = new Identifier("eeasee_masker");
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("Scene masker launched! Made by eeasee");
    }
    public static void onServerStarted() {
    }

    public static void onServerClosed() {
    }

    public static void onServerTicked() {
    }

    public static void onConnectedToServer() {
        LOGGER.debug("CStart");
    }

    public static void onDisconnectedToServer() {
        LOGGER.debug("CClose");
    }

    public static void onClientTicked() {
    }

}
