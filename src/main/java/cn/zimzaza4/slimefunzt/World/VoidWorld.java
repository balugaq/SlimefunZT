package cn.zimzaza4.slimefunzt.World;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;

public class VoidWorld extends ChunkGenerator {
    private SimplexOctaveGenerator noise;
    private SimplexOctaveGenerator fognoise;
    public SimplexOctaveGenerator biomenoise;
    private SimplexOctaveGenerator landnoise;
    private SimplexOctaveGenerator webnoise;
    /*
    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData data = createChunkData(world);
        data.setRegion(0, 255,0, 16,256, 16, Material.BEDROCK);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biome.setBiome(i, j, Biome.THE_VOID);
            }
        }
        if (noise==null){

            noise = new SimplexOctaveGenerator(world.getSeed(), 6);
            noise.setScale(0.005D);

        }
        if (fognoise==null) {
            fognoise = new SimplexOctaveGenerator(world.getSeed() + 193873, 1);
            fognoise.setScale(0.02D);

        }
        if (landnoise==null) {
            landnoise = new SimplexOctaveGenerator(world.getSeed()+19283737, 6);
            landnoise.setScale(0.005D);

        }
        if (webnoise==null) {
            webnoise = new SimplexOctaveGenerator(world.getSeed(), 1);
            webnoise.setScale(0.2D);
        }

        if (biomenoise==null){
            biomenoise = new SimplexOctaveGenerator(world.getSeed()+6, 1);
            biomenoise.setScale(0.005D);

        }




        for (int x1 = 0; x1 < 16; x1++) {

            for (int z1 = 0; z1 < 16; z1++){

                int realX = x * 16 + x1;
                int realZ = z * 16 + z1;
                if ((int)(biomenoise.noise(realX, realZ, 1.5D, 0.9D))<-0.5) {

                    double noiseValue = noise.noise(realX, realZ, 1.5D, 0.5D);

                    int height = (int) (noiseValue * 40 + 100);

                    if (height < 76) {

                        for (int y = 70; y > height - 6; y--) {


                            data.setBlock(x1, y, z1, Material.BLACKSTONE);
                        }

                        int Fh = 9 - height / 8;

                        for (int y = 71; y < Fh + 71; y++) {
                            data.setBlock(x1, y, z1, Material.DEAD_BRAIN_CORAL_BLOCK);
                        }
                        data.setBlock(x1, Fh + 70, z1, Material.STONE);

                    }
                }else {
                    double noiseValue = noise.noise(realX, realZ, 1.5D, 0.5D);

                    int height = (int) (noiseValue * 40 + 100);

                    if (height < 80) {

                        for (int y = 70; y > height - 10; y--) {


                            data.setBlock(x1, y, z1, Material.OBSIDIAN);
                        }

                        int Fh = 9 - height / 8;

                        for (int y = 71; y < Fh + 71; y++) {
                            data.setBlock(x1, y, z1, Material.OBSIDIAN);
                        }
                        data.setBlock(x1, Fh + 70, z1, Material.CRYING_OBSIDIAN);
                    }
                }
                double noiseValue2 = landnoise.noise(realX, realZ ,1.5D, 0.5D);
                int heig = (int) (noiseValue2 * 40 + 100);
                if (heig<75) {
                int Fh = 9-heig/8;
                    for (int y =170; y > heig+95 ; y--) {
                        data.setBlock(x1, y, z1, Material.STONE);
                    }
                    for (int y = 171; y< Fh+171;y++) {
                        data.setBlock(x1, y, z1, Material.DIRT);
                    }
                    data.setBlock(x1, Fh+170, z1,Material.GRASS_BLOCK);


                }

                double noiseV = fognoise.noise(realX, realZ, 1D, 0.5D);

                int fogr = (int) (noiseV * 40D + 100D);
                if (fogr > 120){

                    data.setBlock(x1, 151, z1, Material.GRAY_STAINED_GLASS);
                }

                double noiseweb = webnoise.noise(realX, realZ, 3D, 0.5D);
                if (noiseweb*40 >20){
                    data.setBlock(x1, 230, z1, Material.WHITE_STAINED_GLASS);
                }

            }
        }

        return data;
    }
    */
    
