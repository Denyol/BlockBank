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

package me.denyol.blockbank;

import me.denyol.blockbank.blocks.ModBlocks;
import me.denyol.blockbank.core.BlockBankCreativeTab;
import me.denyol.blockbank.items.ModItems;
import me.denyol.blockbank.items.crafting.ModCraftingRecipes;
import me.denyol.blockbank.network.ModGuiHandler;
import me.denyol.blockbank.proxy.IBlockBankProxy;
import me.denyol.blockbank.tileentity.ModTileEntities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = BlockBank.MOD_ID, name = BlockBank.NAME, version = BlockBank.VERSION, acceptedMinecraftVersions = "[1.10.2]", dependencies = "after:JEI@[3.14.2.401,)")
public class BlockBank
{
	
	public static final String MOD_ID = "blockbank";
	public static final String NAME = "Block Bank";
	public static final String VERSION = "@VERSION@";
	
	@Instance(BlockBank.MOD_ID)
	public static BlockBank instance;
	
	// Make sure some methods for only client use don't get used on a server
	@SidedProxy(clientSide = "me.denyol.blockbank.proxy.ClientProxy", serverSide = "me.denyol.blockbank.proxy.ServerProxy")
	public static IBlockBankProxy proxy;
	
	public static Logger logger;
	
	public static CreativeTabs CREATIVE_TAB = new BlockBankCreativeTab();
	
	@EventHandler
	public void preInt(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		
		ModItems.loadItems(); // load and register items
		ModBlocks.loadBlocks();
		ModBlocks.registerBlocks();
		
		proxy.registerRenders();
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		ModCraftingRecipes.init();

		ModTileEntities.registerTileEntities();
		proxy.registerTESRs();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(BlockBank.instance, new ModGuiHandler());
	}
}
