package io.github.strikerrocker.items;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.Bullet;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.gfx.Assets;

public class GunItemData extends ItemData {
    GunItemData(String name) {
        super(Assets.gun, name);
    }

    @Override
    void onRightClick(Handler handler, Player player, Item stack, int x, int y) {
        float playerPixelPos = player.getPixelPos().getX() - handler.getGameCamera().getXOffset();
        Bullet bullet;
        if (playerPixelPos >= x) {
            bullet = new Bullet(handler, player.getX() - 1.3f, player.getY());
        } else {
            bullet = new Bullet(handler, player.getX() + 0.8f, player.getY());
        }
        handler.getCurrentLevel().getEntityManager().addEntity(bullet);
        bullet.setDamage(2);
        if (playerPixelPos >= x) {
            bullet.move(4);
        } else {
            bullet.move(2);
        }
    }
}
