package cn.zimzaza4.slimefunzt.items.gear;

import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.items.armor.SlimefunArmorPiece;

public class SoulInfinity extends SlimefunArmorPiece {
    public SoulInfinity(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, @Nullable PotionEffect[] effects) {
        super(category, item, recipeType, recipe, effects);

    }

}
