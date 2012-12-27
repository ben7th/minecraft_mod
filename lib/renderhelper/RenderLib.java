package lib.renderhelper;

import lib.math3d.Quat;
import lib.math3d.Vector3;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderLib {
	private static RenderListEntry[] renderers = new RenderListEntry[4096];

	public static void bindTexture(String tex, int subid) {
		ForgeHooksClient.bindTexture(tex, subid);
	}

	public static void bindTexture(String tex) {
		ForgeHooksClient.bindTexture(tex, 0);
	}

	public static void unbindTexture() {
		ForgeHooksClient.unbindTexture();
	}

	// public static void setIntredTexture() {
	// bindTexture("/eloraam/logic/logic1.png");
	// }
	//
	// public static void setRedPowerTexture() {
	// bindTexture("/eloraam/wiring/redpower1.png");
	// }

	public static void setDefaultTexture() {
		unbindTexture();
	}

	public static void renderSpecialLever(Vector3 pos, Quat rot, int tex) {
		int k1 = (tex & 0xF) << 4;
		int l1 = tex & 0xF0;

		Vector3[] pl = new Vector3[8];
		float f8 = 0.0625F;
		float f9 = 0.0625F;
		float f10 = 0.375F;

		pl[0] = new Vector3(-f8, 0.0D, -f9);
		pl[1] = new Vector3(f8, 0.0D, -f9);
		pl[2] = new Vector3(f8, 0.0D, f9);
		pl[3] = new Vector3(-f8, 0.0D, f9);
		pl[4] = new Vector3(-f8, f10, -f9);
		pl[5] = new Vector3(f8, f10, -f9);
		pl[6] = new Vector3(f8, f10, f9);
		pl[7] = new Vector3(-f8, f10, f9);

		for (int i = 0; i < 8; i++) {
			rot.rotate(pl[i]);
			pl[i].add(pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D);
		}

		float u1 = (k1 + 7) / 256.0F;
		float u2 = (k1 + 9 - 0.01F) / 256.0F;
		float v1 = (l1 + 6) / 256.0F;
		float v2 = (l1 + 8 - 0.01F) / 256.0F;

		addVectWithUV(pl[0], u1, v2);
		addVectWithUV(pl[1], u2, v2);
		addVectWithUV(pl[2], u2, v1);
		addVectWithUV(pl[3], u1, v1);

		addVectWithUV(pl[7], u1, v2);
		addVectWithUV(pl[6], u2, v2);
		addVectWithUV(pl[5], u2, v1);
		addVectWithUV(pl[4], u1, v1);

		u1 = (k1 + 7) / 256.0F;
		u2 = (k1 + 9 - 0.01F) / 256.0F;
		v1 = (l1 + 6) / 256.0F;
		v2 = (l1 + 12 - 0.01F) / 256.0F;

		addVectWithUV(pl[1], u1, v2);
		addVectWithUV(pl[0], u2, v2);
		addVectWithUV(pl[4], u2, v1);
		addVectWithUV(pl[5], u1, v1);

		addVectWithUV(pl[2], u1, v2);
		addVectWithUV(pl[1], u2, v2);
		addVectWithUV(pl[5], u2, v1);
		addVectWithUV(pl[6], u1, v1);

		addVectWithUV(pl[3], u1, v2);
		addVectWithUV(pl[2], u2, v2);
		addVectWithUV(pl[6], u2, v1);
		addVectWithUV(pl[7], u1, v1);

		addVectWithUV(pl[0], u1, v2);
		addVectWithUV(pl[3], u2, v2);
		addVectWithUV(pl[7], u2, v1);
		addVectWithUV(pl[4], u1, v1);
	}

	public static void addVectWithUV(Vector3 vect, double u, double v) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertexWithUV(vect.x, vect.y, vect.z, u, v);
	}

	public static void renderPointer(Vector3 pos, Quat rot) {
		Tessellator tessellator = Tessellator.instance;
		double u = 0.390625D;
		double v = 0.015625D;
		double uvd = 0.0312109375D;
		double uvd2 = 0.0077734375D;

		tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);

		Vector3[] pl = new Vector3[8];
		pl[0] = new Vector3(0.4D, 0.0D, 0.0D);
		pl[1] = new Vector3(0.0D, 0.0D, 0.2D);
		pl[2] = new Vector3(-0.2D, 0.0D, 0.0D);
		pl[3] = new Vector3(0.0D, 0.0D, -0.2D);
		pl[4] = new Vector3(0.4D, 0.1D, 0.0D);
		pl[5] = new Vector3(0.0D, 0.1D, 0.2D);
		pl[6] = new Vector3(-0.2D, 0.1D, 0.0D);
		pl[7] = new Vector3(0.0D, 0.1D, -0.2D);

		for (int i = 0; i < 8; i++) {
			rot.rotate(pl[i]);
			pl[i].add(pos);
		}

		addVectWithUV(pl[0], u, v);
		addVectWithUV(pl[1], u + uvd, v);
		addVectWithUV(pl[2], u + uvd, v + uvd);
		addVectWithUV(pl[3], u, v + uvd);

		addVectWithUV(pl[4], u, v);
		addVectWithUV(pl[7], u, v + uvd);
		addVectWithUV(pl[6], u + uvd, v + uvd);
		addVectWithUV(pl[5], u + uvd, v);

		tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);

		addVectWithUV(pl[0], u + uvd2, v);
		addVectWithUV(pl[4], u, v);
		addVectWithUV(pl[5], u, v + uvd);
		addVectWithUV(pl[1], u + uvd2, v + uvd);

		addVectWithUV(pl[0], u, v + uvd2);
		addVectWithUV(pl[3], u + uvd, v + uvd2);
		addVectWithUV(pl[7], u + uvd, v);
		addVectWithUV(pl[4], u, v);

		addVectWithUV(pl[2], u + uvd, v + uvd - uvd2);
		addVectWithUV(pl[6], u + uvd, v + uvd);
		addVectWithUV(pl[7], u, v + uvd);
		addVectWithUV(pl[3], u, v + uvd - uvd2);

		addVectWithUV(pl[2], u + uvd, v + uvd - uvd2);
		addVectWithUV(pl[1], u, v + uvd - uvd2);
		addVectWithUV(pl[5], u, v + uvd);
		addVectWithUV(pl[6], u + uvd, v + uvd);
	}

	public static RenderCustomBlock getRenderer(int bid, int md) {
		RenderListEntry rle = renderers[bid];
		if (rle == null)
			return null;
		return rle.metaRenders[md];
	}

	public static RenderCustomBlock getInvRenderer(int bid, int md) {
		RenderListEntry rle = renderers[bid];
		if (rle == null)
			return null;
		int mdv = rle.mapDamageValue(md);
		if (mdv > 15)
			return rle.defaultRender;
		return rle.metaRenders[rle.mapDamageValue(md)];
	}

	private static RenderCustomBlock makeRenderer(Block bl, Class<?> rcl) {
		try {
			return (RenderCustomBlock) rcl.getDeclaredConstructor(
					new Class[] { Block.class }).newInstance(
					new Object[] { bl });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setRenderer(Block bl, Class<?> rcl) {
		RenderCustomBlock rnd = makeRenderer(bl, rcl);
		if (renderers[bl.blockID] == null) {
			renderers[bl.blockID] = new RenderListEntry();
		}
		for (int i = 0; i < 16; i++)
			renderers[bl.blockID].metaRenders[i] = rnd;
	}

	public static void setRenderer(Block bl, int md, Class<?> rcl) {
		RenderCustomBlock rnd = makeRenderer(bl, rcl);
		if (renderers[bl.blockID] == null) {
			renderers[bl.blockID] = new RenderListEntry();
		}
		renderers[bl.blockID].metaRenders[md] = rnd;
	}

	public static void setHighRenderer(Block bl, int md, Class<?> rcl) {
		RenderCustomBlock rnd = makeRenderer(bl, rcl);
		if (renderers[bl.blockID] == null) {
			renderers[bl.blockID] = new RenderShiftedEntry(8);
		}
		renderers[bl.blockID].metaRenders[md] = rnd;
	}

	public static void setDefaultRenderer(Block bl, int shift, Class<?> rcl) {
		RenderCustomBlock rnd = makeRenderer(bl, rcl);
		if (renderers[bl.blockID] == null) {
			renderers[bl.blockID] = new RenderShiftedEntry(shift);
		}
		for (int i = 0; i < 16; i++) {
			if (renderers[bl.blockID].metaRenders[i] == null)
				renderers[bl.blockID].metaRenders[i] = rnd;
		}
		renderers[bl.blockID].defaultRender = rnd;
	}

	public static void setShiftedRenderer(Block bl, int md, int shift,
			Class<?> rcl) {
		RenderCustomBlock rnd = makeRenderer(bl, rcl);
		if (renderers[bl.blockID] == null) {
			renderers[bl.blockID] = new RenderShiftedEntry(shift);
		}
		renderers[bl.blockID].metaRenders[md] = rnd;
	}

	private static class RenderShiftedEntry extends RenderLib.RenderListEntry {
		public int shift;

		public RenderShiftedEntry(int sh) {
			this.shift = sh;
		}

		@Override
		public int mapDamageValue(int dmg) {
			return dmg >> this.shift;
		}

	}

	private static class RenderListEntry {
		public RenderCustomBlock[] metaRenders = new RenderCustomBlock[16];
		RenderCustomBlock defaultRender;

		public int mapDamageValue(int dmg) {
			return dmg;
		}
	}
}
