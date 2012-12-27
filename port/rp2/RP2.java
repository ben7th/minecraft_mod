package port.rp2;

import port.rp2.blocks.BlockLamp;
import mindpin.utils.ModUtils;
import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "RP2 PORT", name = "RP2 PORT", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class RP2 {
	
	@Instance("RP2")
	public static RP2 instance;
	
	@SidedProxy(clientSide = "port.rp2.ClientProxy", serverSide = "port.rp2.Proxy")
	public static Proxy proxy;

	public static int customBlockModel;
	
	public static Block block_lamp;
	
	@Init
	public void init(@SuppressWarnings("unused") FMLInitializationEvent event) {
		
		block_lamp = ModUtils.init_mod_block(new BlockLamp(503), "RP2Lamp", "RP2灯");
		
		proxy.register_render_things();
	}
}
