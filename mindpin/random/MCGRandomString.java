package mindpin.random;

import java.util.Random;

public class MCGRandomString {
	String[] strs;
	
	public MCGRandomString(String[] strs) {
		this.strs = strs;
	}
	
	public String get_string() {
		return strs[new Random().nextInt(strs.length)];
	}
}
