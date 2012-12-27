package mindpin;

import mindpin.blocks.BlockClover;
import mindpin.blocks.BlockLucky;
import mindpin.generators.GeneratorBlockLucky;
import mindpin.items.ItemClover;
import mindpin.tabs.TabDestiny;
import mindpin.utils.ModUtils;
import net.minecraft.creativetab.CreativeTabs;
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
	
	@Instance("MCMind")
	public static MCMind instance;
	
	@SidedProxy(clientSide = "mindpin.ClientProxy", serverSide = "mindpin.Proxy")
	public static Proxy proxy;
	
	public static int BLOCK_LUCKY_ID  = 501;
	public static int BLOCK_CLOVER_ID = 502;
	
	public static int ITEM_CLOVER_ID = 1001;
	
	public static CreativeTabs TAB_LUCKY = new TabDestiny("lucky");
	
	@Init
	public void init(@SuppressWarnings("unused") FMLInitializationEvent event) {
		// 标签
		LanguageRegistry.instance().addStringLocalization("itemGroup.lucky", "运气");
		
		// 那些年，我们一起堆的方块
		ModUtils.init_mod_block(new BlockLucky(BLOCK_LUCKY_ID), "block_lucky", "幸运方块");
		ModUtils.init_mod_block(new BlockClover(BLOCK_CLOVER_ID), "block_clover", "幸运四叶草");
		
		// 物品
		ModUtils.init_mod_item(new ItemClover(ITEM_CLOVER_ID), "item_clover", "幸运四叶草");
		
		// 地形创造器
		GameRegistry.registerWorldGenerator(new GeneratorBlockLucky());
		
		proxy.register_render_things();
	}
}