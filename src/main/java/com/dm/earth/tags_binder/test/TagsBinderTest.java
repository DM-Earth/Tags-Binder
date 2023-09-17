package com.dm.earth.tags_binder.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dm.earth.tags_binder.api.LoadTagsCallback;
import com.dm.earth.tags_binder.api.ResourceConditionCheckTagCallback;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class TagsBinderTest implements ModInitializer {
    public static final boolean DEBUG = false;
    public static final Logger LOGGER = LoggerFactory.getLogger("tags_binder");

    @Override
    public void onInitialize() {
        if (!DEBUG) return;

        LOGGER.info("tags binder init");

        LoadTagsCallback.ITEM.register(handler -> {
            handler.register(ItemTags.PIGLIN_LOVED, Items.NETHERITE_INGOT);
            handler.register(new Identifier("test", "test"), Items.NETHER_BRICK);
            handler.remove(ItemTags.DIRT, Items.DIRT);
        });

        ResourceConditionCheckTagCallback.ITEM.register(key -> {
            if (key.id().getPath().contains("zinc")) {
                return ActionResult.FAIL;
            }

            return ActionResult.PASS;
        });
    }
}
