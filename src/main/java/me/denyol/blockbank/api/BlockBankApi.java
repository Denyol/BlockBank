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

package me.denyol.blockbank.api;

import me.denyol.blockbank.api.recipe.RecipeCoinForge;
import me.denyol.blockbank.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 26/1/17.
 */
public final class BlockBankApi
{

	/** Types of money that can be put in a vault */
	private static final List<Item> moneyTypes = new ArrayList<>();
	public static final List<RecipeCoinForge> coinForgeRecipes = new ArrayList<>();

	static
	{
		moneyTypes.add(ModItems.goldCoin);
	}

	public static boolean registerCoinForgeRecipe(RecipeCoinForge recipe)
	{
		if(recipe.isValid())
		{
			coinForgeRecipes.add(recipe);
			return true;
		}

		return false;
	}

	public static void addMoneyType(@Nonnull Item item)
	{
		if(!moneyTypes.contains(item))
			moneyTypes.add(item);
	}

	public static boolean hasMoneyType(@Nonnull Item item)
	{
		return moneyTypes.contains(item);
	}

	public enum EnumVaultPartyType implements IStringSerializable
	{
		CASING(0, "casing"),
		WALL(1, "wall"),
		DOOR(2, "door"),
		ACCESS_PANEL(3, "access_panel");

		public int getMetadata()
		{
			return this.meta;
		}

		@Override
		public String toString() {
			return this.name();
		}

		public static EnumVaultPartyType byMetadata(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		private final int meta;
		private final String name;
		private static final EnumVaultPartyType[] META_LOOKUP = new EnumVaultPartyType[values().length];

		EnumVaultPartyType(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		static // set all the types to META_LOOKUP
		{
			for(EnumVaultPartyType type : values())
				META_LOOKUP[type.getMetadata()] = type;
		}
	}
}
