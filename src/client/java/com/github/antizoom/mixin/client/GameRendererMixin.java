package com.github.antizoom.mixin.client;

import com.github.antizoom.client.WeaponZoomBlocker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GameRenderer.class, priority = 200)
public abstract class GameRendererMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    public abstract float getFov(Camera camera, float tickProgress, boolean changingFov);

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true, require = 0)
    private void antiZoomMod$cancelWeaponZoom(Camera camera, float tickProgress, boolean changingFov,
                                              CallbackInfoReturnable<Float> cir) {
        if (!(client.getCameraEntity() instanceof PlayerEntity player)) {
            return;
        }

        double baselineFov = WeaponZoomBlocker.getBaselineFov(client.options.getFov().getValue(), player, tickProgress, changingFov);
        double returnedFov = cir.getReturnValue();
        if (WeaponZoomBlocker.shouldCancelZoom(player, baselineFov, returnedFov)) {
            cir.setReturnValue((float) baselineFov);
        }
    }
}
