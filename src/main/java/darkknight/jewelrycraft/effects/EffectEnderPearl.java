package darkknight.jewelrycraft.effects;

import java.util.Iterator;
import java.util.List;
import darkknight.jewelrycraft.api.ModifierEffects;
import darkknight.jewelrycraft.item.ItemBracelet;
import darkknight.jewelrycraft.item.ItemEarrings;
import darkknight.jewelrycraft.item.ItemNecklace;
import darkknight.jewelrycraft.item.ItemRing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class EffectEnderPearl extends ModifierEffects
{
    public EffectEnderPearl()
    {
        super(new ItemStack(Items.ender_pearl));
    }
    
    @Override
    public void action(ItemStack item, EntityPlayer player, Item jewelry)
    {
        if (jewelry instanceof ItemEarrings){
            AxisAlignedBB axisalignedbb = player.boundingBox.expand(2.0D, 2.0D, 2.0D);
            List list = player.worldObj.getEntitiesWithinAABB(EntityArrow.class, axisalignedbb);
            if (!player.worldObj.isRemote && list != null && !list.isEmpty()){
                Iterator iterator = list.iterator();
                while (iterator.hasNext()){
                    EntityArrow arrow = (EntityArrow)iterator.next();
                    if (arrow.shootingEntity == null || !(arrow.shootingEntity.equals(player)))
                    // Negative earrings
                    if (rand.nextInt(30) == 0){
                        arrow.worldObj.createExplosion(new EntityTNTPrimed(arrow.worldObj), arrow.posX, arrow.posY, arrow.posZ, 2F, true);
                        arrow.setDead();
                    }
                    // Positive earrings
                    else arrow.setPosition(arrow.posX + rand.nextInt(16) - rand.nextInt(16), arrow.posY + rand.nextInt(16), arrow.posZ + rand.nextInt(16) - rand.nextInt(16));
                }
            }
        }
        // Negative Necklace
        if (jewelry instanceof ItemNecklace) player.addPotionEffect(new PotionEffect(Potion.resistance.id, 60, -10, true));
        // Negative bracelet
        if (jewelry instanceof ItemBracelet && player.isInWater()) player.setPositionAndUpdate(player.posX + rand.nextInt(16) - rand.nextInt(16), player.posY + rand.nextInt(4), player.posZ + rand.nextInt(16) - rand.nextInt(16));
    }
    
    @Override
    public void onPlayerAttacked(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        // Positive Necklace
        if (jewelry instanceof ItemNecklace && source.getEntity() != null) source.getEntity().attackEntityFrom(source, amount);
        // Positive bracelet
        if (jewelry instanceof ItemBracelet && !player.worldObj.isRemote){
            int id = player.worldObj.provider.dimensionId;
            if (player.getHealth() <= 6F) if (player.getBedLocation(id) != null) player.setPositionAndUpdate(player.getBedLocation(id).posX, player.getBedLocation(id).posY, player.getBedLocation(id).posZ);
            else player.setPositionAndUpdate(player.worldObj.getSpawnPoint().posX, player.worldObj.getSpawnPoint().posY, player.worldObj.getSpawnPoint().posZ);
        }
    }
    
    public void onEntityAttacked(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        if (jewelry instanceof ItemRing){
            // Negative ring
            if (target instanceof EntityEnderman) player.addPotionEffect(new PotionEffect(Potion.weakness.id, 400, 2, true));
            // Positive ring
            else target.setPosition(target.posX + rand.nextInt(16) - rand.nextInt(16), target.posY + rand.nextInt(4), target.posZ + rand.nextInt(16) - rand.nextInt(16));
        }
    }
    
    public boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        return false;
    }
}
