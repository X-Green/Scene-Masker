package dev.eeasee.scenemasker.client.configs;

import dev.eeasee.scenemasker.Masker;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.util.StringUtils;

import java.util.List;

public class GuiConfig extends GuiConfigsBase {
    private static Configs.Category currentTab = Configs.Category.VISUAL;
    int ticks;

    public GuiConfig() {
        super(10, 50, Masker.MOD_ID, null, "eeaseemod.gui.setting_screen");
        this.ticks = 0;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();
        int x = 10, y = 26;
        for (Configs.Category category : Configs.Category.values()) {
            ButtonGeneric tabButton = new TabButton(category, x, y, -1, 20, StringUtils.translate(category.getKey()));
            tabButton.setEnabled(true);
            this.addButton(tabButton, (buttonBase, i) -> {
                currentTab = ((TabButton)buttonBase).category;
                this.reCreateListWidget();
                this.getListWidget().resetScrollbarPosition();
                this.initGui();
            });
            x += tabButton.getWidth() + 2;
        }
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        return ConfigOptionWrapper.createFor(currentTab.getConfigs());
    }

    public class TabButton extends ButtonGeneric {
        private final Configs.Category category;
        public TabButton(Configs.Category category, int x, int y, int width, int height, String text, String... hoverStrings) {
            super(x, y, width, height, text, hoverStrings);
            this.category = category;
        }
    }
}
