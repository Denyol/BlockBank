package me.denyol.blockbank.items;

import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.api.BlockBankApi;
import me.denyol.blockbank.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

/**
 * Created by Daniel on 27/1/17.
 */
public class ItemBlockVaultPart extends ItemBlock
{
	public ItemBlockVaultPart(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		BlockBankApi.EnumVaultPartyType type = BlockBankApi.EnumVaultPartyType.byMetadata(stack.getMetadata());
		return super.getUnlocalizedName() + "." + type.toString().toLowerCase();
	}
}
