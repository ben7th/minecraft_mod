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
import net.minecraft.src.ModLoader;

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
	public List<Object[]> recipe_objects() {
		List<Object[]> res = new ArrayList<Object[]>();
		
		Object[] o = new Object[] {
			"A",
			"B",
			"C",
			Character.valueOf('A'), MCGEEK.block_clover,
			Character.valueOf('B'), Item.silk,
			Character.valueOf('C'), Item.paper,
		};
		
		res.add(o);
		return res;
	}
	
	@Override
	public void add_recipes() {
		ItemStack is = new ItemStack(this);
		List<Object[]> objs = recipe_objects();
		
		for(Object[] o : objs) {
			ModLoader.addRecipe(is, o);
		}
	}
}
