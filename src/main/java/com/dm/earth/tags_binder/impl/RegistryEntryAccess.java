package com.dm.earth.tags_binder.impl;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;

public class RegistryEntryAccess {
	@Nullable
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <O> RegistryEntry<O> getHolder(O object) {
		if (object instanceof Item i)
			return (RegistryEntry<O>) i.getRegistryEntry();
		if (object instanceof Block i)
			return (RegistryEntry<O>) i.getRegistryEntry();
		if (object instanceof Fluid i)
			return (RegistryEntry<O>) i.getRegistryEntry();
		return null;
	}
}
