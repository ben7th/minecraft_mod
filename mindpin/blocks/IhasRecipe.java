package mindpin.blocks;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IhasRecipe {	
	public List<MCGRecipe> get_recipes();
	
	public class MCGRecipe {
		public Object[] recipe_params;
		public ItemStack item_stack;
		
		public MCGRecipe(Object[] recipe_params, ItemStack item_stack) {
			this.recipe_params = recipe_params;
			this.item_stack = item_stack;
		}
	}
}
