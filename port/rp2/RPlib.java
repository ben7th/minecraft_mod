package port.rp2;

import net.minecraft.world.IBlockAccess;

public class RPlib {

	public static boolean isPowered(IBlockAccess iba, int i, int j, int k,
			int cons, int indside) {
//		if (((cons & 0x1111100) > 0) && (isWeakPoweringTo(iba, i, j - 1, k, 0)))
//			return true;
//		if (((cons & 0x2222200) > 0) && (isWeakPoweringTo(iba, i, j + 1, k, 1)))
//			return true;
//		if (((cons & 0x4440011) > 0) && (isWeakPoweringTo(iba, i, j, k - 1, 2)))
//			return true;
//		if (((cons & 0x8880022) > 0) && (isWeakPoweringTo(iba, i, j, k + 1, 3)))
//			return true;
//		if (((cons & 0x10004444) > 0)
//				&& (isWeakPoweringTo(iba, i - 1, j, k, 4)))
//			return true;
//		if (((cons & 0x20008888) > 0)
//				&& (isWeakPoweringTo(iba, i + 1, j, k, 5)))
//			return true;
//		if (((indside & 0x1) > 0) && (isPoweringTo(iba, i, j - 1, k, 0)))
//			return true;
//		if (((indside & 0x2) > 0) && (isPoweringTo(iba, i, j + 1, k, 1)))
//			return true;
//		if (((indside & 0x4) > 0) && (isPoweringTo(iba, i, j, k - 1, 2)))
//			return true;
//		if (((indside & 0x8) > 0) && (isPoweringTo(iba, i, j, k + 1, 3)))
//			return true;
//		if (((indside & 0x10) > 0) && (isPoweringTo(iba, i - 1, j, k, 4)))
//			return true;
//		if (((indside & 0x20) > 0) && (isPoweringTo(iba, i + 1, j, k, 5)))
//			return true;
//		return false;
		return true;
	}

//	public static boolean isWeakPoweringTo(IBlockAccess iba, int i, int j,
//			int k, int l) {
//		int bid = iba.a(i, j, k);
//		if (bid == 0)
//			return false;
//		if ((searching) && (bid == amq.ay.cm))
//			return false;
//		if (amq.p[bid].b(iba, i, j, k, l))
//			return true;
//		if ((l > 1) && (bid == amq.ay.cm) && (amq.p[bid].b(iba, i, j, k, 1))) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isPoweringTo(IBlockAccess iba, int i, int j, int k, int l) {
//		int bid = iba.a(i, j, k);
//		if (bid == 0)
//			return false;
//		if (amq.p[bid].b(iba, i, j, k, l))
//			return true;
//		if ((iba.t(i, j, k)) && (isStrongPowered(iba, i, j, k, l))) {
//			return true;
//		}
//		if ((l > 1) && (bid == amq.ay.cm)) {
//			if (searching)
//				return false;
//			if (amq.p[bid].b(iba, i, j, k, 1))
//				return true;
//		}
//		return false;
//	}

}
