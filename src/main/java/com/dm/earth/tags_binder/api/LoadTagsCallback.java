package com.dm.earth.tags_binder.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface LoadTagsCallback<T> {

	Event<LoadTagsCallback<Item>> ITEM = EventFactory.createArrayBacked(LoadTagsCallback.class,
			listeners -> handler -> {
				for (LoadTagsCallback<Item> listener : listeners)
					listener.load(handler);
			});

	Event<LoadTagsCallback<Block>> BLOCK = EventFactory.createArrayBacked(LoadTagsCallback.class,
			listeners -> handler -> {
				for (LoadTagsCallback<Block> listener : listeners)
					listener.load(handler);
			});

	Event<LoadTagsCallback<Fluid>> FLUID = EventFactory.createArrayBacked(LoadTagsCallback.class,
			listeners -> handler -> {
				for (LoadTagsCallback<Fluid> listener : listeners)
					listener.load(handler);
			});

	void load(TagHandler<T> handler);

	@SuppressWarnings("unchecked")
	interface TagHandler<T> {
		Map<Identifier, Collection<RegistryEntry<T>>> get();

		void register(Identifier tag, T... values);

		void remove(Identifier tag, T... values);

		List<T> get(Identifier tag);

		List<TagKey<T>> getKeys();

		default void register(TagKey<T> key, T... values) {
			register(key.id(), values);
		}

		default void remove(TagKey<T> key, T... values) {
			remove(key.id(), values);
		}

		default List<T> get(TagKey<T> tag) {
			return get(tag.id());
		}
	}
}
