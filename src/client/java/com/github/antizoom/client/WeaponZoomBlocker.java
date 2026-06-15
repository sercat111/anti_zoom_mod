package com.github.antizoom.client;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Locale;

public final class WeaponZoomBlocker {
    private static final double ZOOM_EPSILON = 0.001D;

    private WeaponZoomBlocker() {
    }

    public static boolean shouldCancelZoom(PlayerEntity player, double vanillaFov, double returnedFov) {
        if (player == null || returnedFov >= vanillaFov - ZOOM_EPSILON) {
            return false;
        }

        ItemStack stack = getWeaponStack(player);
        return !stack.isEmpty() && isProtectedWeapon(stack);
    }

    public static double getBaselineFov(double fov, PlayerEntity player, float tickProgress, boolean changingFov) {
        double baseline = fov;
        if (player.isSubmergedInWater()) {
            baseline *= 0.8571428571428571D;
        }
        return baseline;
    }

    private static ItemStack getWeaponStack(PlayerEntity player) {
        ItemStack mainHand = player.getMainHandStack();
        if (isProtectedWeapon(mainHand)) {
            return mainHand;
        }

        ItemStack offHand = player.getOffHandStack();
        return isProtectedWeapon(offHand) ? offHand : ItemStack.EMPTY;
    }

    private static boolean isProtectedWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        Item item = stack.getItem();
        if (isSwordId(Registries.ITEM.getId(item)) || item instanceof AxeItem || item instanceof TridentItem) {
            return true;
        }

        if (item instanceof BowItem || item instanceof CrossbowItem) {
            return false;
        }

        Identifier id = Registries.ITEM.getId(item);
        if (isPointBlankWeaponId(id)) {
            return true;
        }

        AttributeModifiersComponent modifiers = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        return modifiers != null && modifiers.modifiers().stream().anyMatch(entry ->
                entry.attribute().equals(EntityAttributes.ATTACK_DAMAGE)
                        && entry.slot().matches(EquipmentSlot.MAINHAND)
                        && entry.modifier().value() > 0.0D);
    }

    private static boolean isSwordId(Identifier id) {
        String path = id.getPath().toLowerCase(Locale.ROOT);
        return path.endsWith("_sword") || path.equals("sword");
    }

    private static boolean isPointBlankWeaponId(Identifier id) {
        String namespace = id.getNamespace().toLowerCase(Locale.ROOT);
        String path = id.getPath().toLowerCase(Locale.ROOT);
        return namespace.contains("pointblank")
                || namespace.contains("vic")
                || namespace.contains("vics")
                || path.contains("point_blank")
                || path.contains("pointblank")
                || path.contains("gun")
                || path.contains("rifle")
                || path.contains("pistol")
                || path.contains("deagle")
                || path.contains("revolver")
                || path.contains("sniper")
                || path.contains("shotgun");
    }
}
