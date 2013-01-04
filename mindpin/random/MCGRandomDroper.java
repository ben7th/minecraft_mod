package mindpin.random;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * 用来创造随机掉落的物品列表
 */
public class MCGRandomDroper {
	private List<Drop> drop_list;
	private int total_rate;
	public String label;
	
	public MCGRandomDroper(String label) {
		this.drop_list = new ArrayList<Drop>();
		this.label = label;
	}
	
	public MCGRandomDroper add_item_stack(int drop_rate, ItemStack item_stack) {
		this.total_rate += drop_rate;
		this.drop_list.add(new Drop(drop_rate, item_stack));
		return this;
	}
	
	public ArrayList<ItemStack> get_drop() {
		final ArrayList<ItemStack> re = new ArrayList<ItemStack>();
		
		MCGRandomSwitcher rs = new MCGRandomSwitcher(total_rate, label + "：掉落");
		for (final Drop d : drop_list) {
			rs.add_handler(new MCGRandomHandler(d.drop_rate, d.item_stack.getDisplayName()) {
				@Override
				public void handle() {
					// 必须调用 copy 方法来复制一份，否则重复同一种就不再掉落了
					re.add(d.item_stack.copy());
				}
			});
		}
		
		rs.run();
		
		return re;
	}
	
	private class Drop {
		int drop_rate;
		ItemStack item_stack;

		private Drop(int drop_rate, ItemStack item_stack) {
			this.drop_rate = drop_rate;
			this.item_stack = item_stack;
		}
	}
}
