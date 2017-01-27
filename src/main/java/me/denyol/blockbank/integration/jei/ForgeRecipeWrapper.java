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

import me.denyol.blockbank.api.recipe.RecipeCoinForge;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
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
