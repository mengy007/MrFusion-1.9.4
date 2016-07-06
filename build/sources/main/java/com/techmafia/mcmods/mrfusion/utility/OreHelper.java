package com.techmafia.mcmods.mrfusion.utility;

import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by mengy007 on 1/31/2016.
 */
public class OreHelper {
    public static Object getOreWithVanillaFallback(Object vanillaFallback, String... moddedOre) {
        for (String modOre : moddedOre) {
            if (OreDictionary.getOres(modOre).size() > 0)
                return modOre;
        }

        return vanillaFallback;
    }
}
