package com.dm.earth.tags_binder.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.dm.earth.tags_binder.api.ResourceConditionCheckTagCallback;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.fabricmc.fabric.impl.resource.conditions.ResourceConditionsImpl;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

@SuppressWarnings("UnstableApiUsage")
@Mixin(ResourceConditionsImpl.class)
public class ResourceConditionsImplMixin {
	@Inject(method = "tagsPopulatedMatch(Lcom/google/gson/JsonObject;Lnet/minecraft/registry/RegistryKey;)Z",
			at = @At("HEAD"), cancellable = true)
	private static <T> void tagsPopulatedMatch(JsonObject object,
			RegistryKey<? extends Registry<T>> registryKey, CallbackInfoReturnable<Boolean> cir) {
		JsonArray array = JsonHelper.getArray(object, "values");

		for (JsonElement element : array) {
			if (element.isJsonPrimitive()) {
				Identifier id = new Identifier(element.getAsString());

				if (registryKeyEq(registryKey, RegistryKeys.ITEM)) {
					ActionResult result = ResourceConditionCheckTagCallback.ITEM.invoker()
							.apply(TagKey.of(RegistryKeys.ITEM, id));
					if (result != ActionResult.PASS) {
						cir.setReturnValue(result.isAccepted());
						return;
					}
				} else if (registryKeyEq(registryKey, RegistryKeys.BLOCK)) {
					ActionResult result = ResourceConditionCheckTagCallback.BLOCK.invoker()
							.apply(TagKey.of(RegistryKeys.BLOCK, id));
					if (result != ActionResult.PASS) {
						cir.setReturnValue(result.isAccepted());
						return;
					}
				} else if (registryKeyEq(registryKey, RegistryKeys.FLUID)) {
					ActionResult result = ResourceConditionCheckTagCallback.FLUID.invoker()
							.apply(TagKey.of(RegistryKeys.FLUID, id));
					if (result != ActionResult.PASS) {
						cir.setReturnValue(result.isAccepted());
						return;
					}
				}
			} else {
				throw new JsonParseException("Invalid tag id entry: " + element);
			}
		}
	}

	private static boolean registryKeyEq(RegistryKey<?> key1, RegistryKey<?> key2) {
		return key1.getValue().equals(key2.getValue());
	}
}
