package dev.eeasee.scenemasker;

import dev.eeasee.scenemasker.command.MaskerCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Masker implements ModInitializer {

    public static final String MOD_ID = "eeasee_masker";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("Scene masker launched! Made by eeasee");
        registerCommands();
    }

    private void registerCommands() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> MaskerCommand.register(dispatcher)));
    }
}
