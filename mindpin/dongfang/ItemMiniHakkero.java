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
	�~�j���T�F
	���Ȃ��݁A�}�X�^�[�X�p�[�N�̌��Ă邠��
	*/
	
	
    public ItemMiniHakkero(int par1)
    {
        super(par1);
    	maxStackSize = 1;
    	
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setItemName("item_mini_hakkero");
    }
	
	
	//�E�N���b�N���������Ƃ��ɌĂяo����郁�\�b�h
	@Override
   	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	int i;
    	int count = 0;

    	
    	//�~�j���T�F�̐ݒu�n�_�Ƀu���b�N���Ȃ����Ƃ��m�F
    	if(world.isAirBlock((int)(entityplayer.posX - MathHelper.sin(entityplayer.rotationYaw   / 180F * 3.141593F)),
    						(int)(entityplayer.posY - MathHelper.sin(entityplayer.rotationPitch / 180F * 3.141593F)-0.7D),
    						(int)(entityplayer.posZ + MathHelper.cos(entityplayer.rotationYaw   / 180F * 3.141593F))) )
    	{
    		//�t�@�C�A�[�`���[�W��16����
    		for( i = 0; i < 32; i ++)
    		{
    			if (entityplayer.inventory.hasItem(Item.fireballCharge.shiftedIndex))
        		{
        			entityplayer.inventory.consumeInventoryItem(Item.fireballCharge.shiftedIndex);
        			count++;
        		}
    		}
    		//32�Ȃ��Ȃ������������ɖ߂��ĉ������Ȃ�
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
        			world.spawnEntityInWorld(entityMiniHakkero);//�~�j���T�F���o��
       		}
    		itemstack.stackSize--;//�X�^�b�N������ł�����
    	}
    		
       	return itemstack;
    }
	
}
