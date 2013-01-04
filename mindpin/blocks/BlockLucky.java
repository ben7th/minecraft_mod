package mindpin.blocks;

import java.util.ArrayList;
import java.util.Random;

import mindpin.MCGEEK;
import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import mindpin.random.MCGRandomDroper;
import mindpin.random.MCGRandomHandler;
import mindpin.random.MCGRandomSwitcher;
import mindpin.utils.MCGPosition;
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
	
	private MCGRandomDroper dropper;

	public BlockLucky(int par1) {
		super(par1, 0, new Material(MapColor.stoneColor));

		setTextureFile(ClientProxy.BLOCKS_PNG_PATH);
		setCreativeTab(R.TAB_LUCKY);
		setLightValue(1);
		setHardness(2.0f);
		setResistance(10.0f);
		setStepSound(soundStoneFootstep);

		_init_dropper();
	}
	
	@Override
	public boolean removeBlockByPlayer(final World world, final EntityPlayer player, final int x,
			final int y, final int z) {
		boolean re = super.removeBlockByPlayer(world, player, x, y, z);
		
		if (world.isRemote) return re;
		
		final MCGPosition this_pos = new MCGPosition(world, x, y, z);
		
		MCGRandomSwitcher rs = new MCGRandomSwitcher("幸运方块");
		
		rs.add_handler(new MCGRandomHandler(1, "诅咒死亡") {
			@Override
			public void handle() {
				player.attackEntityFrom(new BlockLuckyDamage(), 9999);
				this_pos.delete_block_with_notifyBlocksOfNeighborChange();
			}
		});
		
		rs.add_handler(new MCGRandomHandler(1, "爆炸死亡") {
			@Override
			public void handle() {
				// 爆炸，不过这个爆炸和玩家本人死亡没什么关系，玩家是必死的
				// 但是应该会伤及无辜
				player.attackEntityFrom(new BlockLuckyDamage().set_explode(), 9999);
				world.createExplosion(player, player.posX, player.posY, player.posZ, EXPLOSION_RADIUS, true);
				this_pos.delete_block_with_notifyBlocksOfNeighborChange();
			}
		});
		
		rs.add_handler(new MCGRandomHandler(2, "随机掉落") {
			@Override
			public void handle() {
				this_pos.drop_self_at_as(x, y, z, MCGEEK.block_lucky);
			}
		});
		
		rs.run();
		
		return re;
	}
	
	@Override
	public boolean canRenderInPass(int pass) {
		return true;
	}
	
	@Override
	public int getRenderBlockPass() {
		return 1;
	}
	
	@Override
	public int getRenderType() {
		return R.RENDER_TYPE_BLOCK_LUCKY;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
			int z, int metadata, int fortune) {
		return dropper.get_drop();
	}
	
	// ----------------------------------------
	
	/**
	 * 开始设定了比较复杂的掉落列表在这里，后来觉得还是简单一些好，因为死亡率实在是太高了
	 * 随着设定丰富慢慢改吧
	 */
	private void _init_dropper() {
		this.dropper = new MCGRandomDroper("幸运方块");
		this.dropper.add_item_stack(5,   new ItemStack(Block.blockDiamond));
		this.dropper.add_item_stack(5,   new ItemStack(Block.enderChest));
		this.dropper.add_item_stack(50,  new ItemStack(Item.swordDiamond));
		this.dropper.add_item_stack(100, new ItemStack(Item.diamond));
	}

	// -------------------------------------------

	private static class BlockLuckyDamage extends DamageSource {
		private boolean explode = false;
		
		final private static String[] EXPLODE_MSGS = new String[] {
			"被幸运方块炸死了！",
			"像烟花般绽放",
			"碉堡了",
			"懂得了爆炸即艺术",
			"被炸得七零八落"
		};
		
		final private static String[] MSGS = new String[] {
			"你若安好，便是晴天",
			"啊朋友再见，再见吧再见吧再见吧……",
			"神秘地蒸发了",
			"啊~~~~~~！",
			"听到一个声音说：“你已经死了。”"
		};

		protected BlockLuckyDamage() {
			super("block_lucky_damage");
		}

		BlockLuckyDamage set_explode() {
			this.explode = true;
			return this;
		}

		@Override
		public String getDeathMessage(EntityPlayer player) {
			if (this.explode) {
				return "§c" + player.getEntityName() + " §c" + _get_random_str(EXPLODE_MSGS);
			}

			return "§c" + player.getEntityName() + " §c" + _get_random_str(MSGS); // 
		}
		
		private String _get_random_str(String[] msgs){
			return msgs[new Random().nextInt(msgs.length)];
		}
	}
}
