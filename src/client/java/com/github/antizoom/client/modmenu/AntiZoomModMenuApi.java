package com.github.antizoom.client.modmenu;

import com.github.antizoom.client.config.AntiZoomConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public final class AntiZoomModMenuApi implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return AntiZoomConfigScreen::new;
    }
}
