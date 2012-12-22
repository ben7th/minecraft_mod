package mindpin.dongfang;
import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMiniHakkero extends ModelBase
{
	
	//
	Random random = new Random();
	
    public ModelRenderer miniHakkero[];

    public ModelMiniHakkero()
    {
    	/*Minecraftのモデルの基本事項
    	基本的にテクスチャに64x32の画像を使用
    	ボックスタイプのモデルしか扱えない
    	ボックスのサイズ＝テクスチャのサイズと考えていい
    	例えば横幅が10の箱ならば、そこに設定されるテクスチャも10ドット分使用する
    	横幅6、高さ4、奥行き3の箱を考えたとき、イメージとしてはテクスチャはこのようになる
    	000000111111222222000000
    	000000111111222222000000
    	000000111111222222000000
    	333333444444555555666666
    	333333444444555555666666
    	333333444444555555666666
    	333333444444555555666666
    	0は未使用部と考えて、ボックス型は6面存在するためこのように６つの部分に分けれれることになる。
    	この配置は人間の頭で言うと、
    	1は頭の上　2は頭の下　3は頭右　4は顔　5は頭左　6は頭後ろにあたる
    	一番左上にあたる座標はModelRenderer(this,x,y)で設定可能。
    	*/
    	
        miniHakkero = new ModelRenderer[10];
    	//持つ場所
        miniHakkero[8] = new ModelRenderer(this, 0, 15);//テクスチャ座標0,0を左上にする
    	
    	//ボックスを追加する。　当たり判定にも使われる？（Entity側で制御してるかも）
    	//addBox(X座標、Z座標、Y座標、X方向のサイズ、Z方向のサイズ、Y方向のサイズ、？（倍率？）)
    	//サイズはテクスチャのサイズそのもの
    	//watchs[i].addBox(-15, -15, -2, 30,30 , 2, 0.0F);
    	miniHakkero[8].addBox(-8, -8, 0, 16, 16 , 1, 0.0F);
    	miniHakkero[9] = new ModelRenderer(this, 0, 15);
    	miniHakkero[9].addBox(-8, -8,  5, 16, 16 , 1, 0.0F);
    	//miniHakkero[1].rotateAngleX = ((float)Math.PI);
    	
    	float pi8 = (float)Math.PI / 4.0F;
    	float angle = 0.0F;
    	int xPos[] = {  0, 6, 12,  6,   0, -6, -12, -6};
    	int yPos[] = { 12, 6,  0, -6, -12, -6,   0,  6};
    	//側面
    	for(int i = 0; i < 8; i++)
    	{
    		miniHakkero[i] = new ModelRenderer(this, 32, 0);
    		//miniHakkero[i].addBox( 0, -7, -3, 8, 2 , 6, 0.0F);
    		//miniHakkero[i].setRotationPoint(4.0F, -12.0F, 0.0F);
    		miniHakkero[i].addBox( -4, -10, 0, 8, 2 , 6, 0.0F);
    		miniHakkero[i].setRotationPoint(0.0F, 0.0F, 0.0F);
    		//miniHakkero[i].rotateAngleY = (float)Math.PI / 2.0F;
    		//miniHakkero
    		miniHakkero[i].rotateAngleZ = angle;
    		//miniHakkero[i].rotateAngleX = angle;
    		//miniHakkero[i].rotateAngleY = (float)Math.PI / 2.0F;
    		//miniHakkero[i].setRotationPoint(0.0F, 12.0F, 0.0F);
    		//miniHakkero[i].rotateAngleX += (float)Math.PI / 2.0F;
    		angle += pi8;
    	}
    	/*float pi32 = (float)Math.PI / 16.0F;
    	for(int i = 10; i < 42; i++)
    	{
    		miniHakkero[i] = new ModelRenderer(this, 0, 8);
    		miniHakkero[i].addBox( 40, -20, -2, 15, 1 , 4, 0.0F);
    		//miniHakkero[i].setRotationPoint(4.0F, -12.0F, 0.0F);
    		//miniHakkero[i].rotateAngleY = (float)Math.PI / 2.0F;
    		//miniHakkero
    		miniHakkero[i].rotateAngleX = angle;
    		miniHakkero[i].rotateAngleY = (float)Math.PI / 2.0F;
    		//miniHakkero[i].setRotationPoint(0.0F, 12.0F, 0.0F);
    		//miniHakkero[i].rotateAngleX += (float)Math.PI / 2.0F;
    		angle += pi32;
    	}*/
    	//把手
    	//miniHakkero[3] = new ModelRenderer(this, 32, 0);
    	//miniHakkero[3].addBox(-2, -20, 0,  8, 12 , 0, 0.0F);
    	//hisou[2].rotateAngleX = ((float)Math.PI / 6F * (float)i);
    	//フタ
    	/*watch[4] = new ModelRenderer(this, 48, 16);
    	watch[4].addBox(-8, -8, 0, 16,16 , 0, 0.0F);
        watch[4].setRotationPoint(0.0F, -16.0F, -4.0F);
        watch[4].rotateAngleX = ((float)Math.PI / 6F);*/
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        for (int i = 0; i < 10; i++)
    	{
    		miniHakkero[i].render(par7);//描画　par7は気にしなくていいと思う
    	}
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
    }
}
