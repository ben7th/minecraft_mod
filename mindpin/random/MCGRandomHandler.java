package mindpin.random;

public abstract class MCGRandomHandler {
	public int rate;
	public String label;
	
	public MCGRandomHandler(int rate, String label) {
		this.rate = rate;
		this.label = label;
	}
	
	abstract public void handle();
}
