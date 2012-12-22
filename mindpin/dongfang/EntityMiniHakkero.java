package mindpin.dongfang;

import mindpin.ModMindpin;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityMiniHakkero extends Entity
{
	
	//ミニ八卦炉
	
	private EntityLiving shootingEntity;
	public int count;
	private int count2;
	private float circleAngle;
	private int mode;
	private int moveTexture;
	public int num;
	private int damage;

	//ワールド読み込み時に呼び出されるコンストラクト
    public EntityMiniHakkero(World par1World)
    {
        super(par1World);
        preventEntitySpawning = true;
        setSize(0.4F, 0.4F);//サイズを設定　平面上の横と奥行きサイズ、高さ
        yOffset = 0.0F;//高さを設定
    	//mode = 1;
    	//shootingEntity = null;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    /*protected boolean canTriggerWalking()
    {
        return false;
    }*/

	//？
    protected void entityInit()
    {
        dataWatcher.addObject(17, new Integer(0));
        dataWatcher.addObject(18, new Integer(1));
        dataWatcher.addObject(19, new Integer(0));
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB getCollisionBox(Entity par1Entity)
    {
        return par1Entity.boundingBox;
    }

    /**
     * returns the bounding box for this entity
     */
    public AxisAlignedBB getBoundingBox()
    {
        return boundingBox;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }

	//出現時に呼び出されるコンストラクト
    public EntityMiniHakkero(World par1World,EntityLiving entityLiving, int da)
    {
        this(par1World);
    	 
    	setSize(0.5F, 0.5F);//ワールド読み込み時のコンストラクトと同じ
        yOffset = 0.0F;//同上
    	prevPosX = entityLiving.posX;
        prevPosY = entityLiving.posY;
        prevPosZ = entityLiving.posZ;
        setPosition(entityLiving.posX - MathHelper.sin(entityLiving.rotationYaw / 180F * 3.141593F),
        			entityLiving.posY - MathHelper.sin(entityLiving.rotationPitch / 180F * 3.141593F)-0.7D,
					entityLiving.posZ + MathHelper.cos(entityLiving.rotationYaw / 180F * 3.141593F));//初期位置を設定(x,y,z)
    	rotationYaw = entityLiving.rotationYaw;
    	rotationPitch = entityLiving.rotationPitch;
		//mode = pmode;
    	shootingEntity = entityLiving;//使用者をshootingEntityに保存
    	count = 0;
    	count2 = 27;
    	circleAngle = 0F;
    	moveTexture = 0;
    	damage = da;
//    	worldObj.playSoundAtEntity(this, "random.masterspark", mod_thKaguya.MasterSparkVol, 1.0F);
    	worldObj.playSoundAtEntity(this, "random.masterspark", 0.5F, 1.0F);
    }
	

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
    	return false;
    }
	
	//Entityの消滅処理
	private void finish()
	{
		if(shootingEntity instanceof EntityPlayer)
		{
			EntityPlayer entityPlayer = (EntityPlayer)shootingEntity;
			if(entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModMindpin.item_mini_hakkero, 1, damage)) == false)
			{
				//インベントリが一杯なら使用者の目の前でアイテム化
				//（クリエイティブでは常にインベントリには物が吸収される仕様があるらしく、クリエイティブでは落とさない（false判定がでない)）
				//entityPlayer.dropItemWithOffset(mod_thKaguya.hisouItem.shiftedIndex, 1, 0.0F);
				entityDropItem(new ItemStack(ModMindpin.item_mini_hakkero.shiftedIndex, 1, damage), 0.0F);
			}
		}
		else
		{
			entityDropItem(new ItemStack(ModMindpin.item_mini_hakkero.shiftedIndex, 1, damage), 0.0F);
		}
		setDead();
	}

    /**
     * Called to update the entity's position/logic.
     */
	//Entityが存在する限り毎フレーム呼び出されるメソッド
    public void onUpdate()
    {
        super.onUpdate();
    	
    	/*if(num >= 1  &&  !worldObj.isRemote)
    	{
    		EntityHisou entityHisou;
    		entityHisou = this;
    		entityHisou.count = 0;
    		entityHisou.count --;
    		//entityHisou.num;
         	worldObj.spawnEntityInWorld(entityHisou);
    	}*/
    	circleAngle += 4.7F;
    		

        if (getTimeSinceHit() > 0)
        {
            setTimeSinceHit(getTimeSinceHit() - 1);
        }

        if (getDamageTaken() > 0)
        {
            setDamageTaken(getDamageTaken() - 1);
        }
    	if(moveTexture++ >= 16)
    	{
    		moveTexture = 0;
    	}

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        int i = 5;
        double d = 0.0D;

        for (int j = 0; j < i; j++)
        {
            double d2 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(j + 0)) / (double)i) - 0.125D;
            double d8 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(j + 1)) / (double)i) - 0.125D;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(boundingBox.minX, d2, boundingBox.minZ, boundingBox.maxX, d8, boundingBox.maxZ);

            if (worldObj.isAABBInMaterial(axisalignedbb, Material.water))
            {
                d += 1.0D / (double)i;
            }
        }
    	
    	double d1 = Math.sqrt(motionX * motionX + motionZ * motionZ);
    	
    	
    	MovingObjectPosition movingobjectposition = new MovingObjectPosition(this);
    	if (!worldObj.isRemote && movingobjectposition.entityHit instanceof EntityPlayer == false && movingobjectposition.entityHit instanceof EntityMiniHakkero == false)
        {
            /*if ( movingobjectposition.entityHit != null)
            {
                if (!movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this,shootingEntity), 6));
            }*/
        	worldObj.createExplosion(null, posX, posY, posZ, 1.0F, true);
           	setDead();
        }

        if (d < 1.0D)
        {
            double d6 = d * 2D - 1.0D;
            motionY += 0.039999999105930328D * d6;
        }
        else
        {
            if (motionY < 0.0D)
            {
                motionY /= 2D;
            }

            motionY += 0.0070000002160668373D;
        }

        double d7 = 0.40000000000000002D;

        //rotationPitch = 0.0F;
        double d12 = rotationYaw;
        double d15 = prevPosX - posX;
        double d18 = prevPosZ - posZ;
    	
    	//rotationPitch -= 23.0F;
    	//rotationPitch = ((float)count+(float)num/3.0F) * 39.0F;
    	//rotationPitch = rotationPitch + 23.0F * (float)(4-num);
    	if(shootingEntity != null)//shootingEntityが存在するなら、それをぬるぬる追尾する
    	{
    		/*float angleXZ,angleY, speed;
    		float disXZ;
    		double px, py, pz;
			px = shootingEntity.posX + MathHelper.cos((shootingEntity.rotationYaw+90F)/180F*3.141593F)*1.2F;
    		py = shootingEntity.posY - MathHelper.sin((shootingEntity.rotationPitch/180F*3.141593F))*1.2F;
    		pz = shootingEntity.posZ + MathHelper.sin((shootingEntity.rotationYaw+90F)/180f*3.141593F)*1.2F;
    		disXZ = (float)Math.sqrt( (px-posX)*(px-posX) + (pz-posZ)*(pz-posZ) );
    		angleXZ = (float)Math.atan2(pz-posZ, px-posX);
    		angleY  = (float)Math.atan2(disXZ, py-posY);
    		speed = (0.25F+((float)num * 0.1F)) * (float)Math.sqrt( (px-posX)*(px-posX) + (py-posY)*(py-posY) + (pz-posZ)*(pz-posZ) );//離れるほど速くなる
    		setPosition(
    			posX + Math.cos(angleXZ)*speed,
    			posY + Math.cos(angleY )*speed,
    			posZ + Math.sin(angleXZ)*speed);
    		rotationYaw = shootingEntity.rotationYaw;*/
    		
    		/*shootingEntity.motionX -= (shootingEntity.posX - shootingEntity.prevPosX)*0.6D;
    		shootingEntity.motionZ -= (shootingEntity.posZ - shootingEntity.prevPosZ)*0.6D;
    		shootingEntity.rotationYawHead = shootingEntity.prevRotationYawHead + (shootingEntity.rotationYawHead - shootingEntity.prevRotationYawHead)*0.2F;
    		shootingEntity.rotationYaw = shootingEntity.prevRotationYaw + (shootingEntity.rotationYaw - shootingEntity.prevRotationYaw)*0.2F;
    		*/
    	}
    	//else//使用者がいないならその場でアイテム化
    	else{
    		entityDropItem(new ItemStack(ModMindpin.item_mini_hakkero.shiftedIndex, 1, damage), 0.0F);
    		setDead();
    	}
    		

        if (d15 * d15 + d18 * d18 > 0.001D)
        {
            d12 = (float)((Math.atan2(d18, d15) * 180D) / Math.PI);
        }

        double d20;

        //rotationYaw += d20;
    	//rotationPitch = 10.0F;
    	
        //setRotation(rotationYaw, rotationPitch);
    	
    	//***********　停止空間（時計じゃなくてもっと大きな見えない壁）とEntityの当たり判定を取る　よくわからん****************//
    	//Entity entity = null;
        //List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(MathHelper.sin((float)boatYaw / 180.0F * 3.141593F) * 20.0D, 20.0D,MathHelper.cos((float)boatYaw / 180.0F * 3.141593F)* 20.0D));
    	//List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(20.0D, 20.0D,20.0D));
    	//List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.2D, 1.2D,1.2D));//(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        /*if (list != null && list.size() > 0)
        {
            for (int j1 = 0; j1 < list.size(); j1++)
            {
                entity = (Entity)list.get(j1);
                if (entity.canBePushed() && (entity instanceof EntityPrivateSquare))
                {
                    entity.applyEntityCollision(this);
                }
            	if (entity != null )
        		{
            		movingobjectposition = new MovingObjectPosition(entity);
        		}
        		if (movingobjectposition != null && entity instanceof EntityPrivateSquare == false && movingobjectposition.entityHit != shootingEntity)
        		{
            		func_40071_a(movingobjectposition);//一番下あたりにあるメソッド。ダメージを与える処理とかしてる
        		}
            }
        }*/      
    	

        //************************************************************************************//
	//****マスパの発射****//
	if(shootingEntity != null)
	{
		double ax, ay, az, dx, dy, dz;
		float angleXZ, angleY;
		
		dx = posX - shootingEntity.posX;
		dy = posY - shootingEntity.posY;
		dz = posZ - shootingEntity.posZ;
		
		angleY  = (float)Math.atan2(dx, dz); 
		angleXZ = (float)Math.atan2( Math.sqrt(dx*dx + dz*dz), dy);
		ay = -MathHelper.sin(shootingEntity.rotationPitch/180F * 3.141593F);//Y方向　上下
		ax = -MathHelper.sin(shootingEntity.rotationYaw/180F * 3.141593F) * MathHelper.cos(shootingEntity.rotationPitch/180F * 3.141593F);//X方向　水平方向
		az =  MathHelper.cos(shootingEntity.rotationYaw/180F * 3.141593F) * MathHelper.cos(shootingEntity.rotationPitch/180F * 3.141593F);//Z方向　水平方向
		EntityMasterSpark entityMasterSpark;
		//EntityMasterSpark entityMasterSpark2;
		//entityMasterSpark = new EntityMasterSpark(worldObj, this, shootingEntity, ax, ay, az, angleY, angleXZ, 1.0D, count, 0.1F);
		entityMasterSpark = new EntityMasterSpark(worldObj, this, shootingEntity, ax*2.5D, ay*2.5D, az*2.5D, rotationYaw, rotationPitch, 1.6D, count, 0.0F, 100-count, count);
		//entityMasterSpark2 = new EntityMasterSpark(worldObj, this, shootingEntity, ax*2.2D, ay*2.2D, az*2.2D, rotationYaw, rotationPitch, 1.6D, count, 0.1F, 100-count);
		worldObj.spawnEntityInWorld(entityMasterSpark);
		//worldObj.spawnEntityInWorld(entityMasterSpark2);
	}
	//********//
		
		//時間で消滅
	if(count >= 99)
	{
		finish();
	}
	else
	{
		count++;
	}
}
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    	nbttagcompound.setShort("count", (short)count);
    	nbttagcompound.setShort("count2", (short)count2);
    	nbttagcompound.setShort("damage", (short)damage);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    	count = nbttagcompound.getShort("count");
    	count2 = nbttagcompound.getShort("count2");
    	damage = nbttagcompound.getShort("damage");
    }

    public float getShadowSize()
    {
        return 0.5F;
    }
    
    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(int par1)
    {
        dataWatcher.updateObject(19, Integer.valueOf(par1));
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public int getDamageTaken()
    {
        return dataWatcher.getWatchableObjectInt(19);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int par1)
    {
        dataWatcher.updateObject(17, Integer.valueOf(par1));
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit()
    {
        return dataWatcher.getWatchableObjectInt(17);
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void setForwardDirection(int par1)
    {
        dataWatcher.updateObject(18, Integer.valueOf(par1));
    }
	
	protected void func_40071_a(MovingObjectPosition movingobjectposition)
    {

    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getForwardDirection()
    {
        return dataWatcher.getWatchableObjectInt(18);
    }
    
    protected boolean isValidLightLevel()
    {
        return true;
    }
    
    public int getBrightnessForRender(float par1)
    {
        return 0xf000f0;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        return 1.0F;
    }
    
	public float getCircleAngle()
	{
		return circleAngle;
	}
}
