package mindpin.items.luckybookmarks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mindpin.MCGEEK;
import mindpin.blocks.IhasRecipe;
import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import mindpin.random.MCGRandomHandler;
import mindpin.random.MCGRandomSwitcher;
import mindpin.utils.MCGPosition;
import mindpin.utils.MCGUtils;
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
	public boolean onItemUse(final ItemStack item_stack, final EntityPlayer player,
			final World world, final int x, final int y, final int z, int face, float x_off,
			float y_off, float z_off) {
		
		// face 0-下表面 1-上表面 2-北 3-南 4-西 5-东
		// off 鼠标点击处 距离方块 东-南-上 角的位置
		
		if (world.isRemote) return false;
		
		final List<MCGPosition> ore_pos_arr = _get_ore_pos_arr(world, x, y, z);
		final MCGPosition this_pos = new MCGPosition(world, x, y, z);
		
		// 至少能获得半组矿石才行,
		// 为了概率更加合理 0.0.6r2 中，把这个判定移到死亡判定之前了
		if (ore_pos_arr.size() < 32) {
			MCGUtils.send_msg_to_player(world, player, "四叶草神符变得暗淡，周围的地下矿藏已经几近枯竭……");
			return false;
		}
		
		--item_stack.stackSize;
		
		/**
		 * 四叶草神符：矿石的设定
		 * 5% 几率，死亡
		 * 5% 几率，所有的红石和青金石变为钻石
		 * 1% 几率，所有的矿石变为钻石
		 * 89% 几率，正常掉落
		 */
		
		MCGRandomSwitcher rs = new MCGRandomSwitcher("四叶草神符：矿石");
		
		rs.add_handler(new MCGRandomHandler(5, "死亡") {
			@Override
			public void handle() {
				player.attackEntityFrom(new ItemLuckyBookmarkDamage(), 9999);
			}
		});
		
		rs.add_handler(new MCGRandomHandler(5, "钻石增多") {
			@Override
			public void handle() {
				MCGUtils.send_public_notice(world, "§6四叶草神符变得更加明亮…… " + player.getEntityName() + " 周围地下的红石与青金石都变成了钻石！");
				for (MCGPosition ore_pos : ore_pos_arr) {
					_drop_more_diamond(ore_pos, _get_item_stack_drop_pos(this_pos));
				}
			}
		});
		
		rs.add_handler(new MCGRandomHandler(1, "钻石极多") {
			@Override
			public void handle() {
				MCGUtils.send_public_notice(world, "§6四叶草神符发出耀眼光辉…… " + player.getEntityName() + " 周围地下全部的矿石都变成了钻石！！");
				for (MCGPosition ore_pos : ore_pos_arr) {
					_drop_most_diamond(ore_pos, _get_item_stack_drop_pos(this_pos));
				}
			}
		});
		
		rs.add_handler(new MCGRandomHandler(89, "正常掉落") {
			@Override
			public void handle() {
				MCGUtils.send_public_notice(world, "§6四叶草神符散发着柔光…… " + player.getEntityName() + " 周围地下的矿石飞出地面，从半空散落");	
				for (MCGPosition ore_pos : ore_pos_arr) {
					_drop_normal(ore_pos, _get_item_stack_drop_pos(this_pos));
				}
			}
		});
		
		rs.run();
		
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
	
	private MCGPosition _get_item_stack_drop_pos(MCGPosition pos) {
		Random rand = new Random();
		int drop_x = pos.x + rand.nextInt(3) - rand.nextInt(3);
		int drop_y = pos.y + rand.nextInt(3) + rand.nextInt(3);
		int drop_z = pos.z + rand.nextInt(3) - rand.nextInt(3);
		
		return new MCGPosition(pos.world, drop_x, drop_y, drop_z);
	}
	
	private void _drop_normal(MCGPosition pos, MCGPosition drop_pos) {
		pos.drop_self_at(drop_pos.x, drop_pos.y, drop_pos.z);
	}
	
	private void _drop_more_diamond(MCGPosition pos, MCGPosition drop_pos) {
		Block[] block_kinds = new Block[] {
			Block.oreRedstone,
			Block.oreLapis
		};
		
		if (pos.is_of_kind(block_kinds)) {
			pos.drop_self_at_as(drop_pos.x, drop_pos.y, drop_pos.z, Block.oreDiamond);
		} else {
			pos.drop_self_at(drop_pos.x, drop_pos.y, drop_pos.z);
		}
	}
	
	private void _drop_most_diamond(MCGPosition pos, MCGPosition drop_pos) {
		pos.drop_self_at_as(drop_pos.x, drop_pos.y, drop_pos.z, Block.oreDiamond);
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
