package mindpin.blocks;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import mindpin.MindpinClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockLucky extends Block {

	public BlockLucky(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);

		this.setBlockName("block_lucky"); // 名称 tile.lucky_block
		this.setTextureFile(MindpinClientProxy.BLOCKS_PNG_PATH); // 贴图

		this.setCreativeTab(CreativeTabs.tabBlock); // 置于创造模式的　Block 页签
		this.setStepSound(soundStoneFootstep); // 放置音效
		this.setLightValue(1);
	}

	@Override
	public boolean canRenderInPass(int pass) {
		return true;
	}
	
	private AudioInputStream ais;
	private SourceDataLine sdl;
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		
		if(null != ais) return false;
		
		try {
			File file = new File("../eclipse/Minecraft/bin/mindpin/gui/80.mp3");
			ais = AudioSystem.getAudioInputStream(file);
			AudioFormat af = ais.getFormat();
			
			if(af.getEncoding() != AudioFormat.Encoding.PCM_SIGNED){
				af = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED, 
					af.getSampleRate(), 
					16, 
					af.getChannels(), 
					af.getChannels() * 2,
					af.getSampleRate(),
					false);
				ais = AudioSystem.getAudioInputStream(af, ais);
			}
			
			DataLine.Info di = new DataLine.Info(
                    SourceDataLine.class, af,
                    AudioSystem.NOT_SPECIFIED);
			
			sdl = (SourceDataLine) AudioSystem.getLine(di);
			sdl.open(af);
			sdl.start();
			
			Thread play_thread = new Thread(){
				@Override
				public void run() {
					
					try{
					
						byte temp_buffer[] = new byte[320];
						int cnt;
						
						while ((cnt = ais.read(temp_buffer, 0, temp_buffer.length)) != -1) {
			                if (cnt > 0) {
			                    // 写入缓存数据
			                    sdl.write(temp_buffer, 0, cnt);
			                }
			            }
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			};
			
			play_thread.start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
