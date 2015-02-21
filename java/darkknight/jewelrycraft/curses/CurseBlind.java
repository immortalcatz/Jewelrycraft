package darkknight.jewelrycraft.curses;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class CurseBlind extends Curse
{
    public CurseBlind(int id, String name, int text)
    {
        super(id, name, text);
    }
    
    @Override
    public void action(World world, EntityPlayer player)
    {
        if (!player.isPotionActive(Potion.blindness) || player.getActivePotionEffect(Potion.blindness).getDuration() < 30) player.addPotionEffect(new PotionEffect(Potion.blindness.id, 60));
    }
    
    public String getDescription()
    {
        return "You see the light slowly fading in front of you";
    }
}
