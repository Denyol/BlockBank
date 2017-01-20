package me.denyol.blockbank.core;

import me.denyol.blockbank.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockBankCreativeTab extends CreativeTabs
{

	public BlockBankCreativeTab()
	{
		super("tabBlockBank");
	}

	@Override
	public Item getTabIconItem()
	{
		return ModItems.goldCoin;
	}

}
