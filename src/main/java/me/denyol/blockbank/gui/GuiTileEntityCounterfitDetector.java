package me.denyol.blockbank.gui;

import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.tileentity.TileEntityCounterfitDetector;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTileEntityCounterfitDetector extends GuiContainer
{

	private TileEntityCounterfitDetector te;
	
	public GuiTileEntityCounterfitDetector(InventoryPlayer player, TileEntityCounterfitDetector tileEntity)
	{
		super(new ContainerTileEntityCounterfitDetector(player, tileEntity));
		
		te = tileEntity;
		
		this.xSize = 184;
		this.ySize = 184;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(new ResourceLocation(BlockBank.MOD_ID, "textures/gui/ItemDetectorContainer.png"));
		int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
