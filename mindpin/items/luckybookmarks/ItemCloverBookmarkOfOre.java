package mindpin.items.luckybookmarks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mindpin.MCGEEK;
import mindpin.blocks.IhasRecipe;
import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import mindpin.utils.MCGPosition;
import mindpin.utils.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemCloverBookmarkOfOre extends Item implements IhasRecipe {

	final static Block[] ORE_BLOCK_KINDS = new Block[] {
			Block.oreCoal,
			Block.oreIron,
			Block.oreGold,
			Block.oreDiamond,
			Block.oreEmerald,
			Block.oreLapis,
			Block.oreRedstone
		};
	
	public ItemCloverBookmarkOfOre(int item_id) {
		super(item_id);
		
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(1, 2);
		setCreativeTab(R.TAB_LUCKY);
		
		setMaxStackSize(1);
	}
	
	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
		return 0;
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		// 显示附魔光影
		return true;
	}
	
	@Override
	public boolean onItemUse(ItemStack item_stack, EntityPlayer player,
			World world, int x, int y, int z, int face, float x_off,
			float y_off, float z_off) {
		
		if (world.isRemote) return false;
		
		-- item_stack.stackSize;
		
		Random rand = new Random();
		int f = rand.nextInt(4);
		if (f == 1) {
			// 25% 几率，死亡
			player.attackEntityFrom(new ItemLuckyBookmarkDamage(), 9999);
			System.out.println("四叶草书笺：矿藏");
			return false;
		}
		
		// face 0-下表面 1-上表面 2-北 3-南 4-西 5-东
		// off 鼠标点击处 距离方块 东-南-上 角的位置

		List<MCGPosition> ore_pos_arr = new ArrayList<MCGPosition>();		
		for (int dx = -8; dx <= 8; dx++) {
			for (int dz = -8; dz <= 8; dz++) {
				for (int new_y = 0; new_y <= y; new_y++) {
					int new_x = x + dx;
					int new_z = z + dz;
					MCGPosition pos = new MCGPosition(world, new_x, new_y, new_z);
					
					if (pos.is_of_kind(ORE_BLOCK_KINDS)){
						ore_pos_arr.add(pos);
					};
				}
			}
		}
		
		if (ore_pos_arr.size() > 0) {
			ModUtils.send_public_notice(world, "§6四叶草书笺散发着光芒…… " + player.getEntityName() + " 附近的矿石穿越地面，高高飞起！");
			
			
			for (MCGPosition ore_pos : ore_pos_arr) {
				int drop_x = x + rand.nextInt(6) - rand.nextInt(6);
				int drop_y = y + 8 + rand.nextInt(5);
				int drop_z = z + rand.nextInt(6) - rand.nextInt(6);
				
				ore_pos.drop_self_at(drop_x, drop_y, drop_z);
			}
		}
		
		return false;
	}
	
	@Override
	public List<Object[]> recipe_objects() {
		List<Object[]> res = new ArrayList<Object[]>();
		
		Object[] o = new Object[] {
			"HZH",
			"TSQ",
			"HJH",
			Character.valueOf('H'), Item.redstone,
			Character.valueOf('Z'), Block.blockDiamond,
			Character.valueOf('T'), Block.blockSteel,
			Character.valueOf('Q'), Block.blockLapis,
			Character.valueOf('J'), Block.blockGold,
			Character.valueOf('S'), MCGEEK.item_clover_bookmark,
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
	
	public static class ItemLuckyBookmarkDamage extends DamageSource {

		protected ItemLuckyBookmarkDamage() {
			super("item_clover_bookmark_damage");
		}
		
		@Override
		public String getDeathMessage(EntityPlayer player) {
			return "§c四叶草书笺化成了飞灰…… " + player.getEntityName() + " 被地下矿道徘徊的无名怨魂拉向了死亡的深渊……"; // 
		}
		
	}
}
