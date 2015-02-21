package darkknight.jewelrycraft.curses;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Curse
{
    protected int id, texturepack;
    protected String name, description;
    private static ArrayList<Curse> curses = new ArrayList<Curse>();
    public static ArrayList<Curse> availableCurses = new ArrayList<Curse>();
    
    /**
     * @param id the ID of the curse
     * @param name the name of the curse
     * @param texturepack the ID of the pack the texture is located in
     */
    protected Curse(int id, String name, int texturepack)
    {
        this.id = id;
        this.name = name;
        this.texturepack = texturepack;
        curses.add(this);
        availableCurses.add(this);
    }
    
    /**
     * @return the name of the curse
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the description of the curse
     */
    public String getDescription()
    {
        return description;
    }
    
    public Curse setDescription(String desc)
    {
        description = desc;
        return this;
    }
    
    /**
     * @return the curse ID
     */
    public int getID()
    {
        return id;
    }
    
    /**
     * @return the texture pack ID
     */
    public int getTexturePack()
    {
        return texturepack;
    }
    
    /**
     * @param world
     * @param player
     */
    public void action(World world, EntityPlayer player)
    {}
    
    public boolean itemToss()
    {
        return false;
    }
    
    /**
     * @return
     */
    public static ArrayList<Curse> getCurseList()
    {
        return curses;
    }
}
