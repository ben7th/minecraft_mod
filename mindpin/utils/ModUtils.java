package mindpin.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.src.ModLoader;

public class ModUtils {

	/**
	 * 在 MOD 里注册一个方块
	 */
	public static Block init_mod_block(Block block, String eng_name, String chs_name) {
		block.setBlockName(eng_name);
		ModLoader.addName(block, chs_name);
		ModLoader.registerBlock(block);
		
		return block;
	}
	
	public static Item init_mod_item(Item item, String eng_name, String chs_name) {
		item.setItemName(eng_name);
		ModLoader.addName(item, chs_name);
		
		return item;
	}
	
}
