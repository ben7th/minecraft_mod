package mindpin.items.luckybookmarks;

import java.util.ArrayList;
import java.util.List;

import mindpin.MCGEEK;
import mindpin.blocks.IhasRecipe;
import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCloverBookmark extends Item implements IhasRecipe {
	
	public ItemCloverBookmark(int item_id) {
		super(item_id);

		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(0, 2);
		setCreativeTab(R.TAB_LUCKY);
		
		setMaxStackSize(1);
	}
	
	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
		return 0;
	}
	
	@Override
	public List<MCGRecipe> get_recipes() {
		Object[] o = new Object[] {
			"A",
			"B",
			"C",
			Character.valueOf('A'), MCGEEK.block_clover,
			Character.valueOf('B'), Item.silk,
			Character.valueOf('C'), Item.paper,
		};
		ItemStack is = new ItemStack(this);
		
		List<MCGRecipe> re = new ArrayList<MCGRecipe>();
		re.add(new MCGRecipe(o, is));
		return re;
	}
}
