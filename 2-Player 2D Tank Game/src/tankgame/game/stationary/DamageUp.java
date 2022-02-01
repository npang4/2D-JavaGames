package tankgame.game.stationary;


import tankgame.game.Collidable;
import tankgame.game.moveable.TRE;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DamageUp extends PowerUp{
    int x, y;
    BufferedImage PowerUp;
    Rectangle hitBox;

    public DamageUp(int x, int y, BufferedImage PowerUp) {
        this.x = x;
        this.y = y;
        this.PowerUp = PowerUp;
        this.hitBox = new Rectangle(x, y, this.PowerUp.getWidth(), this.PowerUp.getHeight());
    }

    @Override
    public void update(TRE game) {

    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.PowerUp, x, y, null);

    }


    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable with) {

    }
}
