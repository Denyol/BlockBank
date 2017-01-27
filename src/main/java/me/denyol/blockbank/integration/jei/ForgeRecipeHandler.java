package me.denyol.blockbank.integration.jei;

import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.api.recipe.RecipeCoinForge;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Created by Daniel on 26/1/17.
 */
public class ForgeRecipeHandler implements IRecipeHandler<RecipeCoinForge>
{

	@Override
	public Class<RecipeCoinForge> getRecipeClass()
	{
		return RecipeCoinForge.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return ForgeRecipeCategory.ID;
	}

	@Override
	public String getRecipeCategoryUid(RecipeCoinForge recipe)
	{
		return this.getRecipeCategoryUid();
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(RecipeCoinForge recipe)
	{
		return new ForgeRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(RecipeCoinForge recipe)
	{
		if(!recipe.isValid())
		{
			BlockBank.logger.error("Could not add coin forge recipe with output: " + recipe.getOutput() + " to JEI.");
			return false;
		}

		return true;
	}
}
