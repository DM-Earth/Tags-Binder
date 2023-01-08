package com.dm.earth.tags_binder.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;

@FunctionalInterface
public interface ResourceConditionCheckTagCallback<T> {
	Event<ResourceConditionCheckTagCallback<Item>> ITEM = EventFactory
			.createArrayBacked(ResourceConditionCheckTagCallback.class, listeners -> key -> {
				for (ResourceConditionCheckTagCallback<Item> listener : listeners) {
					ActionResult result = listener.resourceConditionCheckTag(key);
					if (result != ActionResult.PASS)
						return result;
				}
				return ActionResult.PASS;
			});

	Event<ResourceConditionCheckTagCallback<Block>> BLOCK = EventFactory
			.createArrayBacked(ResourceConditionCheckTagCallback.class, listeners -> key -> {
				for (ResourceConditionCheckTagCallback<Block> listener : listeners) {
					ActionResult result = listener.resourceConditionCheckTag(key);
					if (result != ActionResult.PASS)
						return result;
				}
				return ActionResult.PASS;
			});

	Event<ResourceConditionCheckTagCallback<Fluid>> FLUID = EventFactory
			.createArrayBacked(ResourceConditionCheckTagCallback.class, listeners -> key -> {
				for (ResourceConditionCheckTagCallback<Fluid> listener : listeners) {
					ActionResult result = listener.resourceConditionCheckTag(key);
					if (result != ActionResult.PASS)
						return result;
				}
				return ActionResult.PASS;
			});

	ActionResult resourceConditionCheckTag(TagKey<T> tagKey);
}
