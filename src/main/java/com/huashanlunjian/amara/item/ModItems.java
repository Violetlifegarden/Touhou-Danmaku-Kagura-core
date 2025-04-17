package com.huashanlunjian.amara.item;

import com.huashanlunjian.amara.Amara;
import com.huashanlunjian.amara.item.custom.PachuliNotebook;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Amara.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
    public static final DeferredItem<Item> PachuliNotebook = ITEMS.register("pachulinotebook",
            ()->new PachuliNotebook(new Item.Properties()));

}
