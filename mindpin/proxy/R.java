package mindpin.proxy;

import mindpin.tabs.MCGEEKCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;

public class R {
	// blocks
	final public static int BLOCK_LUCKY_ID  = 501;
	final public static int BLOCK_CLOVER_ID = 502;
	
	// items
	final public static int ITEM_TREE_AXE_IRON = 2001; // 2257
	
	// creative tabs
	final public static CreativeTabs TAB_LUCKY = new MCGEEKCreativeTabs("lucky", R.BLOCK_CLOVER_ID);
	final public static CreativeTabs TAB_PRACTICE = new MCGEEKCreativeTabs("practice", R.ITEM_TREE_AXE_IRON + 256);
	
	// render types
	final public static int RENDER_TYPE_BLOCK_LUCKY = -501;
	final public static int RENDER_TYPE_BLOCK_CLOVER = -502;
}
