package mindpin.items;

import java.util.ArrayList;
import java.util.List;

import mindpin.blocks.IhasRecipe;
import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import mindpin.utils.MCGPosition;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class ItemWheatCutter extends ItemHoe implements IhasRecipe {

	final static int CROPS_MAX_META = 7;
	
	public ItemWheatCutter(int item_id) {
		super(item_id, EnumToolMaterial.IRON);

		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(4, 1);
		setCreativeTab(R.TAB_PRACTICE);
	}
	
	@Override
	public float getStrVsBlock(ItemStack item_stack, Block block, int metadata) {
		
		if (block.blockID == Block.crops.blockID && metadata == CROPS_MAX_META) {
			// 7 是麦子长到最大时的 meta
			return super.getStrVsBlock(item_stack, block, metadata);
		}
		
		return 0;
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack item_stack, int x, int y, int z,
			EntityPlayer player) {
		World world = player.worldObj;

		if (world.isRemote)
			return false;

		MCGPosition this_pos = new MCGPosition(world, x, y, z);

		if (_is_full_blown_wheat(this_pos)) {

			List<MCGPosition> pos_around = this_pos
					.get_horizontal_positions_around();

			for (MCGPosition pos : pos_around) {
				if (_is_full_blown_wheat(pos)) {
					pos.drop_self();
				}
			}

			item_stack.damageItem(1, player);
		}

		return false;
	}
	
	private boolean _is_full_blown_wheat(MCGPosition pos) {
		return pos.get_block_id() == Block.crops.blockID
				&& pos.get_block_meta_data() == CROPS_MAX_META;
	}
	
	@Override
	public List<Object[]> recipe_objects() {
		List<Object[]> res = new ArrayList<Object[]>();

		Object[] o = new Object[] { 
			"BBB", 
			" A ", 
			"A  ",
			Character.valueOf('B'), Item.ingotIron, 
			Character.valueOf('A'), Item.stick 
		};

		res.add(o);
		return res;
	}

	@Override
	public void add_recipes() {
		ItemStack is = new ItemStack(this);
		List<Object[]> objs = recipe_objects();

		for (Object[] o : objs) {
			ModLoader.addRecipe(is, o);
		}
	}
}
