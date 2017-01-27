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

package me.denyol.blockbank.items.crafting;

import me.denyol.blockbank.api.BlockBankApi;
import me.denyol.blockbank.api.recipe.RecipeCoinForge;
import me.denyol.blockbank.crafting.ModCrafting;
import me.denyol.blockbank.crafting.ModFurnaceCrafting;
import me.denyol.blockbank.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by Daniel on 26/1/17.
 */
public class ModCraftingRecipes
{

	public static RecipeCoinForge goldCoin = new RecipeCoinForge(new ItemStack[] {new ItemStack(Items.GOLD_INGOT), new ItemStack(ModItems.coinMold)}, new ItemStack(ModItems.goldCoin));

	public static void init()
	{
		ModFurnaceCrafting.initRegister();
		ModCrafting.initRegister();

		BlockBankApi.registerCoinForgeRecipe(goldCoin);
	}
}
