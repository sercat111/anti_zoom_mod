package com.github.antizoom.client.config;

import com.github.antizoom.config.AntiZoomConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class AntiZoomConfigScreen extends Screen {
    private final Screen parent;
    private final AntiZoomConfig config;

    public AntiZoomConfigScreen(Screen parent) {
        super(Text.translatable("text.anti_zoom_mod.config.title"));
        this.parent = parent;
        this.config = AntiZoomConfig.get();
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        addDrawableChild(ButtonWidget.builder(singleplayerButtonText(), button -> {
            config.setSingleplayerWorldsEnabled(!config.isSingleplayerWorldsEnabled());
            button.setMessage(singleplayerButtonText());
        }).dimensions(centerX - 155, height / 2 - 10, 310, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> close())
                .dimensions(centerX - 100, height / 2 + 20, 200, 20)
                .build());
    }

    @Override
    public void close() {
        config.save();
        client.setScreen(parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, height / 2 - 45, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("text.anti_zoom_mod.config.singleplayer.hint"), width / 2, height / 2 - 30, 0xA0A0A0);
    }

    private Text singleplayerButtonText() {
        return Text.translatable("text.anti_zoom_mod.config.singleplayer",
                Text.translatable(config.isSingleplayerWorldsEnabled() ? "options.on" : "options.off"));
    }
}
