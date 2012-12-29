package mindpin.proxy;

import mindpin.MCGEEK;
import mindpin.blocks.lucky.RenderBlockLucky;
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

		RenderingRegistry.registerBlockHandler(R.RENDER_TYPE_BLOCK_LUCKY,
				new RenderBlockLucky.RenderHandler());

		MinecraftForgeClient.registerRenderContextHandler(BLOCKS_PNG_PATH,
				RenderBlockLucky.RenderContextHandler.SUBID,
				new RenderBlockLucky.RenderContextHandler());

		RenderLib.setRenderer(MCGEEK.block_lucky, RenderBlockLucky.class);
	}
}
