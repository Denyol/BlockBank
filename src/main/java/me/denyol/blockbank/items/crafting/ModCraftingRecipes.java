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
