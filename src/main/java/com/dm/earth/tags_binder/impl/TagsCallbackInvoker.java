package com.dm.earth.tags_binder.impl;

import com.dm.earth.tags_binder.api.LoadTagsCallback;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.TagManagerLoader;
import net.minecraft.util.registry.Registry;

public class TagsCallbackInvoker {
	@SuppressWarnings("unchecked")
	public static <T> TagManagerLoader.RegistryTags<T> call(
			TagManagerLoader.RegistryTags<T> value) {
		TagsHandlerImpl<T> impl = new TagsHandlerImpl<>(value);
		if (value.key().equals(Registry.ITEM))
			LoadTagsCallback.ITEM.invoker().onTagsLoad((LoadTagsCallback.TagHandler<Item>) impl);
		if (value.key().equals(Registry.BLOCK))
			LoadTagsCallback.BLOCK.invoker().onTagsLoad((LoadTagsCallback.TagHandler<Block>) impl);
		if (value.key().equals(Registry.FLUID))
			LoadTagsCallback.FLUID.invoker().onTagsLoad((LoadTagsCallback.TagHandler<Fluid>) impl);
		return new TagManagerLoader.RegistryTags<>(value.key(), impl.get());
	}
}
