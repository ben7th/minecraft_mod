package mindpin.blocks;

import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import net.minecraft.block.BlockFlower;

public class BlockClover extends BlockFlower {

	public BlockClover(int par1) {
		super(par1, 2);
		
		setTextureFile(ClientProxy.BLOCKS_PNG_PATH);
		setCreativeTab(R.TAB_LUCKY);
		setLightValue(0.2F);
	}

}
