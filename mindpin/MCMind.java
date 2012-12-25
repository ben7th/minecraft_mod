package mindpin;

import mindpin.blocks.BlockLucky;
import mindpin.generators.GeneratorBlockLucky;
import mindpin.tabs.TabDestiny;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "MCMind", name = "MCMind", version = "0.0.2")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class MCMind {
	
	public static int BLOCK_LUCKY_ID = 501;
	
	public static CreativeTabs TAB_LUCKY = new TabDestiny("lucky");
	
	@Instance("MCMind")
	public static MCMind instance;
	
	@SidedProxy(clientSide = "mindpin.ClientProxy", serverSide = "mindpin.CommonProxy")
	public static CommonProxy proxy;
	
	@Init
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderThings();
		
		// 标签
		LanguageRegistry.instance().addStringLocalization("itemGroup.lucky", "运气");
		
		// 那些年，我们一起堆的方块
		
		// 幸运方块
		Block block_lucky = new BlockLucky(BLOCK_LUCKY_ID, 0);
		ModLoader.addName(block_lucky, "幸运方块");
		ModLoader.registerBlock(block_lucky);
		
		// 地形创造器
		GameRegistry.registerWorldGenerator(new GeneratorBlockLucky());
	}
}