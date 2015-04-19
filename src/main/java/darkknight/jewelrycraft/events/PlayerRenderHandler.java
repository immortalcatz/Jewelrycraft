package darkknight.jewelrycraft.events;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.entities.renders.RenderHelper;
import darkknight.jewelrycraft.item.render.BraceletRender;
import darkknight.jewelrycraft.item.render.EarringsRender;
import darkknight.jewelrycraft.item.render.MaskRender;
import darkknight.jewelrycraft.model.ModelBracelet;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

public class PlayerRenderHandler
{
    MaskRender mask = new MaskRender();
    EarringsRender earrings = new EarringsRender();
    BraceletRender bracelet = new BraceletRender();
    public static String[] infamyCache = new String[]{};
    
    @SubscribeEvent
    public void renderScreen(RenderPlayerEvent.Specials.Post event)
    {
        ModelBiped main = event.renderer.modelBipedMain;
        Iterator<EntityPlayer> players = event.entityPlayer.worldObj.playerEntities.iterator();
        if (infamyCache != null){
            while (players.hasNext()){
                EntityPlayer player = players.next();
                NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
                if (checkPlayerInfamy(player.getDisplayName()) && event.entityPlayer.getDisplayName().equals(player.getDisplayName()) && playerInfo.getInteger("cursePoints") > 0){
                    float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * event.partialRenderTick;
                    float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * event.partialRenderTick;
                    float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * event.partialRenderTick;
                    GL11.glPushMatrix();
                    GL11.glColor4f(1, 1, 1, 1);
                    GL11.glRotatef(yawOffset, 0, -1, 0);
                    GL11.glRotatef(yaw - 90, 0, 1, 0);
                    GL11.glRotatef(pitch, 0, 0, -1);
                    GL11.glRotatef(90F, 0, 1F, 0F);
                    RenderHelper.translateToHeadLevel(player);
                    GL11.glScalef(1.6f, 1.6f, 1.6f);
                    GL11.glTranslatef(-0.25F, -0.25F, -0.25F);
                    mask.doRender(event.entityPlayer, 0F, 0F, 0F, 0F, 0F);
                    GL11.glPopMatrix();
                }
                if (playerInfo.hasKey("ext17") && event.entityPlayer.getDisplayName().equals(player.getDisplayName())){
                    NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext17");
                    ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                    float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * event.partialRenderTick;
                    float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * event.partialRenderTick;
                    float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * event.partialRenderTick;
                    GL11.glPushMatrix();
                    GL11.glColor4f(1, 1, 1, 1);
                    GL11.glRotatef(yawOffset, 0, -1, 0);
                    GL11.glRotatef(yaw - 90, 0, 1, 0);
                    GL11.glRotatef(pitch, 0, 0, -1);
                    GL11.glRotatef(90F, 0, 1F, 0F);
                    RenderHelper.translateToHeadLevel(player);
                    GL11.glScalef(0.07f, 0.07f, 0.07f);
                    GL11.glTranslatef(0.0F, 3.0F, -2.0F);
                    int gemColor = -1;
                    int ingotColor = -1;
                    if (JewelryNBT.gem(item) != null) gemColor = JewelryNBT.gemColor(item);
                    if (JewelryNBT.ingot(item) != null) ingotColor = JewelryNBT.ingotColor(item);
                    earrings.doRender(event.entityPlayer, 0F, 0F, (float)ingotColor, (float)gemColor, 0F);
                    GL11.glPopMatrix();
                }
                for(int i = 10; i <= 13; i++)
                    if (playerInfo.hasKey("ext" + i) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())){
                        NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext" + i);
                        ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                        GL11.glPushMatrix();
                        GL11.glColor4f(1, 1, 1, 1);
                        GL11.glTranslatef((i / 12 == 1) ? -0.275F : 0.475F, 0.2F + ((i / 12 == 1) ? (i - 12) * 0.08F : (i - 10) * 0.08F), 0F);
                        if (player.isSneaking()) GL11.glTranslatef(0F, 0F, -((i == 10 || i == 12) ? (0.1F) : 0.05F));
                        if (i / 12 == 1){
                            GL11.glScalef(0.05f, 0.03f, 0.05f);
                            GL11.glRotatef(main.bipedRightArm.rotateAngleX * (180F / (float)Math.PI), 1F, 0F, 0F);
                            GL11.glRotatef(main.bipedRightArm.rotateAngleY * (180F / (float)Math.PI), 0F, 1F, 0F);
                            GL11.glRotatef(main.bipedRightArm.rotateAngleZ * (180F / (float)Math.PI), 0F, 0F, 1F);
                            int gemColor = -1;
                            int ingotColor = -1;
                            if (JewelryNBT.gem(item) != null) gemColor = JewelryNBT.gemColor(item);
                            if (JewelryNBT.ingot(item) != null) ingotColor = JewelryNBT.ingotColor(item);
                            bracelet.doRender(event.entityPlayer, 0F, 0F, (float)ingotColor, (float)gemColor, 0.0F);
                        }else{
                            GL11.glScalef(0.05f, 0.03f, 0.05f);
                            GL11.glRotatef(main.bipedLeftArm.rotateAngleX * (180F / (float)Math.PI), 1F, 0F, 0F);
                            GL11.glRotatef(main.bipedLeftArm.rotateAngleY * (180F / (float)Math.PI), 0F, 1F, 0F);
                            GL11.glRotatef(main.bipedLeftArm.rotateAngleZ * (180F / (float)Math.PI), 0F, 0F, 1F);
                            int gemColor = -1;
                            int ingotColor = -1;
                            if (JewelryNBT.gem(item) != null) gemColor = JewelryNBT.gemColor(item);
                            if (JewelryNBT.ingot(item) != null) ingotColor = JewelryNBT.ingotColor(item);
                            bracelet.doRender(event.entityPlayer, 0F, 0F, (float)ingotColor, (float)gemColor, 0F);
                        }
                        GL11.glPopMatrix();
                    }
            }
        }
    }
    
    /**
     * @param string
     * @return
     */
    private boolean checkPlayerInfamy(String string)
    {
        for(int i = 0; i < infamyCache.length; i++)
            if (infamyCache[i].equals(string)) return true;
        return false;
    }
}
