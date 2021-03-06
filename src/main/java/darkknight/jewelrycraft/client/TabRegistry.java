package darkknight.jewelrycraft.client;

import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraftforge.client.event.GuiScreenEvent;

/**
 * @author TinkersConstruct
 */
public class TabRegistry {
	private static ArrayList<AbstractTab>	tabList	= new ArrayList<AbstractTab>();

	public static void registerTab(AbstractTab tab) {
		tabList.add(tab);
	}

	public static ArrayList<AbstractTab> getTabList() {
		return tabList;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void guiPostInit(GuiScreenEvent.InitGuiEvent.Post event) {
		if ((event.gui instanceof GuiInventory)) {
			int xSize = 176;
			int ySize = 166;
			int guiLeft = (event.gui.width - xSize) / 2;
			int guiTop = (event.gui.height - ySize) / 2;
			if (!mc.thePlayer.getActivePotionEffects().isEmpty()) if (Loader.isModLoaded("NotEnoughItems")) {
				try {
					// Check whether NEI is hidden and enabled
					Class<?> c = Class.forName("codechicken.nei.NEIClientConfig");
					Object hidden = c.getMethod("isHidden").invoke(null);
					Object enabled = c.getMethod("isEnabled").invoke(null);
					if (hidden != null && hidden instanceof Boolean && enabled != null && enabled instanceof Boolean) if ((Boolean) hidden || !((Boolean) enabled)) guiLeft += 60;
				} catch (Exception e) {
				}
			} else guiLeft += 60;
			updateTabValues(guiLeft, guiTop, InventoryTabVanilla.class);
			addTabsToList(event.buttonList);
		}
	}

	private static Minecraft	mc	= FMLClientHandler.instance().getClient();

	public static void openInventoryGui() {
		mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.openContainer.windowId));
		GuiInventory inventory = new GuiInventory(mc.thePlayer);
		mc.displayGuiScreen(inventory);
	}

	public static void updateTabValues(int cornerX, int cornerY, Class<?> selectedButton) {
		int count = 0;
		for (int i = 0; i < tabList.size(); i++) {
			AbstractTab t = tabList.get(i);
			if (t.shouldAddToList()) {
				if (selectedButton.equals(TabCurses.class)) {
					t.xPosition = cornerX + 195;
					t.yPosition = cornerY + count*20;
				}else if (selectedButton.equals(TabJewelry.class)) {
					t.xPosition = cornerX + 140 + count * 20;
					t.yPosition = cornerY + 64;
				} else {
					t.xPosition = cornerX + 131 + count * 20;
					t.yPosition = cornerY + 64;
				}
				t.enabled = !t.getClass().equals(selectedButton);
				if (t.enabled) count++;
			}
		}
	}

	public static void addTabsToList(List buttonList) {
		for (AbstractTab tab : tabList)
			if (tab.shouldAddToList() && tab.enabled) buttonList.add(tab);
	}
}