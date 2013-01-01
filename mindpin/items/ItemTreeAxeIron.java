package mindpin.items;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import mindpin.utils.MCGPosition;
import mindpin.utils.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemTreeAxeIron extends ItemAxe {

	final private static String[] TREE_DOWN_MSGS = new String[] {
		"树倒啦 ~~~~~~~ >_<",
		"要致富，先种树，再砍树……",
		"砍树不忘栽树人，可持续发展人人有责。",
		"砍倒了一棵树，毁掉了小鸟的家园！",
		"树上没有猴儿……砍个什么劲呀……",
		"劳动人民砍树忙 @_@",
		"呢棵树太乞人憎咗，仆街呀！！"
	};
	
	public ItemTreeAxeIron(int item_id) {
		super(item_id, EnumToolMaterial.IRON);

		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(0, 1);
		setCreativeTab(R.TAB_PRACTICE);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z,
			EntityPlayer player) {

		// 为了在创造模式能起作用，故调用此方法，而不是调用 onBlockDestroyed
		// 参考 PlayerControllerMP 的 onPlayerDestroyBlock 方法

		World world = player.worldObj;
		TAPosition this_pos = new TAPosition(world, x, y, z);

		if (!this_pos.is_wood_block()) return false;
		
		player.sendChatToPlayer("啊啊啊啊啊啊");
		
		player.addChatMessage("哈哈哈");
		
		ArrayList<TAPosition> block_pos_arr = this_pos.get_connected_wood_blocks();
		
		_send_public_notice(world, player);
		for (TAPosition pos : block_pos_arr)
			if (!pos.equals(this_pos))
				pos.drop_self();
		
		return false;
	}
	
	private void _send_public_notice(World world, EntityPlayer player) {
		String msg = TREE_DOWN_MSGS[new Random().nextInt(TREE_DOWN_MSGS.length)];
		ModUtils.send_public_notice(world, "§6" + player.getEntityName() + " §6" + msg);
	}

	static class TAPosition extends MCGPosition {

		static int[] WOOD_BLOCK_IDS = new int[] { Block.wood.blockID };

		public TAPosition(World world, int x, int y, int z) {
			super(world, x, y, z);
		}

		public TAPosition(MCGPosition pos, ForgeDirection dir) {
			super(pos, dir);
		}

		public boolean not_in(Stack<TAPosition> stack) {
			for (TAPosition pos1 : stack) {
				if (pos1.equals(this))
					return false;
			}
			return true;
		}

		public boolean not_in(ArrayList<TAPosition> list) {
			for (TAPosition pos1 : list) {
				if (pos1.equals(this))
					return false;
			}
			return true;
		}

		public boolean is_wood_block() {
			int this_block_id = get_block_id();

			for (int id : WOOD_BLOCK_IDS) {
				if (this_block_id == id)
					return true;
			}
			return false;
		}
		
		public ArrayList<TAPosition> get_connected_wood_blocks() {
			ArrayList<TAPosition> res = new ArrayList<TAPosition>();
			Stack<TAPosition> positions = new Stack<TAPosition>();
			
			positions.push(this);

			do {
				TAPosition pos = positions.pop();
				for (ForgeDirection to_dir : ForgeDirection.VALID_DIRECTIONS) {
					TAPosition new_pos = new TAPosition(pos, to_dir);
					if (new_pos.is_wood_block() && new_pos.not_in(positions) && new_pos.not_in(res)) {
						positions.push(new_pos);
					}
				}
				res.add(pos);
			} while (!positions.empty());

			return res;
		}
	}
}
