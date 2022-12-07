package com.dm.earth.tags_binder.mixin;

import com.dm.earth.tags_binder.impl.TagsCallbackInvoker;

import net.minecraft.server.DataPackContents;
import net.minecraft.tag.TagManagerLoader;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DataPackContents.class)
public class DataPackContentsMixin {
	@ModifyVariable(method = "repopulateTags", at = @At(value = "HEAD"), argsOnly = true)
	private static <T> TagManagerLoader.RegistryTags<T> populate(TagManagerLoader.RegistryTags<T> value) {
		return TagsCallbackInvoker.call(value);
	}
}
