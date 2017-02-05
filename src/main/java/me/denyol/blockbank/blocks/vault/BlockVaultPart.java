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

import me.denyol.blockbank.api.BlockBankApi;
import me.denyol.blockbank.blocks.BlockBase;
import me.denyol.blockbank.blocks.ModBlocks;
import me.denyol.blockbank.tileentity.vault.*;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Daniel on 27/1/17.
 */
public class BlockVaultPart extends BlockBase implements ITileEntityProvider
{
	public static final PropertyEnum PROPERTY_TYPE = PropertyEnum.create("type", BlockBankApi.EnumVaultPartyType.class);
	public static final PropertyBool PROPERTY_MULTIBLOCK = PropertyBool.create("multiblock");

	public static final EnumFacing[] SIDES = {EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.WEST};

	public BlockVaultPart(ModBlocks.Blocks block)
	{
		super(block);
		this.getDefaultState().withProperty(PROPERTY_TYPE, BlockBankApi.EnumVaultPartyType.CASING).withProperty(PROPERTY_MULTIBLOCK, false);
		this.setHardness(60.0F);
		this.setResistance(2500.0F);
	}

	@Override // stop piston pushing
	public EnumPushReaction getMobilityFlag(IBlockState state)
	{
		return EnumPushReaction.BLOCK;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		BlockBankApi.EnumVaultPartyType type = (BlockBankApi.EnumVaultPartyType) state.getValue(PROPERTY_TYPE);
		return type.getMetadata();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		BlockBankApi.EnumVaultPartyType[] types = BlockBankApi.EnumVaultPartyType.values();
		for (BlockBankApi.EnumVaultPartyType type : types)
		{
			list.add(new ItemStack(itemIn, 1, type.getMetadata()));
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_TYPE, PROPERTY_MULTIBLOCK);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockBankApi.EnumVaultPartyType) state.getValue(PROPERTY_TYPE)).getMetadata();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_TYPE, BlockBankApi.EnumVaultPartyType.byMetadata(meta));
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(PROPERTY_TYPE, BlockBankApi.EnumVaultPartyType.byMetadata(meta)).withProperty(PROPERTY_MULTIBLOCK, false);
	}

	@Override
	public void registerForRendering()
	{
		super.registerForRendering();

		for(BlockBankApi.EnumVaultPartyType type : BlockBankApi.EnumVaultPartyType.values())
		{
			int meta = type.getMetadata();
			String name = type.getName().toLowerCase();
			ModelResourceLocation resourceLocation = new ModelResourceLocation(this.getRegistryName(), "multiblock=false,type=" + name);
			ModelLoader.setCustomModelResourceLocation(ModBlocks.itemBlockVaultPart, meta, resourceLocation);
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileEntityVaultPanel)
		{
			TileEntityVaultPanel tileEntity = (TileEntityVaultPanel) te;

			tileEntity.setOwner(placer.getUniqueID());
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		switch (meta)
		{
			case 0: // casing
				return new TileEntityVaultCasing();
			case 1:
				return new TileEntityVaultWall();
			case 2:
				return new TileEntityVaultDoor();
			case 3: // door
				return new TileEntityVaultPanel();
			default:
				return null;
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity te = worldIn.getTileEntity(pos);

		if(te != null && te instanceof TileEntityVaultPanel)
		{
			TileEntityVaultPanel tileEntity = (TileEntityVaultPanel) te;
		}

		return false;
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state.withProperty(PROPERTY_MULTIBLOCK, false);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
	{
		TileEntity te = worldIn.getTileEntity(pos);

		if(te != null && te instanceof VaultTileEntityBase)
		{
			((VaultTileEntityBase) te).notifyNeighbours();
		}

		super.neighborChanged(state, worldIn, pos, blockIn);
	}

	/*
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		super.onBlockHarvested(worldIn, pos, state, player);

		TileEntity te = worldIn.getTileEntity(pos);

		if(te != null && te instanceof VaultTileEntityBase)
		{
			((VaultTileEntityBase) te).blockRemoved();
		}
	}*/

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);

		TileEntity te = worldIn.getTileEntity(pos);

		if(te != null && te instanceof VaultTileEntityBase)
		{
			((VaultTileEntityBase) te).blockRemoved();
		}
	}
}
