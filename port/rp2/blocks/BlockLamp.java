package port.rp2.blocks;

import mindpin.MCMind;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import port.rp2.RP2;

public class BlockLamp extends Block {

	public BlockLamp(int i) {
		super(i, new Material(MapColor.woodColor));

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		setHardness(0.5F);
		setCreativeTab(MCMind.TAB_LUCKY);
		setLightValue(1.0f);
	}

	@Override
	public boolean canRenderInPass(int n) {
		return true;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getRenderType() {
		return RP2.customBlockModel;
	}
}
