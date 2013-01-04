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
	
	private List<MCGRandomHandler> handlers = new ArrayList<MCGRandomHandler>(); // 回调集合
	private int rate_sum = 0; // 总概率分母
	public String label;
	
	public MCGRandomSwitcher(String label) {
		this.label = label;
	}
	
	public void add_handler(MCGRandomHandler handler) {
		this.rate_sum += handler.rate;
		this.handlers.add(handler);
	}
	
	/**
	 * 算法解说：
	 * 例如， total_rate = 7
	 * new Random().nextInt(total_rate) + 1; 的结果 会是 1, 2, 3, 4, 5, 6, 7
	 * 
	 * 回调 A.rate, B.rate, C.rate = 2, 3, 2
	 * 随机结果 1, 2: 执行 A
	 * 随机结果 3, 4, 5: 执行 B
	 * 随机结果 6, 7: 执行 C
	 * 
	 * 则 ABC 的执行几率符合预期
	 */
	public void run() {		
		int rand_value = new Random().nextInt(rate_sum) + 1; // 1 ~ rate_sum
		
		for (MCGRandomHandler handler : handlers) {
			rand_value -= handler.rate;
			if (rand_value <= 0) {
				System.out.println("概率触发：" + label + "," + handler.label + "," + handler.rate + "/" + rate_sum);
				handler.handle();
				return;
			}
		}
	}
}
