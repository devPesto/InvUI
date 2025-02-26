package xyz.xenondevs.invui.window.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.inventoryaccess.InventoryAccess;
import xyz.xenondevs.inventoryaccess.abstraction.inventory.CartographyInventory;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;
import xyz.xenondevs.inventoryaccess.map.MapIcon;
import xyz.xenondevs.inventoryaccess.map.MapPatch;
import xyz.xenondevs.invui.gui.AbstractGui;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.impl.NormalGuiImpl;
import xyz.xenondevs.invui.util.MathUtils;
import xyz.xenondevs.invui.window.AbstractSplitWindow;
import xyz.xenondevs.invui.window.CartographyWindow;

import java.util.List;

public final class CartographySplitWindowImpl extends AbstractSplitWindow implements CartographyWindow {
    
    private final CartographyInventory cartographyInventory;
    private int mapId;
    
    public CartographySplitWindowImpl(
        @NotNull Player player,
        @Nullable ComponentWrapper title,
        @NotNull AbstractGui upperGui,
        @NotNull AbstractGui lowerGui,
        boolean closeable,
        boolean retain
    ) {
        super(player, title, createWrappingGui(upperGui), lowerGui, null, false, closeable, retain);
        
        cartographyInventory = InventoryAccess.createCartographyInventory(player, title);
        upperInventory = cartographyInventory.getBukkitInventory();
        
        initUpperItems();
        resetMap();
        register();
    }
    
    @SuppressWarnings("deprecation")
    private static AbstractGui createWrappingGui(Gui upperGui) {
        if (upperGui.getWidth() != 2 || upperGui.getHeight() != 1)
            throw new IllegalArgumentException("Gui has to be 2x1");
        
        NormalGuiImpl wrapperGui = new NormalGuiImpl(3, 1);
        wrapperGui.fillRectangle(1, 0, upperGui, true);
        return wrapperGui;
    }
    
    @Override
    public void updateMap(@Nullable MapPatch patch, @Nullable List<MapIcon> icons) {
        InventoryAccess.getPlayerUtils().sendMapUpdate(getViewer(), mapId, (byte) 0, false, patch, icons);
    }
    
    @Override
    @SuppressWarnings({"deprecation", "DuplicatedCode"})
    public void resetMap() {
        mapId = -MathUtils.RANDOM.nextInt(Integer.MAX_VALUE);
        ItemStack map = new ItemStack(Material.FILLED_MAP);
        MapMeta mapMeta = (MapMeta) map.getItemMeta();
        mapMeta.setMapId(mapId);
        map.setItemMeta(mapMeta);
        cartographyInventory.setItem(0, map);
    }
    
    @Override
    public void show() {
        if (isRemoved())
            throw new IllegalStateException("The Window has already been closed.");
        
        Player viewer = getViewer();
        if (viewer == null)
            throw new IllegalStateException("The player is not online.");
        cartographyInventory.open();
    }
    
}
