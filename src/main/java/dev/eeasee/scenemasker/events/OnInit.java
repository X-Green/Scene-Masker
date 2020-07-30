package dev.eeasee.scenemasker.events;

import dev.eeasee.scenemasker.Masker;
import net.fabricmc.api.ModInitializer;

public class OnInit implements ModInitializer {
    @Override
    public void onInitialize() {
        Masker.LOGGER.info("Scene masker launched! Made by eeasee");
    }
}
