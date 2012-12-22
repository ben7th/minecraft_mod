package mindpin.dongfang;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MasterSparkExplosion extends Explosion
{
    /** whether or not the explosion sets fire to blocks around it */
	private World worldObj2;

    public MasterSparkExplosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9)
    {
    	super(par1World, par2Entity, par3, par5, par7, par9);
    	worldObj2 = par1World;
    }

    /**
     * Does the first part of explosion (destroy blocks)
     */
    public void doExplosionA()
    {
        float f = explosionSize;
        int i = 16;

        for (int j = 0; j < i; j++)
        {
            for (int l = 0; l < i; l++)
            {
                label0:

                for (int j1 = 0; j1 < i; j1++)
                {
                    if (j != 0 && j != i - 1 && l != 0 && l != i - 1 && j1 != 0 && j1 != i - 1)
                    {
                        continue;
                    }

                    double d = ((float)j / ((float)i - 1.0F)) * 2.0F - 1.0F;
                    double d1 = ((float)l / ((float)i - 1.0F)) * 2.0F - 1.0F;
                    double d2 = ((float)j1 / ((float)i - 1.0F)) * 2.0F - 1.0F;
                    double d3 = Math.sqrt(d * d + d1 * d1 + d2 * d2);
                    d /= d3;
                    d1 /= d3;
                    d2 /= d3;
                    float f1 = explosionSize * (0.7F + worldObj2.rand.nextFloat() * 0.6F);
                    double d5 = explosionX;
                    double d7 = explosionY;
                    double d9 = explosionZ;
                    float f2 = 0.3F;

                    do
                    {
                        if (f1 <= 0.0F)
                        {
                            continue label0;
                        }

                        int l2 = MathHelper.floor_double(d5);
                        int i3 = MathHelper.floor_double(d7);
                        int j3 = MathHelper.floor_double(d9);
                        int k3 = worldObj2.getBlockId(l2, i3, j3);

                        if (k3 > 0)
                        {
                            f1 -= (Block.blocksList[k3].getExplosionResistance(exploder) + 0.3F) * f2;
                        }

                        if (f1 > 0.0F)
                        {
                        	affectedBlockPositions.add(new ChunkPosition(l2, i3, j3));
                        }

                        d5 += d * (double)f2;
                        d7 += d1 * (double)f2;
                        d9 += d2 * (double)f2;
                        f1 -= f2 * 0.75F;
                    }
                    while (true);
                }
            }
        }

        explosionSize *= 2.0F;
        int k = MathHelper.floor_double(explosionX - (double)explosionSize - 1.0D);
        int i1 = MathHelper.floor_double(explosionX + (double)explosionSize + 1.0D);
        int k1 = MathHelper.floor_double(explosionY - (double)explosionSize - 1.0D);
        int l1 = MathHelper.floor_double(explosionY + (double)explosionSize + 1.0D);
        int i2 = MathHelper.floor_double(explosionZ - (double)explosionSize - 1.0D);
        int j2 = MathHelper.floor_double(explosionZ + (double)explosionSize + 1.0D);
        List list = worldObj2.getEntitiesWithinAABBExcludingEntity(exploder, AxisAlignedBB.getBoundingBox(k, k1, i2, i1, l1, j2));
        Vec3 vec3d = Vec3.createVectorHelper(explosionX, explosionY, explosionZ);

        for (int k2 = 0; k2 < list.size(); k2++)
        {
            Entity entity = (Entity)list.get(k2);
            double d4 = entity.getDistance(explosionX, explosionY, explosionZ) / (double)explosionSize;

            if (d4 <= 1.0D)
            {
                double d6 = entity.posX - explosionX;
                double d8 = entity.posY - explosionY;
                double d10 = entity.posZ - explosionZ;
                double d11 = MathHelper.sqrt_double(d6 * d6 + d8 * d8 + d10 * d10);
                d6 /= d11;
                d8 /= d11;
                d10 /= d11;
                double d12 = worldObj2.getBlockDensity(vec3d, entity.boundingBox);
                double d13 = (1.0D - d4) * d12;
                entity.attackEntityFrom(DamageSource.explosion, (int)(((d13 * d13 + d13) / 2D) * 8D * (double)explosionSize + 1.0D));
                /*double d14 = d13;
                entity.motionX += d6 * d14;
                entity.motionY += d8 * d14;
                entity.motionZ += d10 * d14;*/
            }
        }

        explosionSize = f;
        ArrayList arraylist = new ArrayList();
        arraylist.addAll(affectedBlockPositions);
    }
}
