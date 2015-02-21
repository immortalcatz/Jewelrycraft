package darkknight.jewelrycraft.container;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.client.GuiGuide;

public class Page
{
    static ResourceLocation pageFlipped = new ResourceLocation("jewelrycraft", "textures/gui/guidePageFlip.png");
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param isSmall
     * @param text
     * @param mouseX
     * @param mouseY
     * @param items
     */
    public static void addCraftingRecipeTextPage(GuiGuide gui, int x, int y, boolean isSmall, String text, int mouseX, int mouseY, ItemStack ... items)
    {
        y += 5;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + items[0].getDisplayName(), x + Math.abs(70 - gui.getFont().getStringWidth(items[0].getDisplayName()) / 2) - 10, y - 2, 0);
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(pageFlipped);
        ArrayList<String> name = new ArrayList<String>();
        if (isSmall){
            gui.drawTexturedModalRect(x, y + 10, 145, 54, 111, 46);
            gui.renderItem(items[0], x + 89, y + 22 + 10, 30f);
            for(int i = 1; i <= 4; i++)
                if (items.length > i && items[i] != null){
                    int posX = x + 8 + (i - 1) % 2 * 22;
                    int posY = y + 26 + (i - 1) / 2 * 22;
                    gui.renderItem(items[i], posX, posY, 30f);
                    name.add(items[i].getDisplayName());
                    if (mouseX >= posX - 8 && mouseX <= posX + 8 && mouseY >= posY - 16 && mouseY <= posY) gui.drawHoverString(name, posX - 20, posY - 14);
                    name.removeAll(name);
                    GL11.glDisable(GL11.GL_LIGHTING);
                }
            drawText(gui, text, x, y + 25);
        }else{
            gui.drawTexturedModalRect(x, y + 12, 145, 0, 111, 54);
            gui.renderItem(items[0], x + 91, y + 28 + 10, 30f);
            for(int i = 1; i <= 9; i++)
                if (items.length > i && items[i] != null){
                    int posX = x + 8 + (i - 1) % 3 * 19;
                    int posY = y + 22 + (i - 1) / 3 * 17;
                    gui.renderItem(items[i], posX, posY, 30f);
                    name.add(items[i].getDisplayName());
                    if (mouseX >= posX - 8 && mouseX <= posX + 8 && mouseY >= posY - 10 && mouseY <= posY) gui.drawHoverString(name, posX - 20, posY - 12);
                    name.removeAll(name);
                    GL11.glDisable(GL11.GL_LIGHTING);
                }
            drawText(gui, text, x, y + 32);
            GL11.glColor4f(1, 1, 1, 1);
        }
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param text
     * @param mouseX
     * @param mouseY
     * @param items
     */
    public static void addSmeltingRecipeTextPage(GuiGuide gui, int x, int y, String text, int mouseX, int mouseY, ItemStack ... items)
    {
        ArrayList<String> name = new ArrayList<String>();
        gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + items[1].getDisplayName(), x + 30 - items[0].getDisplayName().length() / 2, y + 2, 0);
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(pageFlipped);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        gui.drawTexturedModalRect(x, y + 10, 145, 100, 111, 56);
        gui.renderItem(items[0], x + 13, y + 20 + 10, 35f);
        name.add(items[0].getDisplayName());
        if (mouseX >= x && mouseX <= x + 20 && mouseY >= y + 20 && mouseY <= y + 20 + 16) gui.drawHoverString(name, x, y + 20);
        name.removeAll(name);
        GL11.glDisable(GL11.GL_LIGHTING);
        gui.renderItem(items[1], x + 77, y + 28 + 10, 35f);
        drawText(gui, text, x, y + 30);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param item
     * @param text
     * @param size
     */
    public static void addImageTextPage(GuiGuide gui, int x, int y, ItemStack item, String text, float size)
    {
        y += 5;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + item.getDisplayName(), x + Math.abs(70 - gui.getFont().getStringWidth(item.getDisplayName()) / 2) - 10, y + 2, 0);
        GL11.glColor4f(1, 1, 1, 1);
        gui.renderItem(item, x + 13, y + 18, size);
        drawText(gui, text, x, y - (int)(200 / size));
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param item
     * @param text
     * @param size
     * @param txtX
     * @param txtY
     * @param showName
     * @param imgX
     * @param imgY
     */
    public static void addImageTextPage(GuiGuide gui, int x, int y, ItemStack item, String text, float size, int txtX, int txtY, boolean showName, int imgX, int imgY)
    {
        y += 5;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        if (showName) gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + item.getDisplayName(), x + Math.abs(70 - gui.getFont().getStringWidth(item.getDisplayName()) / 2) - 10, y + 2, 0);
        GL11.glColor4f(1, 1, 1, 1);
        gui.renderItem(item, x + 13 + imgX, y + 18 + imgY, size);
        drawText(gui, text, x + txtX, y + txtY);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param text
     */
    public static void addTextPage(GuiGuide gui, int x, int y, String text)
    {
        y -= 25;
        drawText(gui, text, x, y);
        GL11.glColor4f(1, 1, 1, 1);
    }
    
    /**
     * @param gui
     * @param text
     * @param x
     * @param y
     */
    public static void drawText(GuiGuide gui, String text, int x, int y)
    {
        String[] s = text.split(" ");
        String displayText = "";
        ArrayList<String> textLines = new ArrayList<String>();
        for(String element: s)
            if ((displayText + element + " ").length() <= 24) displayText += element + " ";
            else{
                textLines.add(displayText.trim());
                displayText = element + " ";
            }
        textLines.add(displayText.trim());
        for(int i = 0; i < textLines.size(); i++)
            gui.getFont().drawString(textLines.get(i), x, y + 30 + i * 12, 0);
    }
}
