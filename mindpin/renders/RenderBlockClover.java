package mindpin.renders;

import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockClover implements ISimpleBlockRenderingHandler {

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		ForgeHooksClient.bindTexture(ClientProxy.BLOCKS_PNG_PATH, 0); // 调用底层的方法变更绑定的贴图
		block.blockIndexInTexture = 2; // 临时修改贴图 index
		renderer.renderCrossedSquares(block, x, y, z);
		block.blockIndexInTexture = 0;
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	@Override
	public int getRenderId() {
		return R.RENDER_TYPE_BLOCK_CLOVER;
	}
}
