package cn.zimzaza4.slimefunzt.World;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockTypes;

import cn.zimzaza4.slimefunzt.SlimefunZT;
import cn.zimzaza4.slimefunzt.lists.Items;
import cn.zimzaza4.slimefunzt.util.SchematicUtil;
import lombok.SneakyThrows;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

public class VoidPopulator extends BlockPopulator {

    @SneakyThrows
    @Override
    public void populate(@NotNull World world, @NotNull Random random, @NotNull Chunk chunk) {
        

        try (EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder()
        		.world(BukkitAdapter.adapt(world))
                .allowedRegionsEverywhere() // 允许任何区域
                .limitUnlimited() // 解除限制
                .changeSetNull() // 不记录变化
                .fastMode(true) // 禁用快速模式（true = 无物理/粒子，false = 有物理/粒子）
                .build()) {
        	
        	Double canspawn = Math.random();
            int x = random.nextInt(16);
            int y = random.nextInt(10);
            int z = random.nextInt(16);
            Block chunkore1 = chunk.getBlock(x, y, z);
            BlockVector3 position = BlockVector3.at(x, y, z);
        	if (canspawn > 0.96) {
        		editSession.setBlock(position, BlockTypes.PURPLE_STAINED_GLASS);
                //chunkore1.setType(Material.PURPLE_STAINED_GLASS);
                BlockStorage.addBlockInfo(chunkore1.getLocation(), "id", Items.Void_Ore_Ame.getItemId(), true);
            }

            x = random.nextInt(16);
            y = random.nextInt(150);
            z = random.nextInt(16);
            if (y > 70) {
                //Block chunkblock1 = chunk.getBlock(x, y, z);
                position = BlockVector3.at(x, y, z);
                if (editSession.getBlock(position).getBlockType() == BlockTypes.AIR) {
                	
                    editSession.setBlock(position, BlockTypes.GLOWSTONE);
                    //chunkblock1.setType(Material.GLOWSTONE);
                    
                    position = BlockVector3.at(x + 1, y, z);
                    editSession.setBlock(position, BlockTypes.BLACK_STAINED_GLASS);
                    position = BlockVector3.at(x, y, z + 1);
                    editSession.setBlock(position, BlockTypes.BLACK_STAINED_GLASS);
                    position = BlockVector3.at(x - 1, y, z);
                    editSession.setBlock(position, BlockTypes.BLACK_STAINED_GLASS);
                    position = BlockVector3.at(x, y, z - 1);
                    editSession.setBlock(position, BlockTypes.BLACK_STAINED_GLASS);
                    
                    /*
                    chunkblock1.getLocation().add(1, 0, 0).getBlock().setType(Material.BLACK_STAINED_GLASS);
                    chunkblock1.getLocation().add(0, 0, 1).getBlock().setType(Material.BLACK_STAINED_GLASS);
                    chunkblock1.getLocation().add(-1, 0, 0).getBlock().setType(Material.BLACK_STAINED_GLASS);
                    chunkblock1.getLocation().add(0, 0, -1).getBlock().setType(Material.BLACK_STAINED_GLASS);
                    */
                }
            }

            canspawn = Math.random();
            x = random.nextInt(16);
            y = random.nextInt(70);
            z = random.nextInt(16);
            for (int i = 0; i < 9; i++) {
            	position = BlockVector3.at(x, y, z);
                if (editSession.getBlock(position).getBlockType() == BlockTypes.BLACKSTONE) {
                    editSession.setBlock(position, BlockTypes.DEEPSLATE_IRON_ORE);
                    
                    //Block chunkblockst = chunk.getBlock(x, y, z);
                    //chunkblockst.setType(Material.DEEPSLATE_IRON_ORE);

                }
            }
            for (int i = 0; i < 17; i++) {

                x = random.nextInt(16);
                z = random.nextInt(16);

                for (y = 180; y > 170; y--) {

                	position = BlockVector3.at(x, y, z);
                    if (editSession.getBlock(position).getBlockType() == BlockTypes.GRASS_BLOCK) {
                    	editSession.setBlock(position, BlockTypes.SHORT_GRASS);
                        break;
                    }
                }
                for (y = 79; y > 70; y--) {

                	position = BlockVector3.at(x, y, z);
                    if (editSession.getBlock(position).getBlockType() == BlockTypes.STONE) {
                        Block b = chunk.getBlock(x, y + 1, z);




                        Clipboard cb = null;


                        Double rd = random.nextDouble();

                        if (rd > 0.99) {

                            cb = SlimefunZT.getInstance().void_tree_large;
                        } else if (rd > 0.93) {

                            cb = SlimefunZT.getInstance().void_tree;

                        } else if (rd > 0.90) {
                            cb = SlimefunZT.getInstance().stone_2;
                        } else if (rd > 0.87) {
                            cb = SlimefunZT.getInstance().stone_1;
                        }
                        if (SlimefunZT.inst.isDebug) {
                            System.out.println(rd);
                            System.out.println("Spawned?");
                        }
                        if (cb!=null) {
                            Location loc = b.getLocation();


                            try {
    							SchematicUtil.SpawnSchmatic(cb, loc);
    						} catch (WorldEditException e) {
    							e.printStackTrace();
    						}

                        }
                    }
                }
            }
            
        	editSession.flushQueue();
		} catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("批量设置方块失败", e);
        }
        
    }
}
