package tankgame.game.stationary;

import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.moveable.TRE;

import java.awt.*;

public abstract class PowerUp extends GameObject implements Collidable {
    public abstract void drawImage(Graphics g);

    public abstract void update(TRE game);



}
