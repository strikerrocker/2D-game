package main.java.io.github.strikerrocker;

import main.java.io.github.strikerrocker.gfx.Assets;
import main.java.io.github.strikerrocker.gfx.Text;
import main.java.io.github.strikerrocker.items.Item;
import main.java.io.github.strikerrocker.items.ItemStack;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Inventory {
    private Handler handler;
    private boolean active = false;
    private ArrayList<ItemStack> inventoryItems;
    private int invX = 64, invY = 48,
            invWidth = 512, invHeight = 384,
            invListCenterX = invX + 171,
            invListCenterY = invY + invHeight / 2 + 5,
            invListSpacing = 30;
    private int invImageX = 452, invImageY = 82,
            invImageWidth = 64, invImageHeight = 64;
    private int invCountX = 484, invCountY = 172;
    private int selectedItem = 0;

    public Inventory(Handler handler) {
        this.handler = handler;
        inventoryItems = new ArrayList<>();
        inventoryItems.add(new ItemStack(Item.woodItem, 2));
        inventoryItems.add(new ItemStack(Item.rockItem, 2));
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

        g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);

        int len = inventoryItems.size();
        if (len == 0)
            return;

        for (int i = -5; i < 6; i++) {
            if (selectedItem + i < 0 || selectedItem + i >= len)
                continue;
            if (i == 0) {
                Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName() + " <", invListCenterX,
                        invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font28);
            } else {
                Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX,
                        invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font28);
            }
        }

        ItemStack stack = inventoryItems.get(selectedItem);
        g.drawImage(stack.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
        Text.drawString(g, Integer.toString(stack.getCount()), invCountX, invCountY, true, Color.WHITE, Assets.font28);
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