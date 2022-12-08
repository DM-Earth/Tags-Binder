package com.dm.earth.tags_binder.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dm.earth.tags_binder.api.LoadTagsCallback;

import net.minecraft.tag.Tag;
import net.minecraft.tag.TagKey;
import net.minecraft.tag.TagManagerLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;

public class TagsHandlerImpl<T> implements LoadTagsCallback.TagHandler<T> {

	protected HashMap<Identifier, Tag<RegistryEntry<T>>> map;
	protected RegistryKey<? extends Registry<T>> key;

	public TagsHandlerImpl(TagManagerLoader.RegistryTags<T> value) {
		this.map = new HashMap<>(value.tags());
		this.key = value.key();
	}

	@Override
	public Map<Identifier, Tag<RegistryEntry<T>>> get() {
		return this.map;
	}

	@SafeVarargs
	@Override
	public final void register(Identifier tag, T... values) {
		if (map.containsKey(tag)) {
			ArrayList<RegistryEntry<T>> tagsList = new ArrayList<>(map.get(tag).values());
			for (T value : values)
				if (tagsList.stream().noneMatch(h -> h.value() == value))
					tagsList.add(RegistryEntryAccess.getHolder(value));
			map.replace(tag, new Tag<>(tagsList));
		} else {
			ArrayList<RegistryEntry<T>> tagsList = new ArrayList<>();
			for (T value : values)
				tagsList.add(RegistryEntryAccess.getHolder(value));
			map.put(tag, new Tag<>(tagsList));
		}
	}

	@SafeVarargs
	@Override
	public final void remove(Identifier tag, T... values) {
		if (map.containsKey(tag)) {
			if (values.length > 0) {
				ArrayList<RegistryEntry<T>> tagsList = new ArrayList<>(map.get(tag).values());
				for (T value : values)
					tagsList.removeIf(h -> h.value() == value);
				if (tagsList.size() > 0)
					map.replace(tag, new Tag<>(tagsList));
				else
					map.remove(tag);
			} else
				map.remove(tag);
		}
	}

	@Override
	public List<T> get(Identifier tag) {
		return this.map.get(tag).values().stream().map(h -> h.value()).toList();
	}

	@Override
	public List<TagKey<T>> getKeys() {
		return this.map.keySet().stream().map(id -> TagKey.of(key, id)).toList();
	}
}
