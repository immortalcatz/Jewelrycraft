package darkknight.jewelrycraft.effects;

import cpw.mods.fml.relauncher.ReflectionHelper;
import darkknight.jewelrycraft.api.ModifierEffects;
import darkknight.jewelrycraft.item.ItemBracelet;
import darkknight.jewelrycraft.item.ItemEarrings;
import darkknight.jewelrycraft.item.ItemNecklace;
import darkknight.jewelrycraft.item.ItemRing;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.block.BlockOre;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;

public class EffectEnderEye extends ModifierEffects
{
    public EffectEnderEye()
    {
        super(new ItemStack(Items.ender_eye));
    }
    
    public void onJewelryEquipped(ItemStack item, Item jewelry)
    {
        if (jewelry instanceof ItemRing)
            setViewDistance(item, Minecraft.getMinecraft().gameSettings.renderDistanceChunks <= 2 ? 10 : Minecraft.getMinecraft().gameSettings.renderDistanceChunks);
    }
    
    public void onJewelryUnequipped(ItemStack item, Item jewelry)
    {
        if (jewelry instanceof ItemRing)
            Minecraft.getMinecraft().gameSettings.setOptionFloatValue(Options.RENDER_DISTANCE, getViewDistance(item));
    }
    
    @Override
    public void action(ItemStack item, EntityPlayer player, Item jewelry)
    {
        if (jewelry instanceof ItemEarrings && !player.worldObj.isRemote) {
            for(Object e: player.worldObj.getEntitiesWithinAABB(EntityEnderman.class, player.boundingBox.expand(100D, 0D, 100D))){
                EntityEnderman enderman = (EntityEnderman)e;
                ReflectionHelper.setPrivateValue(EntityEnderman.class, enderman, -1, "stareTimer", "field_70826_g");
                Vec3 vec3 = player.getLook(1.0F).normalize();
                Vec3 vec31 = Vec3.createVectorHelper(enderman.posX - player.posX, enderman.boundingBox.minY + (double)(enderman.height / 2.0F) - (player.posY + (double)player.getEyeHeight()), enderman.posZ - player.posZ);
                double d0 = vec31.lengthVector();
                vec31 = vec31.normalize();
                double d1 = vec3.dotProduct(vec31);
                if (d1 > 1.0D - 0.025D / d0 && player.canEntityBeSeen(enderman)) {
                    // Positive earrings
                    if (rand.nextInt(JewelryNBT.numberOfModifiers(item)) == 0)
                        enderman.setTarget(null);
                    // Negative earrings
                    if (!player.isPotionActive(Potion.confusion) || player.getActivePotionEffect(Potion.confusion).getDuration() <= 80)
                        player.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 2 + JewelryNBT.numberOfModifiers(item) / 4));
                }
            }
        }
        // Positive necklace
        if (jewelry instanceof ItemNecklace && !player.worldObj.isRemote) {
            ChunkPosition chunkposition = player.worldObj.findClosestStructure("Stronghold", (int)player.posX, (int)player.posY, (int)player.posZ);
            if (chunkposition != null) {
                Minecraft.getMinecraft().thePlayer.motionX += 0.01D * Math.signum((double)chunkposition.chunkPosX - player.posX) * (rand.nextInt(JewelryNBT.numberOfModifiers(item)) == 0 ? 1 : -1);
                Minecraft.getMinecraft().thePlayer.motionZ += 0.01D * Math.signum((double)chunkposition.chunkPosZ - player.posZ) * (rand.nextInt(JewelryNBT.numberOfModifiers(item)) == 0 ? 1 : -1);
            }
        }
        // Positive bracelet
        if (jewelry instanceof ItemBracelet && !player.worldObj.isRemote && player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ) == BiomeGenBase.sky && (!player.isPotionActive(Potion.moveSpeed) || player.getActivePotionEffect(Potion.moveSpeed).getDuration() < 30))
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 150 - JewelryNBT.numberOfModifiers(item) * 10, 2 - JewelryNBT.numberOfModifiers(item) / 5));
        // Negative bracelet
        if (jewelry instanceof ItemBracelet && !player.worldObj.isRemote && rand.nextInt(520 - JewelryNBT.numberOfModifiers(item) * 20) == 15)
            player.setPositionAndUpdate(player.posX + rand.nextInt(30) * (rand.nextBoolean() ? -1 : 1), player.posY, player.posZ + rand.nextInt(30) * (rand.nextBoolean() ? -1 : 1));
        // Negative ring
        if (jewelry instanceof ItemRing && Minecraft.getMinecraft().gameSettings.renderDistanceChunks != 2.2F - JewelryNBT.numberOfModifiers(item) * 0.1F)
            Minecraft.getMinecraft().gameSettings.setOptionFloatValue(Options.RENDER_DISTANCE, 2.2F - JewelryNBT.numberOfModifiers(item) * 0.1F);
        // Positive ring
        if (jewelry instanceof ItemRing && rand.nextInt(180 + JewelryNBT.numberOfModifiers(item) * 20) == 12) {
            for(int i = (int)player.posX - 2; i <= (int)player.posX + 2; i++)
                for(int j = (int)player.posY - 2; j <= (int)player.posY + 2; j++)
                    for(int k = (int)player.posZ - 2; k <= (int)player.posZ + 2; k++)
                        if (player.worldObj.getBlock(i, j, k) instanceof BlockOre)
                            player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GRAY + StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".effect.endereye.1") + " " + player.worldObj.getBlock(i, j, k).getLocalizedName() + " " + StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".effect.endereye.2")));
        }
    }
    
    @Override
    public void onPlayerAttacked(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        // Negative necklace
        if (jewelry instanceof ItemNecklace && !player.worldObj.isRemote)
            player.addPotionEffect(new PotionEffect(Potion.blindness.id, 100 + JewelryNBT.numberOfModifiers(item) * 30, 1));
    }
    
    public static void setViewDistance(ItemStack item, int viewDistance)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else{
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        itemStackData.setInteger("viewDistance", viewDistance);
    }
    
    public static int getViewDistance(ItemStack item)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else{
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        int i = itemStackData.getInteger("viewDistance");
        return i;
    }
}
