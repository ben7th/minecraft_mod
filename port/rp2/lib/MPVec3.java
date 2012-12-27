package port.rp2.lib;

import net.minecraft.util.Vec3;

public class MPVec3 extends Vec3 {
	
	public MPVec3() {
		super(vec3dPool, 0, 0, 0);
	}
	
	public MPVec3(MPVec3 v) {
		super(vec3dPool, v.xCoord, v.yCoord, v.zCoord);
	}
	
	public MPVec3(double x, double y, double z) {
		super(vec3dPool, x, y, z);
	}

	public void set(double x, double y, double z) {
		super.setComponents(x, y, z);
	}
	
	public void set(MPVec3 v) {
		set(v.xCoord, v.yCoord, v.zCoord);
	}
	
	public void add(double x, double y, double z) {
		this.xCoord += x;
		this.yCoord += y;
		this.zCoord += z;
	}
	
	public void add(MPVec3 v) {
		add(v.xCoord, v.yCoord, v.zCoord);
	}
	
	public void multiply(double d) {
		this.xCoord *= d;
		this.yCoord *= d;
		this.zCoord *= d;
	}
}
