package mindpin.proxy;

import mindpin.MCGEEK;
import mindpin.renders.RenderBlockLucky;
import mindpin.rp2.lib.renderhelper.RenderLib;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends Proxy {

	public static String ITEMS_PNG_PATH = "/mindpin/png/mindpin_items.png";
	public static String BLOCKS_PNG_PATH = "/mindpin/png/mindpin_blocks.png";

	@Override
	public void register_render_things() {
		MinecraftForgeClient.preloadTexture(ITEMS_PNG_PATH);
		MinecraftForgeClient.preloadTexture(BLOCKS_PNG_PATH);

		RenderingRegistry.registerBlockHandler(R.RENDER_TYPE_BLOCK_LUCKY,
				new RenderBlockLucky.RenderHandler());

		MinecraftForgeClient.registerRenderContextHandler(BLOCKS_PNG_PATH,
				RenderBlockLucky.RenderContextHandler.SUBID,
				new RenderBlockLucky.RenderContextHandler());

		RenderLib.setRenderer(MCGEEK.block_lucky, RenderBlockLucky.class);
		
//		RenderingRegistry.registerBlockHandler(R.RENDER_TYPE_BLOCK_CLOVER, new ISimpleBlockRenderingHandler() {
//			
//			@Override
//			public boolean shouldRender3DInInventory() {
//				return false;
//			}
//			
//			@Override
//			public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
//					Block block, int modelId, RenderBlocks renderer) {
//				renderer.renderCrossedSquares(block, x, y, z);
//				return true;
//			}
//			
//			@Override
//			public void renderInventoryBlock(Block block, int metadata, int modelID,
//					RenderBlocks renderer) {
//				
//				Tessellator var4 = Tessellator.instance;
//				
//                var4.startDrawingQuads();
//                var4.setNormal(0.0F, -1.0F, 0.0F);
//                renderer.drawCrossedSquares(MCGEEK.block_lucky, metadata, -0.5D, -0.5D, -0.5D, 1.0F);
//                var4.draw();
//			}
//			
//			@Override
//			public int getRenderId() {
//				return R.RENDER_TYPE_BLOCK_CLOVER;
//			}
//		});
	}
}
