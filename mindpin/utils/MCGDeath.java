package mindpin.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class MCGDeath {
	public static void kill(EntityPlayer player, String death_message) {
		MCGDamage damage = new MCGDamage(death_message);
		player.attackEntityFrom(damage, 9999);
		if (player.capabilities.isCreativeMode) {
			MCGUtils.send_msg_to_player(player, "创造模式 " + damage.getDeathMessage(player));
		}
	}
	
	private static class MCGDamage extends DamageSource {

		private String death_message;
		
		protected MCGDamage(String death_message) {
			super("mcgeek_damage");
			this.death_message = death_message;
		}
		
		@Override
		public String getDeathMessage(EntityPlayer player) {
			return "§c" + player.getEntityName() + " " + death_message;
		}
	}
	
}
