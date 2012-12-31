package mindpin.blocks;

import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;

public class BlockClover extends BlockFlower {

	public BlockClover(int par1) {
		super(par1, 0);
		
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setCreativeTab(R.TAB_LUCKY);
		setLightValue(0.2F);
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int par1) {
		// 可以生长在三种生长地形 草，土，田
		
		return par1 == Block.grass.blockID || par1 == Block.dirt.blockID || par1 == Block.tilledField.blockID;
	}

	@Override
	public int getRenderType() {
		return R.RENDER_TYPE_BLOCK_CLOVER;
	}
}
