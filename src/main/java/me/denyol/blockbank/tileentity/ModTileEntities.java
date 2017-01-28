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

package me.denyol.blockbank.tileentity;

import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.tileentity.vault.TileEntityVaultCasing;
import me.denyol.blockbank.tileentity.vault.TileEntityVaultDoor;
import me.denyol.blockbank.tileentity.vault.TileEntityVaultPanel;
import me.denyol.blockbank.tileentity.vault.TileEntityVaultWall;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Daniel on 27/1/17.
 */
public class ModTileEntities
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityCounterfeitDetector.class, BlockBank.MOD_ID + "TileEntityCounterfeitDetector");
		GameRegistry.registerTileEntity(TileEntityCoinForge.class, BlockBank.MOD_ID + "TileEntityCoinForge");
		GameRegistry.registerTileEntity(TileEntityVaultCasing.class, BlockBank.MOD_ID + "TileEntityVaultCasing");
		GameRegistry.registerTileEntity(TileEntityVaultPanel.class, BlockBank.MOD_ID + "TileEntityVaultPanel");
		GameRegistry.registerTileEntity(TileEntityVaultDoor.class, BlockBank.MOD_ID + "TileEntityVaultDoor");
		GameRegistry.registerTileEntity(TileEntityVaultWall.class, BlockBank.MOD_ID + "TileEntityVaultWall");
	}
}
