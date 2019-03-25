package io.github.strikerrocker.entities.player;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.Text;
import io.github.strikerrocker.items.ItemStack;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Inventory {
    private Handler handler;
    private boolean active = false;
    private ArrayList<ItemStack> inventoryItems;
    private int selectedItem = 0;

    public Inventory(Handler handler) {
        this.handler = handler;
        inventoryItems = new ArrayList<>();
    }

    public ArrayList<ItemStack> getInventoryItems() {
        return inventoryItems;
    }

    public void tick() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
            active = !active;
        if (active && handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE))
            active = false;
        getInventoryItems().forEach(ItemStack::tick);
        if (!active)
            return;

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP))
            selectedItem--;
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN))
            selectedItem++;

        if (selectedItem < 0)
            selectedItem = inventoryItems.size() - 1;
        else if (selectedItem >= inventoryItems.size())
            selectedItem = 0;
    }

    public void render(Graphics g) {
        if (!active)
            return;
        int guiX = 64;
        int guiY = 64;
        int guiWidth = handler.getWidth() - guiX * 2;
        int guiHeight = handler.getHeight() - guiY * 2;
        int invListCenterX = 64 + (guiWidth / 3);
        int invListCenterY = (int) (64 + guiHeight / 1.93);
        int invListSpacing = (handler.getHeight() - (guiY * 2) - 61) / 11;
        g.drawImage(Assets.inventoryScreen, guiX, guiY, guiWidth, guiHeight, null);

        int len = inventoryItems.size();
        if (len == 0) {
            return;
        }
        for (int i = -5; i < 6; i++) {
            if (selectedItem + i < 0 || selectedItem + i >= len)
                continue;
            if (i == 0) {
                Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName() + " <", invListCenterX,
                        invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font28.deriveFont((float) invListSpacing * 3 / 2));
            } else {
                Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX,
                        invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font28.deriveFont((float) invListSpacing * 3 / 2));
            }
        }

        ItemStack stack = inventoryItems.get(selectedItem);
        g.drawImage(stack.getTexture(), (int) (guiWidth / 1.34) + guiX, (guiHeight / 14) + guiY, (int) (guiWidth / 6.7), (guiHeight / 5), null);
        Text.drawString(g, Integer.toString(stack.getCount()), (int) (guiWidth / 1.22) + guiX, (guiHeight / 3) + guiY, true, Color.WHITE, Assets.font28.deriveFont((float) invListSpacing));
    }

    public void addItem(ItemStack stack) {
        int notInserted = stack.getCount();
        for (ItemStack invStack : inventoryItems) {
            if (invStack.getItem() == stack.getItem()) {
                notInserted = invStack.incSize(notInserted);
            }
        }
        if (notInserted == 0) return;
        if (notInserted > 0) {
            stack.setCount(notInserted);
            inventoryItems.add(stack);
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public boolean isActive() {
        return active;
    }
}