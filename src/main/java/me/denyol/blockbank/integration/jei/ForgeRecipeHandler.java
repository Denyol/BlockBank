/*
 * <BlockBank Minecraft Forge economy mod>
 *     Copyright (C) <2017>  <Daniel Tucker>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.denyol.blockbank.integration.jei;

import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.api.recipe.RecipeCoinForge;
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
