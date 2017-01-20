package me.denyol.blockbank.crafting;

import me.denyol.blockbank.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting
{
	public static void initRegister()
	{
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.unfiredCoinMold), "CCC", "C C", "CCC", 'C', new ItemStack(Items.CLAY_BALL));
	}
}
