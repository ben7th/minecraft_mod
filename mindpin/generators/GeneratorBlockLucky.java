package mindpin.generators;

import java.util.Random;

import mindpin.proxy.R;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class GeneratorBlockLucky extends MCGEEKBaseGenerator {
	
	/**
	 * 用于构造幸运方块
	 * 刷新在世界表面时：
	 * 		1 在水面刷新的几率更低，是地面的1/4也就是 1/32 (也会刷新在树顶)
	 * 		2 一个chunk最多产生一个
	 * 		3 产生的概率为 1/8
	 * 		4 产生时，离地面的距离为 0(紧贴地面) - 3(浮空) 格
	 * 
	 * 刷新在高空时
	 * 		1 刷新高度为 100 - 199 之间随机
	 * 		2 产生的概率为 1/8
	 * 
	 * 刷新在地下时
	 * 		1 刷新在 高度 0 - 100，如果原先应该是石块，则替换掉
	 * 		2 每个chunk包含10个，一般不会有相邻
	 */
	@Override
	public void generateSurface(World world, Random rand, int block_x, int block_z) {
		_ground(world, rand, block_x, block_z);
		_sky(world, rand, block_x, block_z);
		_underground(world, rand, block_x, block_z);
	}

	private void _ground(World world, Random rand, int block_x, int block_z) {
		if (rand.nextInt(8) != 0) return; // 近地面 1/8 概率
		
		int x = block_x + rand.nextInt(16);
		int z = block_z + rand.nextInt(16);
		int y = world.getHeightValue(x, z);
		
		int below_block_id = world.getBlockId(x, y - 1, z);
		
		if (below_block_id == Block.waterMoving.blockID || below_block_id == Block.waterStill.blockID) {
			if (rand.nextInt(4) == 0) {
				world.setBlockWithNotify(x, y + rand.nextInt(4), z, R.BLOCK_LUCKY_ID);
			}
			
			return; // 水面刷新率较低
		}
		
		world.setBlockWithNotify(x, y + rand.nextInt(4), z, R.BLOCK_LUCKY_ID);
	}
	
	private void _sky(World world, Random rand, int block_x, int block_z) {
		if (rand.nextInt(8) != 0) return; // 空中 1/8 概率
	
		int x = block_x + rand.nextInt(16);
		int z = block_z + rand.nextInt(16);
		int y = rand.nextInt(100) + 100;
				
		world.setBlockWithNotify(x, y, z, R.BLOCK_LUCKY_ID);
	}

	private void _underground(World world, Random rand, int block_x, int block_z) {
		for (int i = 0; i < 10; i++) {
			int x = block_x + rand.nextInt(16);
			int z = block_z + rand.nextInt(16);
			
			int current_height = world.getHeightValue(x, z);
			int y = rand.nextInt(current_height - 1);
			
			int block_id = world.getBlockId(x, y, z);
			
			if (block_id == Block.stone.blockID) {
				world.setBlockWithNotify(x, y, z, R.BLOCK_LUCKY_ID);
			}			
		}
	}
	
	@Override
	public void generateNether(World world, Random rand, int block_x, int block_z) {}

}
