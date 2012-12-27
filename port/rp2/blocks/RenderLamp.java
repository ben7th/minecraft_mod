package port.rp2.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import port.rp2.ClientProxy;
import port.rp2.lib.RenderContext;
import port.rp2.lib.RenderCustomBlock;
import port.rp2.lib.RenderLib;

public class RenderLamp extends RenderCustomBlock {	
	int light_color = 0xFFF100;
	
	RenderContext context;

	public RenderLamp(Block bl) {
		super(bl);
		this.context = new RenderContext();
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k,
			Random random) {
	}

	@Override
	public void renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iba,
			int i, int j, int k, int md) {
		boolean lit = ((BlockLamp) this.block).lit;

		this.context.setPos(i, j, k);
		this.context.setOrientation(0, 0);
		this.context.readGlobalLights(iba, i, j, k);

		if (MinecraftForgeClient.getRenderPass() != 1) {
			float f = this.block.getBlockBrightness(iba, i, j, k);
			if (lit) f = 1.0F;

			this.context.startWorldRender(renderblocks);
			this.context.bindTexture(ClientProxy.RP2_LIGHTTING_PATH);

			this.context.setSize(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			
			this.context.setTexFlags(0);
			this.context.setupBox();
			this.context.transform();
			
			if (lit) {
				this.context.setTint(f, f, f);
				this.context.setLocalLights(1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
				this.context.setTex(0);

				this.context.doMappingBox(63);
				this.context.doLightLocal(63);
				this.context.renderFlat(63);
			} else {
				this.context.setTint(1.0F, 1.0F, 1.0F);
				this.context.setLocalLights(0.5F, 1.0F, 0.8F, 0.8F, 0.6F, 0.6F);
				this.context.setTex(0);

				this.context.renderGlobFaces(63);
			}
			
			this.context.unbindTexture();
			this.context.endWorldRender();
			return;
		}
		if (!lit)
			return;

		RenderLib.bindTexture(ClientProxy.RP2_LIGHTTING_PATH, 1);

		int tc = this.light_color;
		
		this.context.setTint(
				(tc >> 16) / 255.0F, 
				(tc >> 8 & 0xFF) / 255.0F,
				(tc & 0xFF) / 255.0F);

		this.context.setLocalLights(1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
		this.context.setSize(-0.05D, -0.05D, -0.05D, 1.05D, 1.05D, 1.05D);
		
		this.context.setupBox();
		this.context.transform();
		this.context.setSize(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		
		this.context.doMappingBox(63);
		this.context.doLightLocal(63);
		this.context.renderAlpha(63, 0.38F);

		RenderLib.unbindTexture();
	}

	@Override
	public void renderInvBlock(RenderBlocks renderblocks, int md) {
		Tessellator tessellator = Tessellator.instance;
		this.block.setBlockBoundsForItemRender();

		this.context.setPos(-0.5D, -0.5D, -0.5D);
		this.context.setTint(1.0F, 1.0F, 1.0F);
		this.context.setLocalLights(0.5F, 1.0F, 0.8F, 0.8F, 0.6F, 0.6F);
		
		this.context.setTex(0);
		
		this.context.setOrientation(0, 0);

		this.context.setSize(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		this.context.calcBounds();

		this.context.bindTexture(ClientProxy.RP2_LIGHTTING_PATH);
		tessellator.startDrawingQuads();
		this.context.useNormal = true;
		this.context.renderFaces(63);
		this.context.useNormal = false;
		tessellator.draw();
		this.context.unbindTexture();
	}
}
