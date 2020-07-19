package dev.eeasee.scenemasker;

import dev.eeasee.scenemasker.masker.SidedMasker;
import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class Masker {
    public static final Identifier MASKER_CHANNEL = new Identifier("eeasee_masker");

    public static SidedMasker masker_server;

    public static SidedMasker masker_client;

    public static void onServerStarted() {
        masker_server = new SidedMasker(SidedMasker.Side.SERVER);
    }

    public static void onServerClosed() {
        masker_server = null;
    }

    public static void onConnectedToServer() {
        masker_client = new SidedMasker(SidedMasker.Side.CLIENT);
    }

    public static void onDisconnectedToServer() {
        masker_client = null;
    }
}
