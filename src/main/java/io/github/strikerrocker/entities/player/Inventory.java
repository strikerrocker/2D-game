package io.github.strikerrocker.entities.player;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.Text;
import io.github.strikerrocker.items.ItemStack;
import io.github.strikerrocker.items.Items;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;

import static io.github.strikerrocker.blocks.Block.BLOCKHEIGHT;
import static io.github.strikerrocker.blocks.Block.BLOCKWIDTH;

public class Inventory {
    private Handler handler;
    private boolean active = false;
    @Expose
    private ArrayList<ItemStack> inventoryItems;
    private int invSelectedItem = 0;
    private int hotBarSelectedItem = 0;

    public Inventory(Handler handler) {
        this.handler = handler;
        inventoryItems = new ArrayList<>();
        inventoryItems.add(new ItemStack(Items.apple));
    }

    public void setHotBarSelectedItem(int hotBarSelectedItem) {
        this.hotBarSelectedItem = hotBarSelectedItem;
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
        getInventoryItems().stream().filter(Objects::nonNull).filter(itemStack -> itemStack.getCount() <= 0).forEach(itemStack -> inventoryItems.remove(itemStack));
        if (!active)
            return;

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP))
            invSelectedItem--;
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN))
            invSelectedItem++;

        if (invSelectedItem < 0)
            invSelectedItem = inventoryItems.size() - 1;
        else if (invSelectedItem >= inventoryItems.size())
            invSelectedItem = 0;
    }

    public ItemStack getHotbarItem() {
        return hotBarSelectedItem < inventoryItems.size() ? inventoryItems.get(hotBarSelectedItem) : null;
    }

    public void renderHotbar(Graphics graphics) {
        if (active)
            return;
        int guiWidth = BLOCKWIDTH * 3 / 4, guiHeight = BLOCKHEIGHT * 3 / 4;
        int hotBarX = handler.getWidth() / 2 - guiWidth;
        int hotBarY = handler.getHeight() - guiHeight;
        graphics.drawImage(Assets.hotBar, hotBarX - guiWidth / 12, hotBarY - guiHeight / 12, guiWidth, guiHeight, null);
        if (hotBarSelectedItem < inventoryItems.size()) {
            graphics.drawImage(inventoryItems.get(hotBarSelectedItem).getTexture(), hotBarX, hotBarY, guiWidth - guiWidth / 6, guiHeight - guiHeight / 6, null);
        }
    }

    public void renderInv(Graphics graphics) {
        if (!active)
            return;
        int guiX = 64;
        int guiY = 64;
        int guiWidth = handler.getWidth() - guiX * 2;
        int guiHeight = handler.getHeight() - guiY * 2;
        int invListCenterX = 64 + (guiWidth / 3);
        int invListCenterY = (int) (64 + guiHeight / 1.93);
        int invListSpacing = (handler.getHeight() - (guiY * 2) - 61) / 11;
        graphics.drawImage(Assets.inventoryScreen, guiX, guiY, guiWidth, guiHeight, null);

        int len = inventoryItems.size();
        if (len == 0) {
            return;
        }
        for (int i = -5; i < 6; i++) {
            if (invSelectedItem + i < 0 || invSelectedItem + i >= len)
                continue;
            if (i == 0) {
                Text.drawString(graphics, "> " + inventoryItems.get(invSelectedItem + i).getName() + " <", invListCenterX,
                        invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font28.deriveFont((float) invListSpacing * 3 / 2));
            } else {
                Text.drawString(graphics, inventoryItems.get(invSelectedItem + i).getName(), invListCenterX,
                        invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font28.deriveFont((float) invListSpacing * 3 / 2));
            }
        }

        ItemStack stack = inventoryItems.get(invSelectedItem);
        graphics.drawImage(stack.getTexture(), (int) (guiWidth / 1.34) + guiX, (guiHeight / 14) + guiY, (int) (guiWidth / 6.7), (guiHeight / 5), null);
        Text.drawString(graphics, Integer.toString(stack.getCount()), (int) (guiWidth / 1.22) + guiX, (guiHeight / 3) + guiY, true, Color.WHITE, Assets.font28.deriveFont((float) invListSpacing));
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