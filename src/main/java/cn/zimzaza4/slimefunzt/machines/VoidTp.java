package cn.zimzaza4.slimefunzt.machines;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import cn.zimzaza4.slimefunzt.SlimefunZT;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;


public class VoidTp extends SlimefunItem {

    public VoidTp(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
        addItemHandler(new BlockUseHandler() {
            @Override
            public void onRightClick(PlayerRightClickEvent playerRightClickEvent) {
                Optional<Block> block = playerRightClickEvent.getClickedBlock();
                if (block.isEmpty()) {
                    return;
                }

                Location location = block.get().getLocation();
                if (BlockStorage.getLocationInfo(location, "void_tp_is_open") != null) {
                    Player player = playerRightClickEvent.getPlayer();
                    Location loc = player.getLocation();
                    loc.setWorld(Bukkit.getWorld(SlimefunZT.inst.void_world));
                    loc.setY(255);
                    player.teleport(loc);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 600, 0));
                }

            }
        });
    }
}
