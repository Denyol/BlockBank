package me.denyol.blockbank.api.recipe;

import me.denyol.blockbank.api.BlockBankApi;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by Daniel on 26/1/17.
 */
public class RecipeCoinForge
{
	/** 0: input, 1: mold slot*/
	private final ItemStack[] inputs;
	private final ItemStack output;

	/**
	 * @param inputs an array with the first object being the input, and the last object, the item in the mold slot
	 */
	public RecipeCoinForge(@Nonnull ItemStack[] inputs, @Nonnull ItemStack output)
	{
		this.inputs = inputs;
		this.output = output;
	}

	public ItemStack[] getInputs()
	{
		return inputs;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public boolean isValid()
	{
		return !(inputs[0] == null || inputs[1] == null);
	}

	public static boolean isValidInputMaterial(Item item)
	{
		if(item == null)
			return false;

		for(RecipeCoinForge recipe : BlockBankApi.coinForgeRecipes)
		{
			if(recipe.isValid() && recipe.getInputs()[0].getItem() == item)
				return true;
		}

		return false;
	}

	public static boolean isValidMoldItem(Item item)
	{
		if(item == null)
			return false;

		for(RecipeCoinForge recipe : BlockBankApi.coinForgeRecipes)
		{
			if(recipe.isValid() && recipe.getInputs()[1].getItem() == item)
				return true;
		}

		return false;
	}

}
