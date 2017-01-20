package me.denyol.blockbank.crafting;

import me.denyol.blockbank.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModFurnaceCrafting
{
	public static void initRegister()
	{
		GameRegistry.addSmelting(ModItems.unfiredCoinMold, new ItemStack(ModItems.coinMold), 0.1f);
	}
}
