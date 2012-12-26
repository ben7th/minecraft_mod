package mindpin.items;

import mindpin.ClientProxy;
import mindpin.MCMind;
import net.minecraft.item.Item;

public class ItemClover extends Item {

	public ItemClover(int par1) {
		super(par1);

		setItemName("item_clover");
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconIndex(0);
		setCreativeTab(MCMind.TAB_LUCKY);
	}

}
