package mindpin.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class BaseUtils {
	
	/**
	 * 根据传入的 world 实例，返回当前玩家的 EntityPlayer 实例
	 * 目前这样写可能比较绕，而且是否能在SMP环境生效，需要验证
	 */
	public static EntityPlayer get_current_entity_player_from_world(World world) {
		String player_name = ModLoader.getMinecraftInstance().thePlayer.getEntityName();
		return world.getPlayerEntityByName(player_name);
	}
	
	/**
	 * 返回当前玩家的 EntityPlayer 实例
	 */
	public static EntityPlayer get_current_entity_player() {
		String player_name = ModLoader.getMinecraftInstance().thePlayer.getEntityName();
		return get_current_world().getPlayerEntityByName(player_name);
	}
	
	public static World get_current_world() {
		return ModLoader.getMinecraftInstance().theWorld;
	}
}
