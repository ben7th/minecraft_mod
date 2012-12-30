package mindpin;

import mindpin.blocks.BlockClover;
import mindpin.blocks.BlockLucky;
import mindpin.generators.GeneratorBlockClover;
import mindpin.generators.GeneratorBlockLucky;
import mindpin.proxy.Proxy;
import mindpin.proxy.R;
import mindpin.utils.ModUtils;
import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "MCGEEK", name = "MCGEEK", version = "0.0.3")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class MCGEEK {
	
	@Instance("MCGEEK")
	public static MCGEEK instance;
	
	@SidedProxy(clientSide = "mindpin.proxy.ClientProxy", serverSide = "mindpin.proxy.Proxy")
	public static Proxy proxy;
	
	public static Block block_lucky = new BlockLucky(R.BLOCK_LUCKY_ID);
	public static Block block_clover = new BlockClover(R.BLOCK_CLOVER_ID);
	
	@Init
	public void init(@SuppressWarnings("unused") FMLInitializationEvent event) {
		// 标签
		LanguageRegistry.instance().addStringLocalization("itemGroup.lucky", "运气");
		
		// 那些年，我们一起堆的方块
		ModUtils.init_mod_block(block_lucky, "block_lucky", "幸运方块");
		ModUtils.init_mod_block(block_clover, "block_clover", "四叶草");
				
		// 地形创造器
		GameRegistry.registerWorldGenerator(new GeneratorBlockLucky());
		GameRegistry.registerWorldGenerator(new GeneratorBlockClover());
		
		proxy.register_render_things();
	}
}