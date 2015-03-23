package darkknight.jewelrycraft.effects;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class EffectsList
{
    private static ModifierEffects blazePowder, enderEye, feather;
    private static boolean isInitialized = false;
    
    /**
     * @param e
     */
    public static void postInit(FMLPostInitializationEvent e)
    {
        if (!isInitialized){
            blazePowder = new EffectBlazePowder();
            enderEye = new EffectEnderEye();
            feather = new EffectFeather();
            isInitialized = true;
        }
    }
}