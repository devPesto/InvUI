package xyz.xenondevs.invui.window;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;
import xyz.xenondevs.invui.gui.AbstractGui;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.SlotElement;
import xyz.xenondevs.invui.util.Pair;
import xyz.xenondevs.invui.util.SlotUtils;

/**
 * A {@link Window} where top and player {@link Inventory} are affected by the same {@link Gui}.
 */
public abstract class AbstractMergedWindow extends AbstractDoubleWindow {
    
    private final AbstractGui gui;
    
    public AbstractMergedWindow(Player player, ComponentWrapper title, AbstractGui gui, Inventory upperInventory, boolean closeable, boolean retain) {
        super(player, title, gui.getSize(), upperInventory, closeable, retain);
        this.gui = gui;
        
        gui.addParent(this);
        initUpperItems();
    }
    
    @Override
    public void handleSlotElementUpdate(Gui child, int slotIndex) {
        redrawItem(slotIndex, gui.getSlotElement(slotIndex), true);
    }
    
    @Override
    protected SlotElement getSlotElement(int index) {
        return gui.getSlotElement(index);
    }
    
    @Override
    protected Pair<AbstractGui, Integer> getWhereClicked(InventoryClickEvent event) {
        Inventory clicked = event.getClickedInventory();
        int slot = event.getSlot();
        int clickedIndex = clicked == getUpperInventory() ? slot
            : getUpperInventory().getSize() + SlotUtils.translatePlayerInvToGui(slot);
        return new Pair<>(gui, clickedIndex);
    }
    
    @Override
    protected Pair<AbstractGui, Integer> getGuiAt(int index) {
        return index < gui.getSize() ? new Pair<>(gui, index) : null;
    }
    
    @Override
    public AbstractGui[] getGuis() {
        return new AbstractGui[] {gui};
    }
    
}
