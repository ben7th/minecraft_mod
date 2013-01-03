package mindpin;

import mindpin.blocks.BlockClover;
import mindpin.blocks.BlockLucky;
import mindpin.generators.GeneratorBlockClover;
import mindpin.generators.GeneratorBlockLucky;
import mindpin.items.ItemLeavesCutter;
import mindpin.items.ItemTreeAxeIron;
import mindpin.items.ItemWheatCutter;
import mindpin.items.luckybookmarks.ItemCloverBookmark;
import mindpin.items.luckybookmarks.ItemCloverBookmarkOfOre;
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

@Mod(modid = "MCGEEK", name = "MCGEEK", version = "0.0.6")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class MCGEEK {
	
	@Instance("MCGEEK")
	public static MCGEEK instance;
	
	@SidedProxy(clientSide = "mindpin.proxy.ClientProxy", serverSide = "mindpin.proxy.Proxy")
	public static Proxy proxy;
	
	public static Block block_lucky = new BlockLucky(R.BLOCK_LUCKY_ID);
	public static Block block_clover = new BlockClover(R.BLOCK_CLOVER_ID);
	
	public static Item item_board_axe_iron = new ItemTreeAxeIron(R.ITEM_TREE_AXE_IRON);
	public static Item item_leaves_cutter = new ItemLeavesCutter(R.ITEM_LEAVES_CUTTER);
	public static Item item_wheat_cutter = new ItemWheatCutter(R.ITEM_WHEAT_CUTTER);
	
	public static Item item_clover_bookmark = new ItemCloverBookmark(R.ITEM_CLOVER_BOOKMARK);
	public static Item item_clover_bookmark_of_ore = new ItemCloverBookmarkOfOre(R.ITEM_CLOVER_BOOKMARK_OF_ORE);
	
	@Init
	public void init(@SuppressWarnings("unused") FMLInitializationEvent event) {
		// 标签
		LanguageRegistry.instance().addStringLocalization(R.TAB_LUCKY.get_label_name(), "运气");
		LanguageRegistry.instance().addStringLocalization(R.TAB_PRACTICE.get_label_name(), "实践");
		
		// 那些年，我们一起堆的方块
		ModUtils.init_mod_block(block_lucky, "block_lucky", "G-幸运方块");
		ModUtils.init_mod_block(block_clover, "block_clover", "G-四叶草");
		
		// 工具是人类前进的基础
		ModUtils.init_mod_item(item_board_axe_iron, "item_tree_axe_iron", "G-铁制砍树斧");
		((ItemTreeAxeIron) item_board_axe_iron).add_recipes();
		ModUtils.init_mod_item(item_leaves_cutter, "item_leaves_cutter", "G-强力树叶剪");
		((ItemLeavesCutter) item_leaves_cutter).add_recipes();
		ModUtils.init_mod_item(item_wheat_cutter, "item_wheat_cutter", "G-两用镰刀");
		((ItemWheatCutter) item_wheat_cutter).add_recipes();
		
		ModUtils.init_mod_item(item_clover_bookmark, "item_lucky_bookmark", "G-四叶草书笺");
		((ItemCloverBookmark) item_clover_bookmark).add_recipes();
		ModUtils.init_mod_item(item_clover_bookmark_of_ore, "item_lucky_bookmark_of_ore", "G-四叶草书笺：矿藏");
		((ItemCloverBookmarkOfOre) item_clover_bookmark_of_ore).add_recipes();
		
		// 地形创造器
		GameRegistry.registerWorldGenerator(new GeneratorBlockLucky());
		GameRegistry.registerWorldGenerator(new GeneratorBlockClover());
		
		proxy.register_render_things();
	}
}