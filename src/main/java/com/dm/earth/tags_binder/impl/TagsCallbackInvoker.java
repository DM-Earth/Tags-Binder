package com.dm.earth.tags_binder.impl;

import com.dm.earth.tags_binder.api.LoadTagsCallback;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.TagManagerLoader;
import net.minecraft.util.registry.Registry;

public class TagsCallbackInvoker {
	@SuppressWarnings("unchecked")
	public static <T> TagManagerLoader.RegistryTags<T> call(TagManagerLoader.RegistryTags<T> value) {
		TagsHandlerImpl<T> impl = new TagsHandlerImpl<>(value);
		if (value.key().equals(Registry.ITEM_KEY))
			LoadTagsCallback.ITEM.invoker().load((LoadTagsCallback.TagHandler<Item>) impl);
		if (value.key().equals(Registry.BLOCK_KEY))
			LoadTagsCallback.BLOCK.invoker().load((LoadTagsCallback.TagHandler<Block>) impl);
		if (value.key().equals(Registry.FLUID_KEY))
			LoadTagsCallback.FLUID.invoker().load((LoadTagsCallback.TagHandler<Fluid>) impl);
		return new TagManagerLoader.RegistryTags<>(value.key(), impl.get());
	}
}
