package port.rp2.lib;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class RenderCustomBlock {
	protected Block block;

	public RenderCustomBlock(Block bl) {
		this.block = bl;
	}

	public abstract void randomDisplayTick(World paramyc, int paramInt1,
			int paramInt2, int paramInt3, Random paramRandom);

	public abstract void renderWorldBlock(RenderBlocks parambbb, IBlockAccess paramym,
			int paramInt1, int paramInt2, int paramInt3, int paramInt4);

	public abstract void renderInvBlock(RenderBlocks parambbb, int paramInt);
}
