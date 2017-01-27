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
