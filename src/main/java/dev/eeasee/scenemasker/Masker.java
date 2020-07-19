package dev.eeasee.scenemasker;

import dev.eeasee.scenemasker.masker.SidedMasker;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Masker {
    public static final Identifier MASKER_CHANNEL = new Identifier("eeasee_masker");
    public static final Logger LOGGER = LogManager.getLogger();

    public static SidedMasker masker_server;

    public static SidedMasker masker_client;

    public static void onServerStarted() {
        masker_server = new SidedMasker(SidedMasker.Side.SERVER);
        LOGGER.debug("SStart");
    }

    public static void onServerClosed() {
        masker_server = null;
        LOGGER.debug("SClose");
    }

    public static void onConnectedToServer() {
        masker_client = new SidedMasker(SidedMasker.Side.CLIENT);
        LOGGER.debug("CStart");
    }

    public static void onDisconnectedToServer() {
        masker_client = null;
        LOGGER.debug("CClose");
    }
}
