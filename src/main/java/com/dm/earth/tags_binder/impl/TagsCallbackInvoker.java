package com.dm.earth.tags_binder.impl;

import com.dm.earth.tags_binder.api.LoadTagsCallback;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagManagerLoader;

public class TagsCallbackInvoker {
	@SuppressWarnings("unchecked")
	public static <T> TagManagerLoader.RegistryTags<T> call(
			TagManagerLoader.RegistryTags<T> value) {
		TagsHandlerImpl<T> impl = new TagsHandlerImpl<>(value);
		if (value.key().equals(RegistryKeys.ITEM))
			LoadTagsCallback.ITEM.invoker().onTagsLoad((LoadTagsCallback.TagHandler<Item>) impl);
		if (value.key().equals(RegistryKeys.BLOCK))
			LoadTagsCallback.BLOCK.invoker().onTagsLoad((LoadTagsCallback.TagHandler<Block>) impl);
		if (value.key().equals(RegistryKeys.FLUID))
			LoadTagsCallback.FLUID.invoker().onTagsLoad((LoadTagsCallback.TagHandler<Fluid>) impl);
		return new TagManagerLoader.RegistryTags<>(value.key(), impl.get());
	}
}
