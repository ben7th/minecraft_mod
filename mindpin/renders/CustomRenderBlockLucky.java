package mindpin.renders;

import mindpin.proxy.ClientProxy;
import mindpin.proxy.R;
import mindpin.rp2.lib.renderhelper.RenderContext;
import mindpin.rp2.lib.renderhelper.RenderCustomBlock;
import mindpin.rp2.lib.renderhelper.RenderLib;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IRenderContextHandler;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class CustomRenderBlockLucky extends RenderCustomBlock {
	int light_color = 0xFFF100;

	RenderContext context;

	public CustomRenderBlockLucky(Block block) {
		super(block);
		this.context = new RenderContext();
	}

	@Override
	public void renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iba,
			int i, int j, int k, int md) {

		this.context.setPos(i, j, k);
		this.context.setOrientation(0, 0);
		this.context.readGlobalLights(iba, i, j, k);

		if (MinecraftForgeClient.getRenderPass() != 1) {
			float f = 1.0F;

			this.context.startWorldRender(renderblocks);
			this.context.bindTexture(ClientProxy.BLOCKS_PNG_PATH);

			this.context.setSize(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

			this.context.setTexFlags(0);
			this.context.setupBox();
			this.context.transform();

			this.context.setTint(f, f, f);
			this.context.setLocalLights(1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
			this.context.setTex(0);

			this.context.doMappingBox(63);
			this.context.doLightLocal(63);
			this.context.renderFlat(63);

			this.context.unbindTexture();
			this.context.endWorldRender();
			return;
		}

		RenderLib.bindTexture(ClientProxy.BLOCKS_PNG_PATH,
				RenderContextHandler.SUBID);

		int tc = this.light_color;

		this.context.setTint((tc >> 16) / 255.0F, (tc >> 8 & 0xFF) / 255.0F,
				(tc & 0xFF) / 255.0F);

		this.context.setLocalLights(1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
		this.context.setSize(-0.05D, -0.05D, -0.05D, 1.05D, 1.05D, 1.05D);

		this.context.setupBox();
		this.context.transform();
		this.context.setSize(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

		this.context.doMappingBox(63);
		this.context.doLightLocal(63);
		this.context.renderAlpha(63, 0.38F);

		RenderLib.unbindTexture();
	}

	@Override
	public void renderInvBlock(RenderBlocks renderblocks, int md) {
		Tessellator tessellator = Tessellator.instance;
		this.block.setBlockBoundsForItemRender();

		this.context.setPos(-0.5D, -0.5D, -0.5D);
		this.context.setTint(1.0F, 1.0F, 1.0F);
		this.context.setLocalLights(0.5F, 1.0F, 0.8F, 0.8F, 0.6F, 0.6F);

		this.context.setTex(0);

		this.context.setOrientation(0, 0);

		this.context.setSize(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		this.context.calcBounds();

		this.context.bindTexture(ClientProxy.BLOCKS_PNG_PATH);
		tessellator.startDrawingQuads();
		this.context.useNormal = true;
		this.context.renderFaces(63);
		this.context.useNormal = false;
		tessellator.draw();
		this.context.unbindTexture();
	}

	// -----------------------------

	public static class RenderContextHandler implements IRenderContextHandler {
		final public static int SUBID = R.RENDER_TYPE_BLOCK_LUCKY;

		@Override
		public void beforeRenderContext() {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
		}

		@Override
		public void afterRenderContext() {
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	public static class RenderHandler implements ISimpleBlockRenderingHandler {

		@Override
		public void renderInventoryBlock(Block block, int metadata,
				int modelID, RenderBlocks renderer) {
			if (modelID != R.RENDER_TYPE_BLOCK_LUCKY) {
				return;
			}
			RenderCustomBlock rcb = RenderLib.getInvRenderer(block.blockID,
					metadata);

			if (rcb == null) {
				System.out.printf("Bad Render at %d:%d\n", new Object[] {
						block.blockID, metadata });

				return;
			}
			rcb.renderInvBlock(renderer, metadata);
		}

		@Override
		public boolean renderWorldBlock(IBlockAccess world, int x, int y,
				int z, Block block, int modelId, RenderBlocks renderer) {
			if (renderer.overrideBlockTexture >= 0)
				return true;

			if (modelId != R.RENDER_TYPE_BLOCK_LUCKY) {
				return false;
			}

			int meta_data = world.getBlockMetadata(x, y, z);
			RenderCustomBlock rcb = RenderLib.getRenderer(block.blockID,
					meta_data);

			if (rcb == null) {
				System.out.printf("Bad Render at %d:%d\n", new Object[] {
						block.blockID, meta_data });
				return true;
			}

			rcb.renderWorldBlock(renderer, world, x, y, z, meta_data);
			return true;
		}

		@Override
		public boolean shouldRender3DInInventory() {
			return true;
		}

		@Override
		public int getRenderId() {
			return R.RENDER_TYPE_BLOCK_LUCKY;
		}
	}

}
