package port.rp2.blocks;

import java.util.List;
import java.util.Random;

import mindpin.MCMind;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import port.rp2.RP2;
import port.rp2.RPlib;

public class BlockLamp extends Block {
	public boolean lit;
	public boolean powered;
	public int onID;
	public int offID;

	public BlockLamp(int i, boolean l, boolean p) {
		//super(i, CoreLib.materialRedpower);		
		super(i, new Material(MapColor.woodColor));
		
		setBlockName("block_lamp");
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//		setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F);
		setHardness(0.5F);
		setCreativeTab(MCMind.TAB_LUCKY);
		setLightValue(1.0f);
		
		this.lit = l;
		this.powered = p;
	}

	@Override
	public boolean canRenderInPass(int n) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	public boolean isACube() {
		return true;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return this.offID;
	}

	private void checkPowerState(World world, int i, int j, int k) {
		// RedPowerLib -> RPlib
		
		if ((!this.powered)
				&& (RPlib.isPowered(world, i, j, k, 16777215, 63))) {
			int md = world.getBlockId(i, j, k);
			world.setBlockAndMetadataWithNotify(i, j, k, this.onID, md);
			world.markBlockForUpdate(i, j, k);
		} else if ((this.powered)
				&& (!RPlib.isPowered(world, i, j, k, 16777215, 63))) {
			int md = world.getBlockId(i, j, k);
			world.setBlockAndMetadataWithNotify(i, j, k, this.offID, md);
			world.markBlockForUpdate(i, j, k);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		checkPowerState(world, i, j, k);
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		checkPowerState(world, i, j, k);
	}

	@Override
	public int getRenderType() {		
//		return RedPowerCore.customBlockModel;
//		int customBlockModel = -1;
		return RP2.customBlockModel;
	}

	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, @SuppressWarnings("rawtypes") List par3List) {
	}
}
