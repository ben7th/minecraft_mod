package mindpin.items;

import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemTreeAxeIron extends ItemAxe {

	public ItemTreeAxeIron(int item_id) {
		super(item_id, EnumToolMaterial.IRON);
		
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(0, 1);
		
		setCreativeTab(R.TAB_PRACTICE);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z,
			EntityPlayer player) {
		// TODO Auto-generated method stub
		return super.onBlockStartBreak(itemstack, X, Y, Z, player);
	}
}
