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
	
	final static int ORE_SEEK_RADIUS = 4;
	
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
		
		Random rand = new Random();
		int f = rand.nextInt(100);
		
		/**
		 * 四叶草魔符：矿石的设定
		 * 5% 几率，死亡
		 * 5% 几率，所有的红石和青金石变为钻石
		 * 1% 几率，所有的矿石变为钻石
		 */
		
		if (f < 5) {
			// 0 ~ 4
			// 5% 几率，死亡
			
			-- item_stack.stackSize;
			
			player.attackEntityFrom(new ItemLuckyBookmarkDamage(), 9999);
			System.out.println("触发死亡：四叶草魔符：矿石");
			return false;
		}
		
		// f >= 20
		
		// face 0-下表面 1-上表面 2-北 3-南 4-西 5-东
		// off 鼠标点击处 距离方块 东-南-上 角的位置
		
		List<MCGPosition> ore_pos_arr = _get_ore_pos_arr(world, x, y, z);
		
		if (ore_pos_arr.size() > 32) {
			--item_stack.stackSize;
			
			if (f < 10) {
				// 5 ~ 9, 5% 几率，双倍 
				ModUtils.send_public_notice(world, "§6四叶草魔符变得更加明亮…… " + player.getEntityName() + " 周围地下的红石与青金石都变成了钻石！");
			} else if (f < 11) {
				// 10 1% 几率，四倍
				ModUtils.send_public_notice(world, "§6四叶草魔符发出耀眼光辉…… " + player.getEntityName() + " 周围地下全部的矿石都变成了钻石！！");
			} else {
				ModUtils.send_public_notice(world, "§6四叶草魔符散发着柔光…… " + player.getEntityName() + " 周围地下的矿石飞出地面，从半空掉落");	
			}
			
			for (MCGPosition ore_pos : ore_pos_arr) {
				int drop_x = x + rand.nextInt(3) - rand.nextInt(3);
				int drop_y = y + rand.nextInt(3) + rand.nextInt(3);
				int drop_z = z + rand.nextInt(3) - rand.nextInt(3);
				
				if (f < 10) {
					_drop_change_redstone_to_diamond(ore_pos, drop_x, drop_y, drop_z);
				} else if (f < 11) {
					_drop_all_as_diamond(ore_pos, drop_x, drop_y, drop_z);
				} else {
					_drop_normal(ore_pos, drop_x, drop_y, drop_z);
				}
			}
				
		} else {
			ModUtils.send_msg_to_player(world, player, "四叶草书签变得暗淡，周围的地下矿藏已经几近枯竭……");
		}
		
		return false;
	}
	
	private List<MCGPosition> _get_ore_pos_arr(World world, int x, int y, int z) {
		List<MCGPosition> re = new ArrayList<MCGPosition>();
		for (int dx = -ORE_SEEK_RADIUS; dx <= ORE_SEEK_RADIUS; dx++) {
			for (int dz = -ORE_SEEK_RADIUS; dz <= ORE_SEEK_RADIUS; dz++) {
				for (int new_y = 0; new_y <= y; new_y++) {
					int new_x = x + dx;
					int new_z = z + dz;
					MCGPosition pos = new MCGPosition(world, new_x, new_y, new_z);
					
					if (pos.is_ore_block()){
						re.add(pos);
					};
				}
			}
		}
		return re;
	}
	
	private void _drop_normal(MCGPosition pos, int drop_x, int drop_y, int drop_z) {
		pos.drop_self_at(drop_x, drop_y, drop_z);
	}
	
	private void _drop_change_redstone_to_diamond(MCGPosition pos, int drop_x, int drop_y, int drop_z) {
		Block[] block_kinds = new Block[] {
			Block.oreRedstone,
			Block.oreLapis
		};
		
		if (pos.is_of_kind(block_kinds)) {
			pos.drop_self_at_as(drop_x, drop_y, drop_z, Block.oreDiamond);
		} else {
			pos.drop_self_at(drop_x, drop_y, drop_z);
		}
	}
	
	private void _drop_all_as_diamond(MCGPosition pos, int drop_x, int drop_y, int drop_z) {
		pos.drop_self_at_as(drop_x, drop_y, drop_z, Block.oreDiamond);
	}
	
	@Override
	public List<Object[]> recipe_objects() {
		List<Object[]> res = new ArrayList<Object[]>();
		
		Object[] o = new Object[] {
			" Z ",
			"JSZ",
			" J ",
			Character.valueOf('Z'), Item.diamond,
			Character.valueOf('J'), Item.ingotGold,
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
			return "§c四叶草书签化成了飞灰…… " + player.getEntityName() + " 被地下矿道徘徊的游魂拉进了死亡的世界"; // 
		}
		
	}
}
