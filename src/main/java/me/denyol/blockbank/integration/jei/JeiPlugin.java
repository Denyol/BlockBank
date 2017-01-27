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

import me.denyol.blockbank.api.BlockBankApi;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

import javax.annotation.Nonnull;

@JEIPlugin
public class JeiPlugin extends BlankModPlugin
{

	@Override
	public void register(@Nonnull IModRegistry registry)
	{
		registry.addRecipeCategories(new ForgeRecipeCategory(registry.getJeiHelpers().getGuiHelper(), registry.getIngredientRegistry()));

		registry.addRecipeHandlers(new ForgeRecipeHandler());

		registry.addRecipes(BlockBankApi.coinForgeRecipes);
	}

}
