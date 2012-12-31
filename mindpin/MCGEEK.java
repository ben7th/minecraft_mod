package mindpin;

import mindpin.blocks.BlockClover;
import mindpin.blocks.BlockLucky;
import mindpin.generators.GeneratorBlockClover;
import mindpin.generators.GeneratorBlockLucky;
import mindpin.items.ItemTreeAxeIron;
import mindpin.proxy.Proxy;
import mindpin.proxy.R;
import mindpin.utils.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
	
	public static Item item_board_axe_iron = new ItemTreeAxeIron(R.ITEM_TREE_AXE_IRON);
	
	@Init
	public void init(@SuppressWarnings("unused") FMLInitializationEvent event) {
		// 标签
		LanguageRegistry.instance().addStringLocalization(R.TAB_LUCKY.getTranslatedTabLabel(), "运气");
		LanguageRegistry.instance().addStringLocalization(R.TAB_PRACTICE.getTranslatedTabLabel(), "实践");
		
		// 那些年，我们一起堆的方块
		ModUtils.init_mod_block(block_lucky, "block_lucky", "幸运方块");
		ModUtils.init_mod_block(block_clover, "block_clover", "四叶草");
		
		// 工具是人类前进的基础
		ModUtils.init_mod_item(item_board_axe_iron, "item_tree_axe_iron", "铁制砍树斧");
		
		// 地形创造器
		GameRegistry.registerWorldGenerator(new GeneratorBlockLucky());
		GameRegistry.registerWorldGenerator(new GeneratorBlockClover());
		
		proxy.register_render_things();
	}
}