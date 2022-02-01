package tankgame.game.moveable;

import tankgame.GameConstants;
import tankgame.Resource;
import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.stationary.Wall;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements Collidable {
    int x, y, vx, vy;
    float angle;
    int R = 7;
    BufferedImage bulletImage;
    Rectangle hitBox;

    public Bullet(int x, int y, float angle, BufferedImage bulletImage) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.bulletImage = bulletImage;
        this.hitBox = new Rectangle(x, y, Resource.getResourceImage("bulletImage").getWidth(), Resource.getResourceImage("bulletImage").getHeight());
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable with) {
        if(with instanceof Wall){
            R = 0;
        }

    }

    public void moveForwards(){
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    public void checkBorder(){
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT  - 80) {
            y = GameConstants.WORLD_HEIGHT  - 80;
        }
    }
    public void update(TRE game){
        moveForwards();
    }

    public void drawImage(Graphics g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), Resource.getResourceImage("bulletImage").getWidth() / 2.0, Resource.getResourceImage("bulletImage").getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.bulletImage, rotation, null);


    }
}
