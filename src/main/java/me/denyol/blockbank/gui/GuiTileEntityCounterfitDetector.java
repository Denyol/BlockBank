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
	private final InventoryPlayer player;
	
	public GuiTileEntityCounterfitDetector(InventoryPlayer player, TileEntityCounterfitDetector tileEntity)
	{
		super(new ContainerTileEntityCounterfitDetector(player, tileEntity));
		
		te = tileEntity;
		this.player = player;
		
		this.xSize = 176;
		this.ySize = 133;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(this.te.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		this.fontRendererObj.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
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
