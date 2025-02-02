package cn.zimzaza4.slimefunzt;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

import com.sk89q.worldedit.extent.clipboard.Clipboard;

import cn.zimzaza4.slimefunzt.World.VoidWorld;
import cn.zimzaza4.slimefunzt.listeners.ClickMac;
import cn.zimzaza4.slimefunzt.listeners.EliteMobD;
import cn.zimzaza4.slimefunzt.listeners.NormalMobD;
import cn.zimzaza4.slimefunzt.tasks.RegSFItem;
import cn.zimzaza4.slimefunzt.tasks.RegSFMachine;
import cn.zimzaza4.slimefunzt.util.SchematicUtil;
import io.github.mooy1.infinitylib.core.AbstractAddon;

public class SlimefunZT extends AbstractAddon {
    public Boolean isFAME;
    public Boolean isDebug;
    public static SlimefunZT inst;
    private static SlimefunZT Instance;
    public Clipboard void_ring;
    public Clipboard void_tree;
    public Clipboard void_tree_2;
    public Clipboard void_tree_large;
    public Clipboard stone_1;
    public Clipboard stone_2;
    public Clipboard stone_3;


    public String void_world;

    public SlimefunZT(String githubUserName, String githubRepo, String autoUpdateBranch, String autoUpdateKey) {
        super(githubUserName, githubRepo, autoUpdateBranch, autoUpdateKey);
    }



    public SlimefunZT(){
        super("zimzaza4", "Slimefun-SCraft", "main", "auto-update");
    }

    @Override
    protected void enable() {
        isDebug = getConfig().getBoolean("debug-mode", false);

        Instance = this;

        inst = this;

        this.isFAME = Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null;
        setupConfig();
        RegSFMachine.run();
        RegSFItem.run();


        if (getConfig().getBoolean("enable-void-world")) {
            try{setupSchematic();}catch(IOException ignored){}
            if (Bukkit.getWorld("world_void") == null) {
                WorldCreator newWorld = new WorldCreator("world_void");
                newWorld.environment(World.Environment.NORMAL);
                long seed = new Random().nextLong();
                newWorld = newWorld.seed(seed);
                newWorld = newWorld.type(WorldType.NORMAL);
                newWorld = newWorld.generateStructures(false);
                newWorld = newWorld.generator(new VoidWorld());
                World world = newWorld.createWorld();
            }
        }
        if (Bukkit.getPluginManager().getPlugin("EliteMobs") != null) {
            Bukkit.getPluginManager().registerEvents(new EliteMobD(), this);
        }else {
            Bukkit.getPluginManager().registerEvents(new NormalMobD(), this);
        }
        Bukkit.getPluginManager().registerEvents(new ClickMac(), this);

    }

    @Override
    protected void disable() {

    }

    public static SlimefunZT getInstance() {

        return Instance;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String world, String id){
        switch (id){
            case "the_void":
                return new VoidWorld();
            default:
                return null;
        }
    }


    public static NamespacedKey getKey(String key){
        return new NamespacedKey(Instance, key.toUpperCase(Locale.ROOT));
    }

    private void setupConfig(){
        if (!new File(getDataFolder(), "config.yml").exists()){
            saveDefaultConfig();
        }
            
        if (!new File(getDataFolder(), "void_ring.schem").exists()){
        	saveResource("void_ring.schem", true);
        }
        if (!new File(getDataFolder(), "void_tree.schem").exists()){
            saveResource("void_tree.schem", true);
        }
        if (!new File(getDataFolder(), "void_tree_2.schem").exists()){
            saveResource("void_tree_2.schem", true);
        }
        if (!new File(getDataFolder(), "void_tree_large.schem").exists()){
            saveResource("void_tree_large.schem", true);
        }
        if (!new File(getDataFolder(), "stone_natural_1.schem").exists()){
            saveResource("stone_natural_1.schem", true);
        }
        if (!new File(getDataFolder(), "stone_natural_2.schem").exists()){
            saveResource("stone_natural_2.schem", true);
        }
        if (!new File(getDataFolder(), "stone_natural_3.schem").exists()){
            saveResource("stone_natural_3.schem", true);
        }
        
            /*saveResource("void_ring.schem", true);
            
            saveResource("void_tree.schem", true);
            saveResource("void_tree_2.schem", true);
            saveResource("void_tree_large.schem", true);
            
            saveResource("stone_natural_1.schem", true);
            saveResource("stone_natural_2.schem", true);
            saveResource("stone_natural_3.schem", true);*/

    }
    private void setupSchematic() throws IOException {
    	File f = new File (getDataFolder(),"void_ring.schem");
        System.out.println("Loading "+f.getPath());
        this.void_ring = SchematicUtil.LoadSchmatic(f);
        
        f = new File (getDataFolder(),"void_tree.schem");
        System.out.println("Loading "+f.getPath());
        this.void_tree = SchematicUtil.LoadSchmatic(f);
        
        f = new File (getDataFolder(),"void_tree_2.schem");
        System.out.println("Loading "+f.getPath());
        this.void_tree_2 = SchematicUtil.LoadSchmatic(f);

        f = new File (getDataFolder(),"void_tree_large.schem");
        System.out.println("Loading "+f.getPath());
        this.void_tree_large = SchematicUtil.LoadSchmatic(f);

        f = new File (getDataFolder(),"stone_natural_1.schem");
        System.out.println("Loading "+f.getPath());
        this.stone_1 = SchematicUtil.LoadSchmatic(f);

        f = new File (getDataFolder(),"stone_natural_2.schem");
        System.out.println("Loading "+f.getPath());
        this.stone_2 = SchematicUtil.LoadSchmatic(f);

        f = new File (getDataFolder(),"stone_natural_3.schem");
        System.out.println("Loading "+f.getPath());
        this.stone_3 = SchematicUtil.LoadSchmatic(f);

    }
}
