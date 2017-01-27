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

package me.denyol.blockbank.blocks.vault;

import me.denyol.blockbank.blocks.BlockBase;
import me.denyol.blockbank.blocks.ModBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;

/**
 * Created by Daniel on 27/1/17.
 */
public class BlockSecureCasing extends BlockBase
{
	public BlockSecureCasing(ModBlocks.Blocks block)
	{
		super(block);
		setHarvestLevel("pickaxe", 3);
		setSoundType(SoundType.STONE);
		setHardness(45.0F);
		setResistance(2000.0F);
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state)
	{
		return EnumPushReaction.BLOCK;
	}

	@Override
	public MapColor getMapColor(IBlockState state)
	{
		return MapColor.OBSIDIAN;
	}
}
