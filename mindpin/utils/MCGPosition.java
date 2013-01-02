package mindpin.utils;

import java.util.ArrayList;
import java.util.List;

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
	
	public boolean set_block(int block_id) {
		return world.setBlock(x, y, z, block_id);
	}
	
	public boolean delete_block() {
		return set_block(0);
	}
	
	public void drop_block_as_item(int block_id, int meta) {
		Block.blocksList[block_id].dropBlockAsItem(world, x, y, z, meta, 0);
	}
	
	// 使得当前位置的方块掉落它自己
	public void drop_self() {
		int id = get_block_id();
		int meta = get_block_meta_data();
		
		delete_block();
		drop_block_as_item(id, meta);
	}
	
	// 获取当前坐标周围的坐标，和当前坐标一起返回，共27个
	public List<MCGPosition> get_positions_around() {
		List<MCGPosition> re = new ArrayList<MCGPosition>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				for (int k = -1; k <= 1; k++) {
					MCGPosition pos = new MCGPosition(world, x + i, y + j, z
							+ k);
					re.add(pos);
				}
			}
		}
		return re;
	}
}
