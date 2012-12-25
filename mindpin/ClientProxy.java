package mindpin;

import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	
	public static String ITEMS_PNG_PATH = "/mindpin/gui/mindpin_items.png";
	public static String BLOCKS_PNG_PATH = "/mindpin/gui/mindpin_blocks.png";
	
	@Override
	public void registerRenderThings() {
		MinecraftForgeClient.preloadTexture(ITEMS_PNG_PATH);
		MinecraftForgeClient.preloadTexture(BLOCKS_PNG_PATH);
	}
}
