package me.denyol.blockbank.integration.jei;

import me.denyol.blockbank.api.recipe.RecipeCoinForge;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

import java.util.List;

/**
 * Created by Daniel on 26/1/17.
 */
public class ForgeRecipeWrapper extends BlankRecipeWrapper
{

	private final List<ItemStack> inputs;
	private final ItemStack output;

	public ForgeRecipeWrapper(RecipeCoinForge recipe)
	{
		this.inputs = Arrays.asList(recipe.getInputs());
		this.output = recipe.getOutput();
	}

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInputs(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, output);
	}

	@Override
	public List<ItemStack> getInputs()
	{
		return inputs;
	}
}
