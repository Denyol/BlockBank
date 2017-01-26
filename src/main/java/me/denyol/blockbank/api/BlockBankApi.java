package me.denyol.blockbank.api;

import me.denyol.blockbank.api.recipe.RecipeCoinForge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 26/1/17.
 */
public final class BlockBankApi
{

	public static final List<RecipeCoinForge> coinForgeRecipes = new ArrayList<>();

	public static boolean registerCoinForgeRecipe(RecipeCoinForge recipe)
	{
		if(recipe.isValid())
		{
			coinForgeRecipes.add(recipe);
			return true;
		}

		return false;
	}
}
