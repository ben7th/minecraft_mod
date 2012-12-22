package mindpin;

import mindpin.blocks.BlockLucky;
import mindpin.blocks.BlockMusicPlayer;
import mindpin.dongfang.ItemMiniHakkero;
import mindpin.items.ItemFlowerWand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MindpinMod", name = "MindpinMod", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModMindpin {
	
	@Instance("ModMindpin")
	public static ModMindpin instance;
	
	@SidedProxy(
		clientSide = "mindpin.MindpinClientProxy",
		serverSide = "mindpin.MindpinCommonProxy"
	)
	public static MindpinCommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	public static Item item_mini_hakkero;
	
	@Init
	public void init(FMLInitializationEvent event) {
		// 那些年，我们一起堆的方块
				
		// 幸运方块
		Block block_lucky = new BlockLucky(501, 0, Material.wood);
		ModLoader.addName(block_lucky, "幸运方块");
		ModLoader.registerBlock(block_lucky);
		
		// 播放器方块
		Block block_music = new BlockMusicPlayer(502, 1, Material.wood);
		ModLoader.addName(block_music, "音乐播放器");
		ModLoader.registerBlock(block_music);
		
		// 鲜花魔杖
		Item item_flower_wand = new ItemFlowerWand(12000);
		ModLoader.addName(item_flower_wand, "鲜花魔杖");
		ModLoader.addRecipe(new ItemStack(item_flower_wand, 1), new Object[]{
			" # ",
			" * ",
			" * ",
			Character.valueOf('#'), Item.itemsList[38],
			Character.valueOf('*'), Item.stick
		});
		
		// dongfang
		// --------------------
		item_mini_hakkero = new ItemMiniHakkero(12001);
//		ModLoader.addName(item_mini_hakkero, "迷你八卦炉");
//		ModLoader.registerEntityID(EntityMiniHakkero.class, "MiniHakkero", ModLoader.getUniqueEntityId());
//		
//		RenderManager.instance.entityRenderMap.put(EntityMiniHakkero.class, new RenderMiniHakkero());
	}

	@PostInit
	public static void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public static void main(String[] args) {
		System.out.println((int)-0.5);
		System.out.println(Math.floor(-0.5));
	}
}