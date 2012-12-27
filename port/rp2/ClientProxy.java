package port.rp2;

import lib.renderhelper.RenderCustomBlock;
import lib.renderhelper.RenderLib;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IRenderContextHandler;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import port.rp2.blocks.RenderLamp;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends Proxy {

	public static String RP2_LIGHTTING_PATH = "/mindpin/gui/mindpin_blocks.png";

	@Override
	public void register_render_things() {
		MinecraftForgeClient.preloadTexture(RP2_LIGHTTING_PATH);

		RP2.customBlockModel = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(RP2.customBlockModel,
				new RenderHandler());

		MinecraftForgeClient.registerRenderContextHandler(RP2_LIGHTTING_PATH,
				1, new IRenderContextHandler() {

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
						GL11.glBlendFunc(GL11.GL_SRC_ALPHA,
								GL11.GL_ONE_MINUS_SRC_ALPHA);
					}
				});

		RenderLib.setRenderer(RP2.block_lamp, RenderLamp.class);
	}

	public static class RenderHandler implements ISimpleBlockRenderingHandler {

		@Override
		public void renderInventoryBlock(Block block, int metadata,
				int modelID, RenderBlocks renderer) {
			if (modelID != RP2.customBlockModel) {
				return;
			}
			RenderCustomBlock rcb = RenderLib.getInvRenderer(block.blockID,
					metadata);

			if (rcb == null) {
				System.out.printf(
						"Bad Render at %d:%d\n",
						new Object[] { Integer.valueOf(block.blockID),
								Integer.valueOf(metadata) });

				return;
			}
			rcb.renderInvBlock(renderer, metadata);
		}

		@Override
		public boolean renderWorldBlock(IBlockAccess world, int x, int y,
				int z, Block block, int modelId, RenderBlocks renderer) {
			if (renderer.overrideBlockTexture >= 0)
				return true;
			if (modelId != RP2.customBlockModel) {
				return false;
			}
			int md = world.getBlockMetadata(x, y, z);
			RenderCustomBlock rcb = RenderLib.getRenderer(block.blockID, md);

			if (rcb == null) {
				System.out.printf("Bad Render at %d:%d\n", new Object[] {
						Integer.valueOf(block.blockID), Integer.valueOf(md) });

				return true;
			}
			rcb.renderWorldBlock(renderer, world, x, y, z, md);
			return true;
		}

		@Override
		public boolean shouldRender3DInInventory() {
			return true;
		}

		@Override
		public int getRenderId() {
			return RP2.customBlockModel;
		}
	}

}
