package darkknight.jewelrycraft.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

/**
 * @author Sorin
 *
 */
public class EventCommonHandler
{
    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event)
    {
//        System.out.println(event.crafting + " " + event.craftMatrix.getInventoryName());
    }
}
