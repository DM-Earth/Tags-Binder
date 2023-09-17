package com.dm.earth.tags_binder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dm.earth.tags_binder.api.LoadTagsCallback;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.tag.TagManagerLoader;
import net.minecraft.util.Identifier;

public class TagsHandlerImpl<T> implements LoadTagsCallback.TagHandler<T> {
    protected HashMap<Identifier, Collection<RegistryEntry<T>>> map;
    protected RegistryKey<? extends Registry<T>> key;

    public TagsHandlerImpl(TagManagerLoader.RegistryTags<T> value) {
        this.map = new HashMap<>(value.tags());
        this.key = value.key();
    }

    @Override
    public Map<Identifier, Collection<RegistryEntry<T>>> get() {
        return this.map;
    }

    @SafeVarargs
    @Override
    public final void register(Identifier tag, T... values) {
        if (map.containsKey(tag)) {
            ArrayList<RegistryEntry<T>> tagsList = new ArrayList<>(map.get(tag));
            for (T value : values)
                if (tagsList.stream().noneMatch(h -> h.value() == value))
                    tagsList.add(RegistryEntryAccess.getHolder(value));
            map.replace(tag, new ArrayList<>(tagsList));
        } else {
            ArrayList<RegistryEntry<T>> tagsList = new ArrayList<>();
            for (T value : values)
                tagsList.add(RegistryEntryAccess.getHolder(value));
            map.put(tag, new ArrayList<>(tagsList));
        }
    }

    @SafeVarargs
    @Override
    public final void remove(Identifier tag, T... values) {
        if (map.containsKey(tag)) {
            if (values.length > 0) {
                ArrayList<RegistryEntry<T>> tagsList = new ArrayList<>(map.get(tag));
                for (T value : values)
                    tagsList.removeIf(h -> h.value() == value);
                if (tagsList.size() > 0) map.replace(tag, new ArrayList<>(tagsList));
                else map.remove(tag);
            } else map.remove(tag);
        }
    }

    @Override
    public List<T> get(Identifier tag) {
        return this.map.get(tag).stream().map(RegistryEntry::value).toList();
    }

    @Override
    public List<TagKey<T>> getKeys() {
        return this.map.keySet().stream().map(id -> TagKey.of(key, id)).toList();
    }
}
