package mindpin.dongfang;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemMiniHakkero extends Item
{

	/*
	ミニ八卦炉
	おなじみ、マスタースパークの撃てるあれ
	*/
	
	
    public ItemMiniHakkero(int par1)
    {
        super(par1);
    	maxStackSize = 1;
    	
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setItemName("item_mini_hakkero");
    }
	
	
	//右クリックを押したときに呼び出されるメソッド
	@Override
   	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	int i;
    	int count = 0;

    	
    	//ミニ八卦炉の設置地点にブロックがないことを確認
    	if(world.isAirBlock((int)(entityplayer.posX - MathHelper.sin(entityplayer.rotationYaw   / 180F * 3.141593F)),
    						(int)(entityplayer.posY - MathHelper.sin(entityplayer.rotationPitch / 180F * 3.141593F)-0.7D),
    						(int)(entityplayer.posZ + MathHelper.cos(entityplayer.rotationYaw   / 180F * 3.141593F))) )
    	{
    		//ファイアーチャージを16個消費
    		for( i = 0; i < 32; i ++)
    		{
    			if (entityplayer.inventory.hasItem(Item.fireballCharge.shiftedIndex))
        		{
        			entityplayer.inventory.consumeInventoryItem(Item.fireballCharge.shiftedIndex);
        			count++;
        		}
    		}
    		//32個ないなら消費した分を元に戻して何もしない
    		if(count < 32)
    		{
    			if(count != 0)
    			{
    				entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.fireballCharge, count, 0));
    			}
    			return itemstack;
    		}
    		
    		EntityMiniHakkero entityMiniHakkero;
    		
    		entityMiniHakkero = new EntityMiniHakkero(world, entityplayer, itemstack.getItemDamageForDisplay());
       		if(!world.isRemote)
       		{
        			world.spawnEntityInWorld(entityMiniHakkero);//ミニ八卦炉を出す
       		}
    		itemstack.stackSize--;//スタックから消滅させる
    	}
    		
       	return itemstack;
    }
	
}
