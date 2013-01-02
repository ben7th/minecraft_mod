package mindpin.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mindpin.blocks.IhasRecipe;
import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemLeavesCutter extends Item implements IhasRecipe {

	public ItemLeavesCutter(int item_id) {
		super(item_id);

		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(2, 1);
		setCreativeTab(R.TAB_PRACTICE);
		
		this.setMaxStackSize(1);
		this.setMaxDamage(1190); // 5倍于一般剪刀
	}

	@Override
	public boolean onBlockDestroyed(ItemStack item_stack, World world,
			int block_id, int x, int y, int z, EntityLiving par7EntityLiving) {
		// 可以剪树叶
		if (block_id != Block.leaves.blockID) {
			return super.onBlockDestroyed(item_stack, world, block_id, x, y, z,
					par7EntityLiving);
		}

		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack item_stack, Block block) {
		// 对树叶的强度是 15.0
		return block.blockID == Block.leaves.blockID ? 15.0F : super
				.getStrVsBlock(item_stack, block);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack item_stack, int x, int y, int z,
			EntityPlayer player) {
		World world = player.worldObj;

		if (world.isRemote)
			return false;

		int block_id = world.getBlockId(x, y, z);

		if (block_id == Block.leaves.blockID) {
			ArrayList<ItemStack> drops = Block.leaves.onSheared(item_stack,
					world, x, y, z, EnchantmentHelper.getEnchantmentLevel(
							Enchantment.fortune.effectId, item_stack));
			Random rand = new Random();
			for (ItemStack stack : drops) {
                float f = 0.7F;
                double dx = (rand.nextFloat() * f) + (1.0F - f) * 0.5D;
                double dy = (rand.nextFloat() * f) + (1.0F - f) * 0.5D;
                double dz = (rand.nextFloat() * f) + (1.0F - f) * 0.5D;
                
                EntityItem entity_item = new EntityItem(player.worldObj, x + dx, y + dy, z + dz, stack);
                entity_item.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(entity_item);
			}
			
			item_stack.damageItem(1, player);
			player.addStat(StatList.mineBlockStatArray[block_id], 1);
		}

		return false;
	}

	@Override
	public List<Object[]> recipe_objects() {
		List<Object[]> res = new ArrayList<Object[]>();
		
		Object[] o = new Object[] {
			"B B",
			" A ",
			"A A",
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
		
		for(Object[] o : objs) {
			ModLoader.addRecipe(is, o);
		}
	}

}
