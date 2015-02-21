package darkknight.jewelrycraft.curses;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CurseFlamingSoul extends Curse
{
    public CurseFlamingSoul(int id, String name, int text)
    {
        super(id, name, text);
    }
    
    @Override
    public void action(World world, EntityPlayer player)
    {
        if (!player.isBurning()) player.setFire(60);
    }
    
    public String getDescription()
    {
        return "Is it me or is it getting hot in here?";
    }
}
