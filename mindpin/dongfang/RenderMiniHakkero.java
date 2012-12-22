package mindpin.dongfang;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class RenderMiniHakkero extends Render
{
	
	/*
	ミニ八卦炉の描画
	*/
	
    /** instance of ModelBoat for rendering */
    protected ModelBase modelMiniHakkero;

    public RenderMiniHakkero()
    {
        shadowSize = 0.5F;//多分影のサイズ
        modelMiniHakkero = new ModelMiniHakkero();
    }

    /**
     * The render method used in RenderBoat that renders the boat model.
     */
    public void renderMiniHakkero(EntityMiniHakkero par1EntityMiniHakkero, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
    	//GL11.glDisable(GL11.GL_LIGHTING);
    	//GL11.glEnable(GL11.GL_BLEND);
    	//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        //GL11.glRotatef(180F - par8, 0.0F, 1.0F, 0.0F);
    	GL11.glRotatef(par1EntityMiniHakkero.rotationPitch, -(float)Math.sin((par1EntityMiniHakkero.rotationYaw-90F)/180F * 3.141593F), 0.0F, (float)Math.cos((par1EntityMiniHakkero.rotationYaw-90F)/180F * 3.141593F));
    	GL11.glRotatef(180F - par1EntityMiniHakkero.rotationYaw, 0.0F, 1.0F, 0.0F);
    	//GL11.glRotatef( 90F, 1.0F, 0.0F, 0.0F);
    	//GL11.glRotatef(par1EntityMiniHakkero.rotationPitch,0.0F, 1.0F, 0.0F);
    	//GL11.glRotatef(180F - par1EntityMiniHakkero.rotationYaw, -(float)Math.sin(par1EntityMiniHakkero.rotationYaw/180F * 3.141593F), 0.0F, (float)Math.cos(par1EntityMiniHakkero.rotationYaw/180F * 3.141593F));
        float f = (float)par1EntityMiniHakkero.getTimeSinceHit() - par9;
        float f1 = (float)par1EntityMiniHakkero.getDamageTaken() - par9;

        /*if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f > 0.0F)
        {
            GL11.glRotatef(((MathHelper.sin(f) * f * f1) / 10F) * (float)par1EntityPrivateSquare.getForwardDirection(), 0.0F, 0.0F, 0.0F);
        }*/

        loadTexture("/terrain.png");
        //float f2 = 3.5F;
        //GL11.glScalef(f2, f2, f2);
        //GL11.glScalef(1.0F / f2, 1.0F / f2, 1.0F / f2);
        loadTexture("/mindpin/dongfang/MiniHakkeroTexture.png");//テクスチャ画像を読み込み
        GL11.glScalef(0.5F, 0.5F, 0.5F);//倍率　縦方向 高さ　幅
    	//if(par1EntityHisou.num != 8)
    	//{

    		//GL11.glColor4f(1.0F, 1.0F, 1.0F, (float)par1EntityMiniHakkero.num * 0.125F);
    	//}
    	modelMiniHakkero.render(par1EntityMiniHakkero, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);//最後の引数以外関係ない？　最後の引数は大きさの倍率
    	GL11.glDisable(GL11.GL_LIGHTING);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    	Tessellator tessellator = Tessellator.instance;
    	float constR[] = { 255F, 255F, 255F,   0F,   0F,  76F, 128F};
    	float constG[] = {   0F, 165F, 255F, 128F,   0F,   0F,   0F};
    	float constB[] = {   0F,   0F,   0F,   0F, 255F, 130F, 128F};
    	float u_min  = 0F;
    	float v_min  = 0F;
    	float u_max  = 1F / 2F;
    	float v_max  = 1F / 4F;
    	
    	/*float down_u_min = 0F;
    	float down_v_min = 1F / 2F;
    	float down_u_max = 1F / 2F;
    	float down_v_max = 1F / 2F;*/
    	float angle = par1EntityMiniHakkero.getCircleAngle();
    	float pi18 = (float)Math.PI / 9F;
    	int c = par1EntityMiniHakkero.count;
    	GL11.glRotatef((float)Math.sin(angle) / 3.141593F * 180F, 0.0F, 0.0F, 1.0F);
    	//GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
    	//GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
    	for(int i = 0; i < 19; i++)
    	{
    		//GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
    		//手前リング
    		GL11.glRotatef((float)Math.sin(pi18) / 3.141593F * 180F, 0.0F, 0.0F, 1.0F);
	    	tessellator.startDrawingQuads();
    		tessellator.setColorRGBA_F(constR[c % 7]/255F * 0.5F,constG[c % 7]/255F * 0.5F, constB[c % 7]/255F * 0.5F, 0.6F);
		    tessellator.addVertexWithUV(  0.77F,  4.5F, -6.0D, u_min, v_min);
	        tessellator.addVertexWithUV( -0.77F,  4.5F, -6.0D, u_max, v_min);
	        tessellator.addVertexWithUV( -0.77F,   4.5F,  -4.0, u_max, v_max);
	        tessellator.addVertexWithUV(  0.77F,   4.5F,  -4.0D, u_min, v_max);
	        tessellator.draw();
    		tessellator.startDrawingQuads();
    		tessellator.setColorRGBA_F(constR[c % 7]/255F * 0.5F,constG[c % 7]/255F * 0.5F, constB[c % 7]/255F * 0.5F, 0.6F);
	        tessellator.addVertexWithUV( -0.77F,  4.5F, -6.0D, u_min, v_min);
	        tessellator.addVertexWithUV(  0.77F,  4.5F, -6.0D, u_max, v_min);
	        tessellator.addVertexWithUV(  0.77F,   4.5F,  -4.0D, u_max, v_max);
	        tessellator.addVertexWithUV( -0.77F,   4.5F,  -4.0D, u_min, v_max);
    		tessellator.draw();
    		//奥リング
    		//GL11.glRotatef((float)Math.sin(pi18) / 3.141593F * 180F, 0.0F, 0.0F, 1.0F);
	    	tessellator.startDrawingQuads();
    		tessellator.setColorRGBA_F(constR[(c+3) % 7]/255F * 0.5F,constG[(c+3) % 7]/255F * 0.5F, constB[(c+3) % 7]/255F * 0.5F, 0.6F);
		    tessellator.addVertexWithUV(  1.11F,  6.5F, -12.0D, u_min, v_min);
	        tessellator.addVertexWithUV( -1.11F,  6.5F, -12.0D, u_max, v_min);
	        tessellator.addVertexWithUV( -1.11F,   6.5F, -10.0D, u_max, v_max);
	        tessellator.addVertexWithUV(  1.11F,   6.5F, -10.0D, u_min, v_max);
	        tessellator.draw();
    		tessellator.startDrawingQuads();
    		tessellator.setColorRGBA_F(constR[(c+3) % 7]/255F * 0.5F,constG[(c+3) % 7]/255F * 0.5F, constB[(c+3) % 7]/255F * 0.5F, 0.6F);
	        tessellator.addVertexWithUV( -1.11F,  6.5F, -12.0D, u_min, v_min);
	        tessellator.addVertexWithUV(  1.11F,  6.5F, -12.0D, u_max, v_min);
	        tessellator.addVertexWithUV(  1.11F,   6.5F, -10.0D, u_max, v_max);
	        tessellator.addVertexWithUV( -1.11F,   6.5F, -10.0D, u_min, v_max);
	        tessellator.draw();
    		angle += pi18;
    	}
    	
        
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        renderMiniHakkero((EntityMiniHakkero)par1Entity, par2, par4, par6, par8, par9);
    }
}
