package mindpin.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MCGEEKCreativeTabs extends CreativeTabs {

	int item_or_block_id;
	String label_temp;
	
	public MCGEEKCreativeTabs(String label, int item_or_block_id) {
		super(label);
		this.item_or_block_id = item_or_block_id;
		this.label_temp = label;
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(this.item_or_block_id, 1, 0);
	}
	
	public String get_label_name() {
		return "itemGroup." + this.label_temp;
	}

}
