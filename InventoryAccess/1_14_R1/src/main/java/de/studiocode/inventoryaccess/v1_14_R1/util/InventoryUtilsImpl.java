package de.studiocode.inventoryaccess.v1_14_R1.util;

import de.studiocode.inventoryaccess.abstraction.util.InventoryUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_14_R1.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftContainer;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryUtilsImpl implements InventoryUtils {
    
    @Override
    public void openCustomInventory(Player player, Inventory inventory) {
        openCustomInventory(player, inventory, null);
    }
    
    @Override
    public void openCustomInventory(Player player, Inventory inventory, BaseComponent[] title) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        Containers<?> windowType = getNotchInventoryType(inventory);
        
        if (entityPlayer.playerConnection != null) {
            Container container = new CraftContainer(inventory, entityPlayer, entityPlayer.nextContainerCounter());
            container = CraftEventFactory.callInventoryOpenEvent(entityPlayer, container);
            if (container != null) {
                IInventory iinventory = ((CraftInventory) inventory).getInventory();
                IChatBaseComponent titleComponent;
                if (title == null) {
                    if (iinventory instanceof ITileInventory)
                        titleComponent = ((ITileInventory) iinventory).getScoreboardDisplayName();
                    else titleComponent = new ChatComponentText(container.getBukkitView().getTitle());
                } else titleComponent = createNMSComponent(title);
                
                entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(container.windowId, windowType, titleComponent));
                entityPlayer.activeContainer = container;
                entityPlayer.activeContainer.addSlotListener(entityPlayer);
            }
        }
    }
    
    @Override
    public void updateOpenInventoryTitle(Player player, BaseComponent[] title) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        Container container = entityPlayer.activeContainer;
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(container.windowId, container.getType(), createNMSComponent(title)));
    }
    
    private static Containers<?> getNotchInventoryType(Inventory inventory) {
        InventoryType type = inventory.getType();
        if (type == InventoryType.CHEST) {
            switch (inventory.getSize()) {
                case 9:
                    return Containers.GENERIC_9X1;
                case 18:
                    return Containers.GENERIC_9X2;
                case 27:
                    return Containers.GENERIC_9X3;
                case 36:
                    return Containers.GENERIC_9X4;
                case 45:
                    return Containers.GENERIC_9X5;
                case 54:
                    return Containers.GENERIC_9X6;
                default:
                    throw new IllegalArgumentException("Unsupported custom inventory size " + inventory.getSize());
            }
        } else return CraftContainer.getNotchInventoryType(type);
    }
    
    public static IChatBaseComponent createNMSComponent(BaseComponent[] components) {
        String json = ComponentSerializer.toString(components);
        return IChatBaseComponent.ChatSerializer.a(json);
    }
    
}
