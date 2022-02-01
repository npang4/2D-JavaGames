package tankgame.game;

import tankgame.game.moveable.TRE;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject implements Collidable{

    int x;
    int y;
    int vx;
    int vy;
    int R;
    BufferedImage img;
    float angle;
    Rectangle r;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setR(int r) {
        R = r;
    }


    public abstract void drawImage(Graphics g);
    public abstract void update(TRE game);

}


