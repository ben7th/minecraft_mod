package mindpin.blocks;

import mindpin.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class BlockMusicPlayer extends Block {

	public BlockMusicPlayer(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);

		this.setBlockName("block_music_player");
		this.setTextureFile(ClientProxy.BLOCKS_PNG_PATH); // ��ͼ

		this.setCreativeTab(CreativeTabs.tabBlock); // ���ڴ���ģʽ�ġ�Block ҳǩ
		this.setStepSound(soundStoneFootstep); // ������Ч
		this.setLightValue(1);
	}
	
	@Override
	public boolean onBlockActivated(World world, int par2, int par3,
			int par4, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		
		ModLoader.getMinecraftInstance().displayGuiScreen(new GuiBlockMusicPlayer());
		
		return true;
	}
	
	public class GuiBlockMusicPlayer extends GuiScreen {
		@Override
		public void initGui() {
			GuiButton prev = new GuiButton(1, (width - 175) / 2 + 20, (height - 165) / 2 + 60, 40, 20, "��һ��");
	        controlList.add(prev);
		}
		
		@Override
		public void drawScreen(int par1, int par2, float par3) {
			drawDefaultBackground();
			fontRenderer.drawString("���ֲ�����", (width - 175) / 2 + 6, (height - 165) / 2 + 6, 0x404040);
		}
	}
}
