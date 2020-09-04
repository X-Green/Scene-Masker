package dev.eeasee.scenemasker.compat;

import dev.eeasee.scenemasker.Masker;
import dev.eeasee.scenemasker.client.configs.GuiConfig;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;

import java.util.function.Function;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public String getModId() {
        return Masker.MOD_ID;
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return (screen) -> {
            GuiConfig gui = new GuiConfig();
            gui.setParent(screen);
            return gui;
        };
    }
}
