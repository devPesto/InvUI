package xyz.xenondevs.invui.virtualinventory.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.virtualinventory.VirtualInventory;

/**
 * An event that is called after the {@link VirtualInventory} has been updated.
 */
public class InventoryUpdatedEvent extends UpdateEvent {
    
    /**
     * Creates a new {@link ItemUpdateEvent}.
     *
     * @param virtualInventory  The {@link VirtualInventory} where this action takes place.
     * @param updateReason      The {@link UpdateReason} for the calling of this event.
     *                          This will probably be a {@link PlayerUpdateReason} in most cases but can be a custom one
     *                          if you called the methods in the {@link VirtualInventory} yourself.
     *                          if it wasn't a {@link Player}
     * @param slot              The slot that is affected
     * @param previousItemStack The {@link ItemStack} that was on that slot previously
     * @param newItemStack      The {@link ItemStack} that is on that slot now
     */
    public InventoryUpdatedEvent(@NotNull VirtualInventory virtualInventory, int slot, @Nullable UpdateReason updateReason,
                                 @Nullable ItemStack previousItemStack, @Nullable ItemStack newItemStack) {
        
        super(virtualInventory, slot, updateReason, previousItemStack, newItemStack);
    }
    
}
