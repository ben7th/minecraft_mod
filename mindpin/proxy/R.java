package mindpin.proxy;

import mindpin.tabs.MCGEEKCreativeTabs;

public class R {
	// blocks
	final public static int BLOCK_LUCKY_ID  = 501;
	final public static int BLOCK_CLOVER_ID = 502;
	
	// items
	final public static int ITEM_TREE_AXE_IRON = 10001; // 10257
//	final public static int ITEM_TREE_AXE_DIAMOND = 10002;
	final public static int ITEM_LEAVES_CUTTER = 10003; // 10259
//  final public static int xxx = 10004;
	final public static int ITEM_WHEAT_CUTTER = 10005; // 10261
	
	// creative tabs
	final public static MCGEEKCreativeTabs TAB_LUCKY = new MCGEEKCreativeTabs("lucky", R.BLOCK_CLOVER_ID);
	final public static MCGEEKCreativeTabs TAB_PRACTICE = new MCGEEKCreativeTabs("practice", R.ITEM_TREE_AXE_IRON + 256);
	
	// render types
	final public static int RENDER_TYPE_BLOCK_LUCKY = -501;
	final public static int RENDER_TYPE_BLOCK_CLOVER = -502;
}
