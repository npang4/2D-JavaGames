package tankgame.game.stationary;



import tankgame.Resource;
import tankgame.game.Collidable;
import tankgame.game.moveable.TRE;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {
    int x, y;
    BufferedImage wallImage;
    Rectangle hitBox;

    public BreakableWall(int x, int y, BufferedImage wallImage) {
        this.x = x;
        this.y = y;
        this.wallImage = wallImage;
        this.hitBox = new Rectangle(x, y, Resource.getResourceImage("breakableWall").getWidth(), Resource.getResourceImage("breakableWall").getHeight());
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable with) {
    }


    @Override
    public void update(TRE game) {

    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.wallImage, x, y, null);


    }


}
