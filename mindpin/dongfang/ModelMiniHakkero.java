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
    	/*Minecraft�̃��f���̊�{����
    	��{�I�Ƀe�N�X�`����64x32�̉摜���g�p
    	�{�b�N�X�^�C�v�̃��f�����������Ȃ�
    	�{�b�N�X�̃T�C�Y���e�N�X�`���̃T�C�Y�ƍl���Ă���
    	�Ⴆ�Ή�����10�̔��Ȃ�΁A�����ɐݒ肳���e�N�X�`����10�h�b�g���g�p����
    	����6�A����4�A���s��3�̔����l�����Ƃ��A�C���[�W�Ƃ��Ă̓e�N�X�`���͂��̂悤�ɂȂ�
    	000000111111222222000000
    	000000111111222222000000
    	000000111111222222000000
    	333333444444555555666666
    	333333444444555555666666
    	333333444444555555666666
    	333333444444555555666666
    	0�͖��g�p���ƍl���āA�{�b�N�X�^��6�ʑ��݂��邽�߂��̂悤�ɂU�̕����ɕ������邱�ƂɂȂ�B
    	���̔z�u�͐l�Ԃ̓��Ō����ƁA
    	1�͓��̏�@2�͓��̉��@3�͓��E�@4�͊�@5�͓����@6�͓����ɂ�����
    	��ԍ���ɂ�������W��ModelRenderer(this,x,y)�Őݒ�\�B
    	*/
    	
        miniHakkero = new ModelRenderer[10];
    	//���ꏊ
        miniHakkero[8] = new ModelRenderer(this, 0, 15);//�e�N�X�`�����W0,0������ɂ���
    	
    	//�{�b�N�X��ǉ�����B�@�����蔻��ɂ��g����H�iEntity���Ő��䂵�Ă邩���j
    	//addBox(X���W�AZ���W�AY���W�AX�����̃T�C�Y�AZ�����̃T�C�Y�AY�����̃T�C�Y�A�H�i�{���H�j)
    	//�T�C�Y�̓e�N�X�`���̃T�C�Y���̂���
    	//watchs[i].addBox(-15, -15, -2, 30,30 , 2, 0.0F);
    	miniHakkero[8].addBox(-8, -8, 0, 16, 16 , 1, 0.0F);
    	miniHakkero[9] = new ModelRenderer(this, 0, 15);
    	miniHakkero[9].addBox(-8, -8,  5, 16, 16 , 1, 0.0F);
    	//miniHakkero[1].rotateAngleX = ((float)Math.PI);
    	
    	float pi8 = (float)Math.PI / 4.0F;
    	float angle = 0.0F;
    	int xPos[] = {  0, 6, 12,  6,   0, -6, -12, -6};
    	int yPos[] = { 12, 6,  0, -6, -12, -6,   0,  6};
    	//����
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
    	//�c��
    	//miniHakkero[3] = new ModelRenderer(this, 32, 0);
    	//miniHakkero[3].addBox(-2, -20, 0,  8, 12 , 0, 0.0F);
    	//hisou[2].rotateAngleX = ((float)Math.PI / 6F * (float)i);
    	//�t�^
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
    		miniHakkero[i].render(par7);//�`��@par7�͋C�ɂ��Ȃ��Ă����Ǝv��
    	}
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
    }
}
