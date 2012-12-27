package mindpin.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mindpin.MCMind;
import mindpin.ClientProxy;
import mindpin.utils.BaseUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockLucky extends Block {
	private static float EXPLOSION_RADIUS = 4.0f; // 爆炸半径，目前与TNT相等
	
	private List<BlockLuckyDrop> drop_list = new ArrayList<BlockLuckyDrop>();
	private int rate_sum = 0;

	public BlockLucky(int par1) {
		super(par1, 0, new Material(MapColor.stoneColor));

		setTextureFile(ClientProxy.BLOCKS_PNG_PATH);
		setCreativeTab(MCMind.TAB_LUCKY);
		setLightValue(1);
		setHardness(2.0f);
		setResistance(10.0f);
		setStepSound(soundStoneFootstep);

		_init_drop_list();
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		if (world.isRemote) return;
		
		int f = new Random().nextInt(4); // 0, 1, 2, 3

		EntityPlayer player = BaseUtils.get_current_entity_player_from_world(world);
		
		if (f == 0) {
			player.attackEntityFrom(new BlockLuckyDamage(), 9999);
		}
		
		if (f == 1) {
			player.attackEntityFrom(new BlockLuckyDamage().set_explode(), 9999);
			// 爆炸，不过这个爆炸和玩家本人死亡没什么关系
			// 但是应该会伤及无辜
			world.createExplosion(null, player.posX, player.posY, player.posZ, EXPLOSION_RADIUS, true);
		}
		
		if (f > 1) {
			ItemStack item_stack = new ItemStack(_get_drop_id(), 1, 0);
			this.dropBlockAsItem_do(world, x, y, z, item_stack);
		}
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		// 掉落判定发生在敲碎方块时，因此不通过正常途径产生掉落，这里返回 0
		return 0;
	}

	private void _init_drop_list() {
		_add_drop(Item.diamond.shiftedIndex, 95); // 钻石
		_add_drop(Block.blockDiamond.blockID, 5); // 钻石块
	}

	private void _add_drop(int id, int rate) {
		drop_list.add(new BlockLuckyDrop(id, rate));
		rate_sum += rate;
	}

	/**
	 * 用来计算应该掉落什么物品
	 * 应该保证每个物品的掉落符合设置的随机值
	 * 例如设置的随机值为：
	 * 		空 2
	 * 		煤 3
	 * 		铁 2
	 * 
	 * rate_sum = 2 + 3 + 2 = 7，于是
	 * drop_value 会随机产生为 1, 2, 3, 4, 5, 6, 7 中某个值
	 * 当值为：
	 * 		1, 2 时，掉落为 空
	 * 		3, 4, 5 时，掉落为 煤
	 * 		5, 6 时，掉落为 铁
	 * 
	 * 掉落概率符合 2:3:2 的分布
	 */
	private int _get_drop_id() {
		int drop_value = new Random().nextInt(rate_sum) + 1; // 1 ~ rate_sum

		for (BlockLuckyDrop b : drop_list) {
			drop_value -= b.drop_rate;

			if (drop_value <= 0) {
				return b.minecraft_id;
			}
		}

		return 0;
	}

	// -------------------------------------------
	
	private class BlockLuckyDrop {
		private int minecraft_id;
		private int drop_rate;

		private BlockLuckyDrop(int minecraft_id, int drop_rate) {
			this.minecraft_id = minecraft_id;
			this.drop_rate = drop_rate;
		}
	}

	private class BlockLuckyDamage extends DamageSource {
		private boolean explode = false;

		protected BlockLuckyDamage() {
			super("block lucky");
		}

		BlockLuckyDamage set_explode() {
			this.explode = true;
			return this;
		}

		@Override
		public String getDeathMessage(EntityPlayer player) {
			if (this.explode) {
				return "§c" + player.getEntityName() + " §e死于幸运方块引发的剧烈爆炸";
			}

			return "§c" + player.getEntityName() + " §e死于幸运方块唤来的神秘厄运";
		}
	}
}
