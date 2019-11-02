package ru.keupoz.macromodules.yaku;

import java.util.Arrays;

import net.eq2online.util.Game;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class ItemID {
    public final String identifier;
    public final int damage;
    public final Item item;
    public final ItemStack itemStack;

    public ItemID(String itemIdString) {
        String[] idParts = itemIdString.split(":");
        int damageTmp = -1;

        try {
            int i = idParts.length - 1;
            damageTmp = Integer.parseInt(idParts[i]);
            idParts = Arrays.copyOfRange(idParts, 0, i);
        } catch (NumberFormatException e) {}

        itemIdString = String.join(":", idParts);

        this.identifier = itemIdString;
        this.damage = damageTmp;

        this.item = Game.getItem(new ResourceLocation(itemIdString));
        this.itemStack = this.toItemStack(1);
    }

    public boolean isValid() {
        return this.item != null;
    }

    public ItemStack toItemStack(int count) {
        return new ItemStack(this.item, count, this.damage > -1 ? this.damage : 0);
    }

    private boolean equalsExpression(ItemStack itemStackIn) {
        return
            !itemStackIn.isEmpty() && (itemStackIn.getItem() == this.itemStack.getItem()) &&
            (!itemStackIn.getHasSubtypes() || (itemStackIn.getMetadata() == this.itemStack.getMetadata()));
    }

    private int getSlotFor(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < inventoryPlayer.mainInventory.size(); i++) {
            ItemStack invStack = inventoryPlayer.mainInventory.get(i);

            if (this.equalsExpression(invStack)) return i;
        }

        return -1;
    }

    public boolean inventoryPick(boolean pickInCreative) {
        if (!this.isValid()) return false;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;

        if (player == null) return false;

        ItemStack itemStackL = this.itemStack;

        InventoryPlayer inventoryPlayer = player.inventory;
        ItemStack currentItem = inventoryPlayer.getCurrentItem();

        if (this.equalsExpression(currentItem)) return true;

        int slotForItem = getSlotFor(inventoryPlayer);

        if (slotForItem == -1 && pickInCreative && player.capabilities.isCreativeMode) {
            if (!itemStackL.getHasSubtypes()) {
                itemStackL = new ItemStack(itemStackL.getItem(), itemStackL.getMaxStackSize());
            }

            inventoryPlayer.setPickedItemStack(itemStackL);
            mc.playerController.sendSlotPacket(player.getHeldItem(EnumHand.MAIN_HAND), 36 + inventoryPlayer.currentItem);
        } else if (slotForItem != -1) {
            if (InventoryPlayer.isHotbar(slotForItem)) {
                inventoryPlayer.currentItem = slotForItem;
            } else {
                mc.playerController.pickItem(slotForItem);
            }
        }

        currentItem = inventoryPlayer.getCurrentItem();
        return !currentItem.isEmpty() && this.itemStackEquals(currentItem);
    }

    public boolean itemStackEquals(ItemStack itemStack) {
        if (itemStack == null) return false;

        return ItemStack.areItemStacksEqual(itemStack, toItemStack(itemStack.getCount()));
    }
}
