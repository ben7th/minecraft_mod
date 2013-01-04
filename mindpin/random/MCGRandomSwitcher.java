package mindpin.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 事件概率发生器，用来处理游戏中需要进行随机判定的逻辑，如幸运方块被敲碎时，四叶草神符被使用时
 * 需要以一个总概率分母来进行构造
 * 需要传入一组回调钩子实例，以及为每个实例指定概率分子
 * 当调用 run() 方法时，根据每个概率分子占分母的比率产生来随机选择执行哪一个回调
 */
public class MCGRandomSwitcher {
	
	private List<MCGRandomHandler> handlers; // 回调集合
	private int total_rate; // 总概率分母
	public String label;
	
	public MCGRandomSwitcher(int total_rate, String label) {
		this.handlers = new ArrayList<MCGRandomHandler>();
		this.total_rate = total_rate;
		this.label = label;
	}
	
	public void add_handler(MCGRandomHandler handler) {
		this.handlers.add(handler);
	}
	
	public void run() {		
		int result_value = new Random().nextInt(total_rate);
		
		for (MCGRandomHandler handler : handlers) {
			result_value -= handler.rate;
			if (result_value <= 0) {
				System.out.println("概率触发：" + label + "," + handler.label + "," + handler.rate + "/" + total_rate);
				handler.handle();
				return;
			}
		}
	}
}
