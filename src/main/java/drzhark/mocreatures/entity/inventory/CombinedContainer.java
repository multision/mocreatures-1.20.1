package drzhark.mocreatures.entity.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * A container that combines multiple containers into one
 */
public class CombinedContainer implements Container {
    private final Container[] containers;
    private final int[] baseIndices;
    private final int totalSize;

    public CombinedContainer(Container... containers) {
        this.containers = containers;
        this.baseIndices = new int[containers.length];
        
        int index = 0;
        for (int i = 0; i < containers.length; i++) {
            this.baseIndices[i] = index;
            index += containers[i].getContainerSize();
        }
        this.totalSize = index;
    }

    @Override
    public int getContainerSize() {
        return totalSize;
    }

    @Override
    public boolean isEmpty() {
        for (Container container : containers) {
            if (!container.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        int containerIndex = getContainerIndexForSlot(slot);
        if (containerIndex < 0) {
            return ItemStack.EMPTY;
        }
        return containers[containerIndex].getItem(slot - baseIndices[containerIndex]);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        int containerIndex = getContainerIndexForSlot(slot);
        if (containerIndex < 0) {
            return ItemStack.EMPTY;
        }
        return containers[containerIndex].removeItem(slot - baseIndices[containerIndex], amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        int containerIndex = getContainerIndexForSlot(slot);
        if (containerIndex < 0) {
            return ItemStack.EMPTY;
        }
        return containers[containerIndex].removeItemNoUpdate(slot - baseIndices[containerIndex]);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        int containerIndex = getContainerIndexForSlot(slot);
        if (containerIndex >= 0) {
            containers[containerIndex].setItem(slot - baseIndices[containerIndex], stack);
        }
    }

    @Override
    public void setChanged() {
        for (Container container : containers) {
            container.setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        for (Container container : containers) {
            if (!container.stillValid(player)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clearContent() {
        for (Container container : containers) {
            container.clearContent();
        }
    }
    
    private int getContainerIndexForSlot(int slot) {
        if (slot < 0 || slot >= totalSize) {
            return -1;
        }
        
        for (int i = containers.length - 1; i >= 0; i--) {
            if (slot >= baseIndices[i]) {
                return i;
            }
        }
        
        return -1;
    }
} 