package me.denyol.blockbank.items.crafting;

import com.google.common.collect.Maps;
import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * Created by Daniel on 22/1/17.
 */
public class CoinForgeRecipes
{
					//input      output
	private final Map<ItemStack, ItemStack> forgingList = Maps.<ItemStack, ItemStack>newHashMap();

	public static CoinForgeRecipes getInstance()
	{
		return new CoinForgeRecipes();
	}

	public CoinForgeRecipes()
	{
		this.addForgingRecipe(new ItemStack(Items.GOLD_INGOT), new ItemStack(ModItems.goldCoin));
	}

	public void addForgingRecipe(ItemStack input, ItemStack output)
	{
		if(getForgeResult(input) != null)
		{
			BlockBank.logger.info("Ignored duplicate forging recipe with input: " + input + "=" + output);
			return;
		}
		this.forgingList.put(input, output);
	}

	public ItemStack getForgeResult(ItemStack input)
	{
		if(input == null)
			return null;

		for(Map.Entry<ItemStack, ItemStack> entry : this.forgingList.entrySet())
		{
			if (input.getItem() == ((ItemStack)entry.getKey()).getItem())
			{
				return (ItemStack)entry.getValue();
			}
		}

		return null;
	}

	public boolean isValidInput(ItemStack input)
	{
		return input != null && isValidInput(input.getItem());

	}

	public boolean isValidInput(Item input)
	{
		if(input == null)
			return false;

		for(Map.Entry<ItemStack, ItemStack> entry : this.forgingList.entrySet())
		{
			if (input == ((ItemStack)entry.getKey()).getItem())
			{
				return true;
			}
		}

		return false;
	}
}