    // 缓存常用高度值
    private static final int CHUNK_SIZE = 16;
    private static final int WORLD_HEIGHT = 256;
    
    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, @NotNull BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);
        
        generateTerrainOptimized(world, random, chunkX, chunkZ, biome);
        
        return chunkData;
    }
    
    private void generateTerrainOptimized(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        try (EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder()
                .world(BukkitAdapter.adapt(world))
                .allowedRegionsEverywhere()
                .limitUnlimited()
                .changeSetNull()
                .fastMode(true)
                .build()) {
            
            initializeNoiseGenerators(world);
            
            int startX = chunkX * CHUNK_SIZE;
            int startZ = chunkZ * CHUNK_SIZE;
            
            // 预计算所有高度和类型
            HeightData[][] heightData = new HeightData[CHUNK_SIZE][CHUNK_SIZE];
            
            // 第一遍：计算所有高度
            for (int x = 0; x < CHUNK_SIZE; x++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    int realX = startX + x;
                    int realZ = startZ + z;
                    
                    HeightData data = new HeightData();
                    data.biomeType = biomenoise.noise(realX, realZ, 1.5D, 0.9D) < -0.5 ? 0 : 1;
                    
                    if (data.biomeType == 0) {
                        double noiseValue = noise.noise(realX, realZ, 1.5D, 0.5D);
                        data.height = (int) (noiseValue * 40 + 100);
                        data.fh = 9 - data.height / 8;
                    } else {
                        double noiseValue = noise.noise(realX, realZ, 1.5D, 0.5D);
                        data.height = (int) (noiseValue * 40 + 100);
                        data.fh = 9 - data.height / 8;
                    }
                    
                    double landNoiseValue = landnoise.noise(realX, realZ, 1.5D, 0.5D);
                    data.landHeight = (int) (landNoiseValue * 40 + 100);
                    data.landFh = 9 - data.landHeight / 8;
                    
                    data.fogHeight = (int) (fognoise.noise(realX, realZ, 1D, 0.5D) * 40D + 100D);
                    data.webValue = webnoise.noise(realX, realZ, 3D, 0.5D) * 40;
                    
                    heightData[x][z] = data;
                }
            }
            
            // 第二遍：设置方块
            BlockState bedrockState = BukkitAdapter.adapt(Material.BEDROCK.createBlockData());
            BlockState blackstoneState = BukkitAdapter.adapt(Material.BLACKSTONE.createBlockData());
            BlockState deadBrainCoralState = BukkitAdapter.adapt(Material.DEAD_BRAIN_CORAL_BLOCK.createBlockData());
            BlockState stoneState = BukkitAdapter.adapt(Material.STONE.createBlockData());
            BlockState obsidianState = BukkitAdapter.adapt(Material.OBSIDIAN.createBlockData());
            BlockState cryingObsidianState = BukkitAdapter.adapt(Material.CRYING_OBSIDIAN.createBlockData());
            BlockState dirtState = BukkitAdapter.adapt(Material.DIRT.createBlockData());
            BlockState grassBlockState = BukkitAdapter.adapt(Material.GRASS_BLOCK.createBlockData());
            BlockState grayGlassState = BukkitAdapter.adapt(Material.GRAY_STAINED_GLASS.createBlockData());
            BlockState whiteGlassState = BukkitAdapter.adapt(Material.WHITE_STAINED_GLASS.createBlockData());
            
            for (int x = 0; x < CHUNK_SIZE; x++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    int worldX = startX + x;
                    int worldZ = startZ + z;
                    HeightData data = heightData[x][z];
                    
                    // 填充基岩（整个柱状）
                    for (int y = 0; y < WORLD_HEIGHT; y++) {
                        editSession.setBlock(BlockVector3.at(worldX, y, worldZ), bedrockState);
                    }
                    
                    // 根据生物群系生成地形
                    if (data.biomeType == 0 && data.height < 76) {
                        // 黑石地形
                        for (int y = 70; y > data.height - 6; y--) {
                            editSession.setBlock(BlockVector3.at(worldX, y, worldZ), blackstoneState);
                        }
                        for (int y = 71; y < data.fh + 71; y++) {
                            editSession.setBlock(BlockVector3.at(worldX, y, worldZ), deadBrainCoralState);
                        }
                        editSession.setBlock(BlockVector3.at(worldX, data.fh + 70, worldZ), stoneState);
                    } else if (data.biomeType == 1 && data.height < 80) {
                        // 黑曜石地形
                        for (int y = 70; y > data.height - 10; y--) {
                            editSession.setBlock(BlockVector3.at(worldX, y, worldZ), obsidianState);
                        }
                        for (int y = 71; y < data.fh + 71; y++) {
                            editSession.setBlock(BlockVector3.at(worldX, y, worldZ), obsidianState);
                        }
                        editSession.setBlock(BlockVector3.at(worldX, data.fh + 70, worldZ), cryingObsidianState);
                    }
                    
                    // 陆地地形
                    if (data.landHeight < 75) {
                        for (int y = 170; y > data.landHeight + 95; y--) {
                            editSession.setBlock(BlockVector3.at(worldX, y, worldZ), stoneState);
                        }
                        for (int y = 171; y < data.landFh + 171; y++) {
                            editSession.setBlock(BlockVector3.at(worldX, y, worldZ), dirtState);
                        }
                        editSession.setBlock(BlockVector3.at(worldX, data.landFh + 170, worldZ), grassBlockState);
                    }
                    
                    // 玻璃装饰
                    if (data.fogHeight > 120) {
                        editSession.setBlock(BlockVector3.at(worldX, 151, worldZ), grayGlassState);
                    }
                    if (data.webValue > 20) {
                        editSession.setBlock(BlockVector3.at(worldX, 230, worldZ), whiteGlassState);
                    }
                    
                    // 设置生物群系
                    biome.setBiome(x, z, org.bukkit.block.Biome.THE_VOID);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initializeNoiseGenerators(World world) {
        if (noise == null) {
            noise = new SimplexOctaveGenerator(world.getSeed(), 6);
            noise.setScale(0.005D);
        }
        if (fognoise == null) {
            fognoise = new SimplexOctaveGenerator(world.getSeed() + 193873, 1);
            fognoise.setScale(0.02D);
        }
        if (landnoise == null) {
            landnoise = new SimplexOctaveGenerator(world.getSeed() + 19283737, 6);
            landnoise.setScale(0.005D);
        }
        if (webnoise == null) {
            webnoise = new SimplexOctaveGenerator(world.getSeed(), 1);
            webnoise.setScale(0.2D);
        }
        if (biomenoise == null) {
            biomenoise = new SimplexOctaveGenerator(world.getSeed() + 6, 1);
            biomenoise.setScale(0.005D);
        }
    }
    
    // 辅助类
    private static class HeightData {
        int biomeType;
        int height;
        int fh;
        int landHeight;
        int landFh;
        int fogHeight;
        double webValue;
    }
    
    @Override
    public List<BlockPopulator> getDefaultPopulators(@Nonnull World world){
        return Collections.singletonList(new VoidPopulator());
    }

}
