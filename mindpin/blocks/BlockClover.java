package mindpin.blocks;

import mindpin.ClientProxy;
import mindpin.MCMind;
import net.minecraft.block.BlockFlower;

public class BlockClover extends BlockFlower {

	public BlockClover(int par1) {
		super(par1, 2);
		
		setBlockName("block_lucky");
		setTextureFile(ClientProxy.BLOCKS_PNG_PATH);
		setCreativeTab(MCMind.TAB_LUCKY);
		setLightValue(0.2F);
	}

}
