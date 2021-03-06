/*
 * <BlockBank Minecraft Forge economy mod>
 *     Copyright (C) <2017>  <Daniel Tucker>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

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
