package mindpin.proxy;

import mindpin.MCGEEK;
import mindpin.renders.CustomRenderBlockLucky;
import mindpin.renders.RenderBlockClover;
import mindpin.rp2.lib.renderhelper.RenderLib;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends Proxy {

	public static String ITEMS_PNG_PATH = "/mindpin/png/mindpin_items.png";
	public static String BLOCKS_PNG_PATH = "/mindpin/png/mindpin_blocks.png";

	@Override
	public void register_render_things() {
		MinecraftForgeClient.preloadTexture(ITEMS_PNG_PATH);
		MinecraftForgeClient.preloadTexture(BLOCKS_PNG_PATH);

		// render block lucky
		RenderingRegistry.registerBlockHandler(new CustomRenderBlockLucky.RenderHandler());

		MinecraftForgeClient.registerRenderContextHandler(BLOCKS_PNG_PATH,
				CustomRenderBlockLucky.RenderContextHandler.SUBID,
				new CustomRenderBlockLucky.RenderContextHandler());

		RenderLib.setRenderer(MCGEEK.block_lucky, CustomRenderBlockLucky.class);
		
		// render block clover
		RenderingRegistry.registerBlockHandler(new RenderBlockClover());
	}
}
