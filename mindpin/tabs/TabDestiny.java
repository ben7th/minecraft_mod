package mindpin.tabs;

import mindpin.proxy.R;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabDestiny extends CreativeTabs {

	public TabDestiny(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(R.BLOCK_CLOVER_ID/* + 256*/, 1, 0);
	}

}
