package me.denyol.blockbank.blocks;

import me.denyol.blockbank.BlockBank;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
	
	public static Block counterfitDetector;
	
	public static void loadBlocks()
	{
		counterfitDetector = new BlockCounterfitDetector(Blocks.COUNTERFIT_DETECTOR);
	}
	
	public static void registerBlocks()
	{
		registerBlock(counterfitDetector);
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
		registerRender(counterfitDetector);
	}
	
	private static void registerRender(Block block)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	public static enum Blocks
	{
		COUNTERFIT_DETECTOR("counterfit_detector", "counterfit_detector", Material.IRON);
		
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