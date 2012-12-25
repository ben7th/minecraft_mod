package mindpin.generators;

import java.util.Random;

import mindpin.MCMind;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class GeneratorBlockLucky extends MindBaseGenerator {

	/**
	 * 用于构造幸运方块
	 * 刷新在世界表面时：
	 * 		1 不刷新在水面(但是会刷新在露天岩浆表面)(也会刷新在树顶)
	 * 		2 一个chunk最多产生一个
	 * 		3 产生的概率为 1/8
	 * 		4 产生时，离地面的距离为 0(紧贴地面) - 3(浮空) 格
	 * 
	 * 刷新在高空时
	 * 		1 刷新高度为 100 - 199 之间随机
	 * 		2 产生的概率为 1/8
	 * 
	 * 刷新在地下时
	 */
	@Override
	public void generateSurface(World world, Random rand, int block_x, int block_z) {
		_ground(world, rand, block_x, block_z);
		_sky(world, rand, block_x, block_z);
	}

	private void _ground(World world, Random rand, int block_x, int block_z) {
		if (rand.nextInt(8) != 0) return; // 1/8 概率
		
		int x = block_x + rand.nextInt(16);
		int z = block_z + rand.nextInt(16);
		int y = world.getHeightValue(x, z);
		
		int block_below_id = world.getBlockId(x, y - 1, z);
		if (block_below_id == Block.waterMoving.blockID
				|| block_below_id == Block.waterStill.blockID)
			return; // 不刷新在水上
		
		int id = MCMind.BLOCK_LUCKY_ID;
		
		world.setBlockWithNotify(x, y + rand.nextInt(4), z, id);
	}
	
	private void _sky(World world, Random rand, int block_x, int block_z) {
		if (rand.nextInt(8) != 0) return; // 1/8 概率
	
		int x = block_x + rand.nextInt(16);
		int z = block_z + rand.nextInt(16);
		int y = rand.nextInt(100) + 100;
		
		int id = MCMind.BLOCK_LUCKY_ID;
		
		world.setBlockWithNotify(x, y, z, id);
	}

	@Override
	public void generateNether(World world, Random rand, int block_x, int block_z) {}

}
