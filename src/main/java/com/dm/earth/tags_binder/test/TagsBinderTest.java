package com.dm.earth.tags_binder.test;

import com.dm.earth.tags_binder.api.LoadTagsCallback;

import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;

public class TagsBinderTest implements ModInitializer {
	private static final boolean DEBUG = false;

	@Override
	public void onInitialize() {
		if (!DEBUG) return;
		LoadTagsCallback.ITEM.register(handler -> {
			handler.register(ItemTags.PIGLIN_LOVED, Items.NETHERITE_INGOT);
			handler.register(new Identifier("test", "test"), Items.NETHER_BRICK);
			handler.remove(ItemTags.DIRT, Items.DIRT);
		});
	}
}
