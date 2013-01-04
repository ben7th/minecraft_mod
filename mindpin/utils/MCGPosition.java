package mindpin.utils;

import java.util.ArrayList;
import java.util.List;

import mindpin.proxy.R;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class MCGPosition {
	public World world;
	public int x;
	public int y;
	public int z;

	public MCGPosition(World world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public MCGPosition(MCGPosition pos, ForgeDirection dir) {
		this.world = pos.world;
		this.x = pos.x + dir.offsetX;
		this.y = pos.y + dir.offsetY;
		this.z = pos.z + dir.offsetZ;
	}

	public boolean equals(MCGPosition pos) {
		return (pos.x == this.x) && (pos.y == this.y) && (pos.z == this.z);
	}

	public int get_block_id() {
		return world.getBlockId(x, y, z);
	}

	public int get_block_meta_data() {
		return world.getBlockMetadata(x, y, z);
	}
	
	public Block get_block_reg_instance() {
		return Block.blocksList[get_block_id()];
	}

	// 方块改变函数
	// --------------------------
	
	// 设置方块，不触发 notifyBlocksOfNeighborChange
	public boolean set_block(int block_id) {
		return world.setBlock(x, y, z, block_id);
	}

	// 删除方块，不触发 notifyBlocksOfNeighborChange
	public boolean delete_block() {
		return set_block(0);
	}
	
	public boolean delete_block_with_notifyBlocksOfNeighborChange() {
		boolean re = delete_block();
		world.notifyBlocksOfNeighborChange(x, y, z, 0);
		return re;
	}

	// 使得当前位置的方块按默认掉落来掉落
	// 会触发 notifyBlocksOfNeighborChange
	public void drop_self() {
		int meta = get_block_meta_data();
		get_block_reg_instance().dropBlockAsItem(world, x, y, z, meta, 0);
		delete_block_with_notifyBlocksOfNeighborChange();
	}
	
	public void drop_self_at(int drop_x, int drop_y, int drop_z) {
		int meta = get_block_meta_data();
		get_block_reg_instance().dropBlockAsItem(world, drop_x, drop_y, drop_z, meta, 0);
		delete_block_with_notifyBlocksOfNeighborChange();
	}
	
	// 多倍掉落
	public void drop_self_multiple_at(int drop_x, int drop_y, int drop_z, int multiple) {
		int meta = get_block_meta_data();
		for (int i = 0; i < multiple; i++) {
			get_block_reg_instance().dropBlockAsItem(world, drop_x, drop_y,
					drop_z, meta, 0);
		}
		delete_block_with_notifyBlocksOfNeighborChange();
	}
	
	// 掉落为
	public void drop_self_at_as(int drop_x, int drop_y, int drop_z, Block block) {
		block.dropBlockAsItem(world, drop_x, drop_y, drop_z, 0, 0);
		delete_block_with_notifyBlocksOfNeighborChange();
	}
	
	// 方块判定函数
	public boolean is_of_kind(Block[] block_kinds) {
		int block_id = get_block_id();
		for (Block block : block_kinds) {
			if (block_id == block.blockID) return true;
		}
		return false;
	}
	
	public boolean is_ore_block() {
		return is_of_kind(R.ORE_BLOCK_KINDS);
	}

	
	// 位置获取函数
	// ---------------------------------------------
	
	// 获取当前坐标周围的坐标，和当前坐标一起返回，共27个
	public List<MCGPosition> get_positions_around() {
		List<MCGPosition> re = new ArrayList<MCGPosition>();
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					MCGPosition pos = new MCGPosition(world, x + dx, y + dy, z
							+ dz);
					re.add(pos);
				}
			}
		}
		return re;
	}

	// 获取当前坐标水平位置的坐标，和当前坐标一起返回，共9个
	public List<MCGPosition> get_horizontal_positions_around() {
		List<MCGPosition> re = new ArrayList<MCGPosition>();
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				MCGPosition pos = new MCGPosition(world, x + dx, y, z + dz);
				re.add(pos);
			}
		}
		return re;
	}
}
