package mindpin.generators;

import java.util.Random;

import mindpin.MCGEEK;
import mindpin.proxy.R;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class GeneratorBlockClover extends MCGEEKBaseGenerator {

	// 刷新四叶草

	@Override
	public void generateSurface(World world, Random rand, int block_x, int block_z) {
		if (rand.nextInt(2) != 0) return; // 1/2 几率，是否在本块刷新
		
		int x = block_x + rand.nextInt(16);
		int z = block_z + rand.nextInt(16);
		int y = world.getHeightValue(x, z);
		
		// 如果下面一格是树叶，就往下移动，直到有适合的土地为止
		if (world.getBlockId(x, y - 1, z) == Block.leaves.blockID) {
			do {
				y--;
				if (MCGEEK.block_clover.canBlockStay(world, x, y, z)) {
					break;
				}
			} while (y > 0);
		}
		
		
		for (int i = 0; i < 16; ++i) { // 最多刷新16个，可以刷新在看不到天空的地方
			int set_x = x + rand.nextInt(8) - rand.nextInt(8);
			int set_y = y + rand.nextInt(4) - rand.nextInt(4);
			int set_z = z + rand.nextInt(8) - rand.nextInt(8);

			if (world.isAirBlock(set_x, set_y, set_z)
					&& set_y < 127
					&& MCGEEK.block_clover.canBlockStay(world, set_x, set_y, set_z)) { // 这里 y 不用减 1
				world.setBlock(set_x, set_y, set_z, R.BLOCK_CLOVER_ID);
			}
		}

	}

	@Override
	public void generateNether(World world, Random rand, int block_x, int block_z) {}
}
