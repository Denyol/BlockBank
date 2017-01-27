package me.denyol.blockbank.tileentity;

import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.tileentity.vault.TileEntityVaultCasing;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Daniel on 27/1/17.
 */
public class ModTileEntitys
{
	public static void registerTileEntitys()
	{
		GameRegistry.registerTileEntity(TileEntityCounterfeitDetector.class, BlockBank.MOD_ID + "TileEntityCounterfeitDetector");
		GameRegistry.registerTileEntity(TileEntityCoinForge.class, BlockBank.MOD_ID + "TileEntityCoinForge");
		GameRegistry.registerTileEntity(TileEntityVaultCasing.class, BlockBank.MOD_ID + "TileEntityVaultCasing");
	}
}
