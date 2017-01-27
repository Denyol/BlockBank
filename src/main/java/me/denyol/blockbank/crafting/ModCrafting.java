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

package me.denyol.blockbank.crafting;

import me.denyol.blockbank.api.BlockBankApi;
import me.denyol.blockbank.blocks.ModBlocks;
import me.denyol.blockbank.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting
{
	public static void initRegister()
	{
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.unfiredCoinMold), "CCC", "C C", "CCC", 'C', new ItemStack(Items.CLAY_BALL));
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockVaultPart, 1, BlockBankApi.EnumVaultPartyType.CASING.getMetadata()), "OiO", "I I", "OiO", 'I', new ItemStack(Blocks.IRON_BLOCK), 'O', new ItemStack(Blocks.OBSIDIAN), 'i', new ItemStack(Items.IRON_INGOT));
	}
}
