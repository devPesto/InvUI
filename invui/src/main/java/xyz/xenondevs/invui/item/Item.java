package xyz.xenondevs.invui.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.window.Window;

import java.util.Set;

public interface Item {
    
    /**
     * Gets the {@link ItemProvider}.
     * This method gets called every time a {@link Window} is notified ({@link #notifyWindows()}).
     *
     * @return The {@link ItemProvider}
     */
    ItemProvider getItemProvider();
    
    /**
     * Adds a {@link Window} to the window set, telling the {@link Item} that it is
     * currently being displayed in that {@link Window}.
     *
     * @param window The {@link Window} the {@link Item} is currently displayed in.
     */
    void addWindow(Window window);
    
    /**
     * Removes an {@link Window} from the window set, telling the {@link Item} that it
     * is no longer being displayed in that {@link Window}.
     *
     * @param window The {@link Window} the {@link Item} is no longer displayed in.
     */
    void removeWindow(Window window);
    
    /**
     * Gets an immutable {@link Set} of all the {@link Window}s where this
     * {@link Item} is displayed in.
     *
     * @return An immutable view of the {@link Set} of all the {@link Window}s
     * where this {@link Item} is displayed in.
     */
    Set<Window> getWindows();
    
    /**
     * Calls the {@link Window#handleItemProviderUpdate(Item)} method on every {@link Window}
     * in the set, notifying them that the {@link ItemProvider} has been updated
     * and the {@link ItemStack} inside the {@link Window}'s {@link Inventory} should
     * be replaced.
     */
    void notifyWindows();
    
    /**
     * A method called if the the {@link ItemStack} associated to this {@link Item}
     * has been clicked by a player.
     *
     * @param clickType The {@link ClickType} the {@link Player} did.
     * @param player    The {@link Player} who clicked on the {@link ItemStack}
     * @param event     The {@link InventoryClickEvent} associated with this click.
     */
    void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event);
    
}
