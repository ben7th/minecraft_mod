package mindpin.dongfang;

import java.util.List;
import java.util.Random;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMasterSpark extends Entity
{
	/*
	マスタースパーク
	地形破壊、高火力のレーザーを発射する
	*/
	
	private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private boolean inGround;
    public EntityLiving shootingEntity;//緋想の剣の使用者
	public Entity sourceEntity;//発射元　まず緋想の剣
    private int ticksAlive;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
	protected String texture;
	private ModelRenderer tama;
	public int count;
	public int flagtime;
	public double speed;
	public float size;
	public float oldSize;
	public int endtime;
	public int inicount;

	//ワールド読み込み時に呼び出されるコンストラクト
    public EntityMasterSpark(World par1World)
    {
        super(par1World);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        ticksInAir = 0;
        setSize(1.0F, 1.0F);
    	count = 0;
    	
    }

    protected void entityInit()
    {
    }

    public boolean isInRangeToRenderDist(double d)
    {
        double d1 = boundingBox.getAverageEdgeLength() * 4D;
        d1 *= 64D;
        return d < d1 * d1;
    }

    public EntityMasterSpark(World par1World, Entity entity_s, EntityLiving entity_p, double d, double d1, double d2, float angleY, float angleXZ, double sp, int flagt, float si, int endt, int ini)
    {
        super(par1World);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        ticksInAir = 0;
        shootingEntity = entity_p;
    	sourceEntity = entity_s;
        setSize(10.0F, 10.0F);
        //setLocationAndAngles(entity_s.posX, entity_s.posY, entity_s.posZ, angleY, angleXZ );
        setPosition(entity_s.posX+d*0.2F, entity_s.posY+d1*0.2F, entity_s.posZ+d2*0.2F);
    	rotationYaw = entity_s.rotationYaw;
    	rotationPitch = entity_s.rotationPitch;
        yOffset = 0.0F;//発射地点の高さ　特にイジる必要はなし
    	speed = sp;
    	motionX = -MathHelper.sin(rotationYaw / 180F * 3.141593F) * MathHelper.cos(rotationPitch / 180F * 3.141593F) * speed;
    	motionZ = MathHelper.cos(rotationYaw / 180F * 3.141593F) * MathHelper.cos(rotationPitch / 180F * 3.141593F) * speed;
    	motionY = -MathHelper.sin(rotationPitch / 180F * 3.141593F) * speed;
    	count = 0;
    	flagtime = flagt;
    	if(endt < 20)
    	{
    		endtime = endt;
    	}
    	else
    	{
    		endtime = 20;
    	}
    	size = si;
    	inicount = ini;
    }
	
	public boolean canBePushed()
    {
        return true;
    }

	//マスタースパークが存在する限り呼び出されるメソッド
    public void onUpdate()
    {
        super.onUpdate();

    	/*else if(ticksExisted == 40)//40フレームで自動消滅
    	{
    		setDead();
    	}*/
    	ticksExisted++;
    	//時間停止した際に消えないときの対処用
    	count++;
    	if(count == endtime)
    	{
    		setDead();
    	}
    	
    	flagtime++;
    	oldSize = size;
    	if(count < 10F)
    	{
    		size += 1F - (float)count/10F;//MathHelper.sin((float)count / 30F);
    		setSize(size, size);
    	}
    	
    	
        if (!worldObj.isRemote && (shootingEntity == null || shootingEntity.isDead))
        {
            setDead();
        }
        /*if (inGround)
        {
            int i = worldObj.getBlockId(xTile, yTile, zTile);
            if (i != inTile)
            {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksAlive = 0;
                ticksInAir = 0;
            }
            else
            {
                ticksAlive++;
                if (ticksAlive == 300)
                {
                    setDead();
                }
                return;
            }
        }
        else
        {
            ticksInAir++;
        }*/
        Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);
        Vec3 vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3d, vec3d1);
        vec3d = Vec3.createVectorHelper(posX, posY, posZ);
        vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        if (movingobjectposition != null)
        {
            vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        Entity entity = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand((double)size, (double)size, (double)size));
        double d = 0.0D;
        for (int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            if (!entity1.canBeCollidedWith() || entity1.isEntityEqual(shootingEntity) && ticksInAir < 25 || entity1 instanceof EntityMasterSpark)// || entity1 instanceof EntityMiniHakkero)// || entity1 instanceof EntityExplodeFX)
            {
                continue;
            }
        	float f2 = size;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f2, f2, f2);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
            if (movingobjectposition1 == null)
            {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
            if (d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }

        if (entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }
        if (movingobjectposition != null)
        {
            func_40071_a(movingobjectposition);
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        //float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        //rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        //for (rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        //for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        //for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        //for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        //rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        //rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        //float f1 = 0.95F;
        
        /*motionX += accelerationX;
        motionY += accelerationY;
        motionZ += accelerationZ;
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;*/

		setPosition(posX, posY, posZ);
    }

    protected void func_40071_a(MovingObjectPosition movingobjectposition)
    {
    	//当たった時の処理　　気質弾同士のあたり判定はとらない
    	if (!worldObj.isRemote)
        {
        	if(flagtime > 20 && count < endtime - 5)
        	{
            	if ( movingobjectposition.entityHit != null)
            	{
                	if (!movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, shootingEntity), 6));
            	}
        		MasterSparkExplosion explosion = new MasterSparkExplosion(worldObj, null, posX, posY, posZ, 3.5F);
        		explosion.isFlaming = false;
        		explosion.doExplosionA();
        		explosion.doExplosionB(true);
        	}
        	if ( movingobjectposition.entityHit == null)
        	{
            	setDead();//当たったら消滅
        	}
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)xTile);
        nbttagcompound.setShort("yTile", (short)yTile);
        nbttagcompound.setShort("zTile", (short)zTile);
        nbttagcompound.setByte("inTile", (byte)inTile);
        nbttagcompound.setByte("inGround", (byte)(inGround ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
        inTile = nbttagcompound.getByte("inTile") & 0xff;
        inGround = nbttagcompound.getByte("inGround") == 1;
    }
    
	public float getSize()
	{
		if(flagtime > 20)
    	{
    		return size;
    	}
    	else
    	{
    		return 0.3F;
    	}
	}
	
	public float getOldSize()
	{
		if(flagtime > 20)
    	{
    		return oldSize;
    	}
    	else
    	{
    		return 0.3F;
    	}
	}

    public boolean canBeCollidedWith()
    {
        return false;
    }

    public float getCollisionBorderSize()
    {
		return 10F;
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    public float getEntityBrightness(float f)
    {
        return 1.0F;
    }

    public int getEntityBrightnessForRender(float f)
    {
        return 0xf000f0;
    	//return 0xff0000;
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
}
