package me.denyol.blockbank.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBase extends Item
{
	/**
	 * Sets the unlocalized name, registry name, creative tab, the max damage to 0 and registers the item
	 */
	public ItemBase(ModItems.Items item)
	{
		setUnlocalizedName(item.getUnlocalizedName());
		setRegistryName(item.getRegistryName());
		setCreativeTab(item.getCreativeTabs());
		this.setMaxDamage(0);
		GameRegistry.register(this); // register the item
	}
}
