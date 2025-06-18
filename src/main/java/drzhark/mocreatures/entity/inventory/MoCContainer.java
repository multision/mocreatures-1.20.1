package drzhark.mocreatures.entity.inventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.LockCode;

import javax.annotation.Nullable;

/**
 * Helper class for animal containers in MoCreatures
 */
public class MoCContainer {
    private LockCode lockCode = LockCode.NO_LOCK;
    private Component name;
    private MoCAnimalChest.Size size;
    private Container inventory;

    public MoCContainer(String name, MoCAnimalChest.Size size, Container inventory) {
        this.name = Component.literal(name);
        this.size = size;
        this.inventory = inventory;
    }
    
    public Component getDisplayName() {
        return name;
    }

    /**
     * Creates a menu provider for this container
     */
    public SimpleMenuProvider createMenuProvider() {
        return new SimpleMenuProvider(
            (id, playerInventory, player) -> createMenu(id, playerInventory, player),
            this.name
        );
    }

    @Nullable
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player playerIn) {
        // Create a menu based on the number of rows and populate with the inventory
        AbstractContainerMenu menu;
        
        switch (this.size.getRows()) {
            case 1:
                menu = ChestMenu.oneRow(id, playerInventory);
                break;
            case 2:
                menu = ChestMenu.twoRows(id, playerInventory);
                break;
            case 3:
                menu = ChestMenu.threeRows(id, playerInventory);
                break;
            case 4:
                menu = ChestMenu.fourRows(id, playerInventory);
                break;
            case 5:
                menu = ChestMenu.fiveRows(id, playerInventory);
                break;
            case 6:
                menu = ChestMenu.sixRows(id, playerInventory);
                break;
            default:
                menu = ChestMenu.threeRows(id, playerInventory);
        }
        
        // Note: In 1.20.1, the inventory will be synced automatically
        // but we still need to manually tie the container to specific slots 
        // if needed in the future
        
        return menu;
    }
}
