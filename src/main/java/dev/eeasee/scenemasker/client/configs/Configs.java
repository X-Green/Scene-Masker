package dev.eeasee.scenemasker.client.configs;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigDouble;

import java.util.ArrayList;
import java.util.List;

public class Configs {

    static {

    }

    public enum Category {
        VISUAL("Visual");

        private final String key;
        private final List<IConfigBase> configs;

        private Category(String key) {
            this.key = key;
            configs = new ArrayList<>();
        }

        protected <T extends IConfigBase> T add(T config) {
            this.configs.add(config);
            return config;
        }

        public List<IConfigBase> getConfigs() {
            return ImmutableList.copyOf(this.configs);
        }

        public String getKey() {
            return this.key;
        }
    }

}
