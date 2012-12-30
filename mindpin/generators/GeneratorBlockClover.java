package mindpin.generators;

import java.util.Random;

import mindpin.MCGEEK;
import mindpin.proxy.R;
import net.minecraft.world.World;

public class GeneratorBlockClover extends MCGEEKBaseGenerator {

	// 刷新四叶草

	@Override
	public void generateSurface(World world, Random rand, int block_x, int block_z) {
		if (rand.nextInt(4) != 0) return; // 1/4 几率，是否在本块刷新
		
		int x = block_x + rand.nextInt(16);
		int z = block_z + rand.nextInt(16);
		int y = world.getHeightValue(x, z);

		for (int i = 0; i < 16; ++i) { // 最多刷新16个
			int set_x = x + rand.nextInt(8) - rand.nextInt(8);
			int set_y = y + rand.nextInt(4) - rand.nextInt(4);
			int set_z = z + rand.nextInt(8) - rand.nextInt(8);

			if (world.isAirBlock(set_x, set_y, set_z)
					&& (!world.provider.hasNoSky || set_y < 127)
					&& MCGEEK.block_clover.canBlockStay(world, set_x, set_y, set_z)) {
				world.setBlock(set_x, set_y, set_z, R.BLOCK_CLOVER_ID);
			}
		}

	}

	@Override
	public void generateNether(World world, Random rand, int block_x, int block_z) {}
}
