package mindpin.tabs;

import mindpin.MCMind;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabDestiny extends CreativeTabs {

	public TabDestiny(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(MCMind.ITEM_CLOVER_ID + 256, 1, 0);
	}

}
