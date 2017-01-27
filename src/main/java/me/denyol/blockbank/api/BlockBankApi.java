package me.denyol.blockbank.api;

import me.denyol.blockbank.api.recipe.RecipeCoinForge;
import net.minecraft.util.IStringSerializable;

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

	public static enum EnumVaultPartyType implements IStringSerializable
	{
		CASING(0, "casing"),
		WALL(1, "wall");

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

		private EnumVaultPartyType(int meta, String name)
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
