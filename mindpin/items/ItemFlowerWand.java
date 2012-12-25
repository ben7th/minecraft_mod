package mindpin.items;

import mindpin.ClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemFlowerWand extends Item {

	public ItemFlowerWand(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setItemName("item_flower_wand");
		this.setTextureFile(ClientProxy.ITEMS_PNG_PATH);
	}

	// ����Ϊ��Ϊ��
	public void onPlayerStoppedUsing(ItemStack item_stack, World world,
			EntityPlayer player, int left_ticks_count) {
		int spend_ticks_count = this.getMaxItemUseDuration(item_stack)
				- left_ticks_count;

		ArrowLooseEvent event = new ArrowLooseEvent(player, item_stack,
				spend_ticks_count);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		spend_ticks_count = event.charge;

		System.out.println(spend_ticks_count + "," + spend_ticks_count * 0.05f);

		boolean can_shot = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(
						Enchantment.infinity.effectId, item_stack) > 0;

		if (can_shot || player.inventory.hasItem(Item.arrow.shiftedIndex)) {
			float seconds_count = spend_ticks_count / 20.0F;
			seconds_count = (seconds_count * seconds_count + seconds_count * 2.0F) / 3.0F;

			seconds_count = 1.0F;

			EntityArrow arrow = new EntityArrow(world, player,
					seconds_count * 2.0F);
			arrow.setIsCritical(true);
			arrow.setFire(100);

			item_stack.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F
					/ (itemRand.nextFloat() * 0.4F + 1.2F) + seconds_count
					* 0.5F);

			if (can_shot) {
				arrow.canBePickedUp = 2;
			} else {
				player.inventory.consumeInventoryItem(Item.arrow.shiftedIndex);
			}

			if (!world.isRemote) {
				world.spawnEntityInWorld(arrow);
			}
		}
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack item_stack, World world,
			EntityPlayer player) {
		ArrowNockEvent event = new ArrowNockEvent(player, item_stack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return event.result; // ����д�������������ʱ�Զ�ʩ�ŵ�bug
		}

		if (player.capabilities.isCreativeMode
				|| player.inventory.hasItem(Item.arrow.shiftedIndex)) {
			player.setItemInUse(item_stack,
					this.getMaxItemUseDuration(item_stack));
		}

		return item_stack;
	}
}
