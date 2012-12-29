package mindpin.rp2.lib.math3d;



public class MathLib {
	private static Matrix3[] orientMatrixList = new Matrix3[24];
	private static Quat[] orientQuatList = new Quat[24];

	public static void orientMatrix(Matrix3 m, int down, int rot) {
		m.set(orientMatrixList[(down * 4 + rot)]);
	}

	public static Quat orientQuat(int down, int rot) {
		return new Quat(orientQuatList[(down * 4 + rot)]);
	}

	static {
		Quat q2 = Quat.aroundAxis(1.0D, 0.0D, 0.0D, 3.141592653589793D);
		for (int j = 0; j < 4; j++) {
			Quat q1 = Quat
					.aroundAxis(0.0D, 1.0D, 0.0D, -1.570796326794897D * j);
			orientQuatList[j] = q1;

			q1 = new Quat(q1);
			q1.multiply(q2);
			orientQuatList[(j + 4)] = q1;
		}

		for (int i = 0; i < 4; i++) {
			int k = (i >> 1 | i << 1) & 0x3;
			q2 = Quat.aroundAxis(0.0D, 0.0D, 1.0D, 1.570796326794897D);
			q2.multiply(Quat.aroundAxis(0.0D, 1.0D, 0.0D,
					1.570796326794897D * (k + 1)));
			for (int j = 0; j < 4; j++) {
				Quat q1 = new Quat(orientQuatList[j]);
				q1.multiply(q2);
				orientQuatList[(8 + 4 * i + j)] = q1;
			}
		}
		for (int i = 0; i < 24; i++)
			orientMatrixList[i] = new Matrix3(orientQuatList[i]);
	}
}
