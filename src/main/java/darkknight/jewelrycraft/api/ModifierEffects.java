package darkknight.jewelrycraft.api;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ModifierEffects
{
    protected ItemStack modifier;
    protected Random rand = new Random();
    protected static ArrayList<ModifierEffects> effects = new ArrayList<ModifierEffects>();
    
    /**
     * @param modifier
     */
    public ModifierEffects(ItemStack modifier)
    {
        this.modifier = modifier;
        effects.add(this);
    }
    
    /**
     * @return
     */
    public static ArrayList<ModifierEffects> getEffects()
    {
        return effects;
    }
    
    /**
     * @param item
     * @param player
     * @param jewelry
     */
    public void action(ItemStack item, EntityPlayer player, Item jewelry)
    {};
    
    /**
     * @param item
     * @param player
     * @param target
     * @param jewelry
     * @return
     */
    public boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        return false;
    }
    
    /**
     * @param item
     * @param player
     * @param source
     * @param jewelry
     * @return
     */
    public boolean onPlayerAttackedCacellable(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        return false;
    }
    
    /**
     * @param item
     * @param player
     * @param target
     * @param jewelry
     * @return
     */
    public void onEntityAttacked(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {}
    
    /**
     * @param item
     * @param player
     * @param source
     * @param jewelry
     * @return
     */
    public void onPlayerAttacked(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {}
}