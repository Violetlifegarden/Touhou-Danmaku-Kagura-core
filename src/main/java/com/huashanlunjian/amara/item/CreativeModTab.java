package com.huashanlunjian.amara.item;

import com.huashanlunjian.amara.Amara;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Amara.MOD_ID);

    public static final Supplier<CreativeModeTab> AMARA_ITEMS_TAB = CREATIVE_MODE_TAB.register("touhou_danmaku_kagura_core",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PachuliNotebook.get()))
                    .title(Component.literal("东方弹幕神乐核心"))
                    .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModItems.PachuliNotebook.get());
                    }).build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
