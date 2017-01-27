package me.denyol.blockbank.blocks.vault;

import me.denyol.blockbank.api.BlockBankApi;
import me.denyol.blockbank.blocks.BlockBase;
import me.denyol.blockbank.blocks.ModBlocks;
import me.denyol.blockbank.tileentity.vault.TileEntityVaultCasing;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Daniel on 27/1/17.
 */
public class BlockVaultPart extends BlockBase implements ITileEntityProvider
{
	public static final PropertyEnum PROPERTY_TYPE = PropertyEnum.create("type", BlockBankApi.EnumVaultPartyType.class);
	public static final PropertyBool PROPERTY_MULTIBLOCK = PropertyBool.create("multiblock");

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
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		switch (meta)
		{
			case 0:
				return new TileEntityVaultCasing();
			case 1:
				return null;
			default:
				return null;
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntity te = worldIn instanceof ChunkCache ? ((ChunkCache)worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);

		if(te instanceof TileEntityVaultCasing)
		{
			TileEntityVaultCasing tileEntity = (TileEntityVaultCasing)te;

			if(tileEntity.hasMaster())
				return state.withProperty(PROPERTY_MULTIBLOCK, true);
		}

		return state.withProperty(PROPERTY_MULTIBLOCK, false);
	}
}