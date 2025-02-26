package xyz.xenondevs.invui.item.impl.controlitem;

import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.impl.BaseItem;

/**
 * A special type of {@link Item} that stores the {@link Gui} in which it is displayed.
 *
 * @param <G> The Gui Type this {@link ControlItem} will control. Not checked when adding it to a Gui.
 */
public abstract class ControlItem<G extends Gui> extends BaseItem {
    
    private G gui;
    
    public abstract ItemProvider getItemProvider(G gui);
    
    @Override
    public final ItemProvider getItemProvider() {
        return getItemProvider(gui);
    }
    
    public G getGui() {
        return gui;
    }
    
    @SuppressWarnings("unchecked")
    public void setGui(Object gui) {
        if (this.gui == null) this.gui = (G) gui;
    }
    
}
