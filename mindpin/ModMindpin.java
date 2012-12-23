package mindpin;

import mindpin.blocks.BlockLucky;
import net.minecraft.block.Block;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MindpinMod", name = "MindpinMod", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModMindpin {
	
	@Instance("ModMindpin")
	public static ModMindpin instance;
	
	@Init
	public void init(FMLInitializationEvent event) {
		// 那些年，我们一起堆的方块
				
		// 幸运方块
		Block block_lucky = new BlockLucky(501, 0);
		ModLoader.addName(block_lucky, "幸运方块");
		ModLoader.registerBlock(block_lucky);
	}
}