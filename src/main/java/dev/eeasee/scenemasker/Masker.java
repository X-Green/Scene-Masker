package dev.eeasee.scenemasker;

import dev.eeasee.scenemasker.masker.SidedMasker;
import net.minecraft.util.Identifier;

public class Masker {
    public static final Identifier MASKER_CHANNEL = new Identifier("eeasee_masker");

    public static SidedMasker masker_server;

    public static SidedMasker masker_client;

    public static void onServerStarted() {
        masker_server = new SidedMasker(SidedMasker.Side.SERVER);
        System.out.println("SStart");
    }

    public static void onServerClosed() {
        masker_server = null;
        System.out.println("SClose");
    }

    public static void onConnectedToServer() {
        masker_client = new SidedMasker(SidedMasker.Side.CLIENT);
        System.out.println("CStart");
    }

    public static void onDisconnectedToServer() {
        masker_client = null;
        System.out.println("CClose");
    }
}
