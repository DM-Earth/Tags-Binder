# Tags Binder

This is a small library mod for **Minecraft 1.18.2 Fabric/Quilt** which allows mod and modpack developers to register/remove tags for items, blocks and fluids (will support more in the future).

## Usage

### Listening to `LoadTagsCallback` event

The `LoadTagsCallback` event will be called when the game is loading tags before tags binding in `Registry`. This event will provide a `TagHandler` which allows you to bind/unbind tag from items.

```java
// It can be ITEM, BLOCK or FLUID.
LoadTagsCallback.ITEM.register(handler -> {
    // Do anything you want here
})
```

### Bind tag with registrations

The `register` method in `TagHandler` will bind a tag with a registration. You can use `Identifier` or `TagKey` to specify the tag.

```java
LoadTagsCallback.ITEM.register(handler -> {
    // This will bind piglin_loved with both netherite ingot and netherite block.
    handler.register(ItemTags.PIGLIN_LOVED, Items.NETHERITE_INGOT, Items.NETHERITE_BLOCK);
	handler.register(new Identifier("test", "test"), Items.NETHER_BRICK);
})
```

### Unbind tag with registrations

The `remove` method in `TagHandler` will unbind a tag with a registration. You can use `Identifier` or `TagKey` to specify the tag.

```java
LoadTagsCallback.ITEM.register(handler -> {
    // Remove dirt from the dirt tag.
    handler.remove(ItemTags.DIRT, Items.DIRT);
    // Remove all items from the tag.
    handler.remove(ItemTags.CARPET);
})
```
