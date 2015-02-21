package darkknight.jewelrycraft.curses;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class CurseRottenHeart extends Curse
{
    public CurseRottenHeart(int id, String name, int text)
    {
        super(id, name, text);
    }
    
    @Override
    public void action(World world, EntityPlayer player)
    {
        if (!player.isPotionActive(Potion.poison) || player.getActivePotionEffect(Potion.poison).getDuration() < 30) player.addPotionEffect(new PotionEffect(Potion.poison.id, 80));
    }
    
    public String getDescription()
    {
        return "Your heart slowly rots inside";
    }
}
