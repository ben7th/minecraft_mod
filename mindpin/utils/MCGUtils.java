package mindpin.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class MCGUtils {

	/**
	 * 在 MOD 里注册一个方块
	 */
	public static Block init_mod_block(Block block, String eng_name, String chs_name) {
		block.setBlockName(eng_name);
		ModLoader.addName(block, chs_name);
		ModLoader.registerBlock(block);
		
		return block;
	}
	
	public static Item init_mod_item(Item item, String eng_name, String chs_name) {
		item.setItemName(eng_name);
		ModLoader.addName(item, chs_name);
		
		return item;
	}
	
	public static void send_public_notice(World world, String string) {
		if (!world.isRemote) {
			MinecraftServer.getServer().getConfigurationManager().func_92027_k(string);
		}
	}
	
	public static void send_msg_to_player(EntityPlayer player, String string) {
		if (!player.worldObj.isRemote) {
			player.sendChatToPlayer(string);
			// 似乎用 player.addChatMessage(""); 也可以，暂时没看出区别
		}
	}
	
}
