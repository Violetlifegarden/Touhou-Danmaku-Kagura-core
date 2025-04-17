package com.huashanlunjian.amara.screen;

import com.huashanlunjian.amara.Amara;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AmaraScreens {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Amara.MOD_ID);

}
