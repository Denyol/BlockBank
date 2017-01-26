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
		registry.addRecipeCategories(new ForgeRecipeCategory(registry.getJeiHelpers().getGuiHelper()));

		registry.addRecipeHandlers(new ForgeRecipeHandler());

		registry.addRecipes(BlockBankApi.coinForgeRecipes);
	}

}
