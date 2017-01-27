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
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Daniel on 26/1/17.
 */
public class ForgeRecipeCategory extends BlankRecipeCategory<ForgeRecipeWrapper>
{

	private IDrawable background;
	//private IDrawable icon;
	private final IIngredientRegistry ingredientRegistry;

	public static final String ID = BlockBank.MOD_ID + ".forgeRecipeCategory";

	public ForgeRecipeCategory(IGuiHelper helper, IIngredientRegistry ingredientRegistry)
	{
		background = helper.createDrawable(new ResourceLocation(BlockBank.MOD_ID, "textures/gui/CoinForgeContainer.png"), 35, 16, 106, 54);
		//icon = helper.createDrawable(new ResourceLocation(BlockBank.MOD_ID, "textures/blocks/coin_forge_front_active.png"), 0 ,0 ,16, 16);
		this.ingredientRegistry = ingredientRegistry;
	}

	@Nonnull
	@Override
	public String getUid()
	{
		return ID;
	}

	@Nonnull
	@Override
	public String getTitle() {
		return I18n.format("container.tile_entity_coin_forge");
	}

	@Nonnull
	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ForgeRecipeWrapper recipeWrapper, IIngredients ingredients)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 0);
		guiItemStacks.init(1, true, 26, 18);
		guiItemStacks.init(2, true, 0, 36);
		guiItemStacks.init(3, false, 84, 18);

		guiItemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));
		guiItemStacks.set(1, ingredients.getInputs(ItemStack.class).get(1));
		guiItemStacks.set(2, ingredientRegistry.getFuels());
		guiItemStacks.set(3, ingredients.getOutputs(ItemStack.class).get(0));
	}

	@Nullable
	@Override
	public IDrawable getIcon()
	{
		//return icon;
		return null;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {
		return;
	}

	@Override
	public void drawAnimations(@Nonnull Minecraft minecraft) {
		return;
	}
}
