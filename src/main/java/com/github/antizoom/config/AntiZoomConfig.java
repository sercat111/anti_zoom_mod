package com.github.antizoom.config;

import com.github.antizoom.AntiZoomConstants;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class AntiZoomConfig {
    private static final String SINGLEPLAYER_KEY = "enableSingleplayerWorlds";
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve(AntiZoomConstants.MOD_ID + ".properties");
    private static AntiZoomConfig instance;

    private boolean enableSingleplayerWorlds = true;

    private AntiZoomConfig() {
    }

    public static AntiZoomConfig get() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    public boolean isSingleplayerWorldsEnabled() {
        return enableSingleplayerWorlds;
    }

    public void setSingleplayerWorldsEnabled(boolean enableSingleplayerWorlds) {
        this.enableSingleplayerWorlds = enableSingleplayerWorlds;
    }

    public void save() {
        Properties properties = new Properties();
        properties.setProperty(SINGLEPLAYER_KEY, Boolean.toString(enableSingleplayerWorlds));

        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                properties.store(writer, "Anti Zoom Mod settings");
            }
        } catch (IOException exception) {
            AntiZoomConstants.LOGGER.error("Failed to save Anti Zoom config", exception);
        }
    }

    private static AntiZoomConfig load() {
        AntiZoomConfig config = new AntiZoomConfig();
        if (!Files.exists(CONFIG_PATH)) {
            config.save();
            return config;
        }

        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            properties.load(reader);
            config.enableSingleplayerWorlds = Boolean.parseBoolean(properties.getProperty(SINGLEPLAYER_KEY, "true"));
        } catch (IOException exception) {
            AntiZoomConstants.LOGGER.error("Failed to load Anti Zoom config", exception);
        }
        return config;
    }
}
