package cn.zimzaza4.slimefunzt.machines;

import org.antlr.v4.runtime.misc.NotNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;

public class AutoSoulCrafter extends AContainer implements RecipeDisplayItem {
    public AutoSoulCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.IRON_BLOCK);
    }


    @NotNull
    @Override
    public String getMachineIdentifier() {
        return "ZIM_AUTO_CRAFTER";
    }
}