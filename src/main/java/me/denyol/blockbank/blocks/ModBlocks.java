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

package me.denyol.blockbank.blocks;

import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.blocks.vault.BlockVaultPart;
import me.denyol.blockbank.items.ItemBlockVaultPart;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
	
	public static Block counterfeitDetector;
	public static Block coinForge;

	public static Block blockVaultPart;
	public static ItemBlockVaultPart itemBlockVaultPart;
	
	public static void loadBlocks()
	{
		counterfeitDetector = new BlockCounterfeitDetector(Blocks.COUNTERFEIT_DETECTOR);
		coinForge = new BlockCoinForge(Blocks.COIN_FORGE);

		blockVaultPart = new BlockVaultPart(Blocks.VAULT_PART);
		itemBlockVaultPart = new ItemBlockVaultPart(blockVaultPart);
	}
	
	public static void registerBlocks()
	{
		registerBlock(counterfeitDetector);
		registerBlock(coinForge);

		registerVariantBlock(blockVaultPart, itemBlockVaultPart);
	}

	private static void registerVariantBlock(Block block, ItemBlock itemBlock)
	{
		GameRegistry.register(block);

		itemBlock.setRegistryName(block.getRegistryName());
		GameRegistry.register(itemBlock);
	}
	
	private static void registerBlock(Block block)
	{
		GameRegistry.register(block); // register the block
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		GameRegistry.register(itemBlock);
	}
	
	public static void registerForRendering()
	{
		registerRender(counterfeitDetector);
		registerRender(coinForge);
		registerRender(blockVaultPart);
	}
	
	private static void registerRender(Block block)
	{
		if(block instanceof BlockBase)
			((BlockBase) block).registerForRendering();
	}
	
	public enum Blocks
	{
		COUNTERFEIT_DETECTOR("counterfeit_detector", "counterfeit_detector", Material.IRON),
		COIN_FORGE("coin_forge", "coin_forge", Material.IRON),
		VAULT_PART("vault_part", "vault_part", Material.IRON);
		
		private String unlocalizedName;
		private String registryName; // should be unique
		private CreativeTabs creativeTabs;
		private Material blockMaterial;
		
		Blocks(String unlocalizedName, String registryName, CreativeTabs creativeTabs, Material material)
		{
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
			this.creativeTabs = creativeTabs;
			this.blockMaterial = material;
		}
		
		Blocks(String unlocalizedName, String registryName, Material material)
		{
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
			this.creativeTabs = BlockBank.CREATIVE_TAB;
			this.blockMaterial = material;
		}
		
		public String getUnlocalizedName()
		{
			return unlocalizedName;
		}
		
		public String getRegistryName()
		{
			return registryName;
		}
		
		public CreativeTabs getCreativeTabs()
		{
			return creativeTabs;
		}
		
		public Material getMaterial()
		{
			return blockMaterial;
		}
		
	}
}