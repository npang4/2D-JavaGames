package tankgame.game.moveable;

import tankgame.GameConstants;
import tankgame.Resource;
import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.stationary.*;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends GameObject implements Collidable {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int lifeCount = 3;
    private int health = 100;


    private float angle;
    private int screen_x;
    private int screen_y;
    private ArrayList<Bullet> ammo;
    private Rectangle hitBox;

    private int R = 2;
    private final float ROTATIONSPEED = 3.0f;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    private boolean damageUpPressed;

    public Tank(int x, int y, int vx, int vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.ammo = new ArrayList<>();
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());

    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable with) {
        if (with instanceof Wall) {
            if (this.UpPressed) {
                this.x -= this.vx;
                this.y -= this.vy;
            } else if (this.DownPressed) {
                this.x -= this.vx;
                this.y -= this.vy;
            }
        }
        if (with instanceof Tank) {
            if (this.UpPressed) {
                this.x -= this.vx;
                this.y -= this.vy;
            } else if (this.DownPressed) {
                this.x -= this.vx;
                this.y -= this.vy;
            }
        }
        if (with instanceof Bullet && !this.ammo.contains((Bullet) with)) {
            this.health -= 3;

        }
        if (with instanceof HealthUp) {
            while (this.health < 100) {
                this.health += 3;
            }

        }
        if (with instanceof SpeedUp) {
            R = 3;
        }
        if (with instanceof DamageUp) {

        }

    }

    public int getScreen_x() {
        return screen_x;
    }

    public int getScreen_y() {
        return screen_y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    void toggleShootPressed() {
        this.shootPressed = true;
    }

    void toggleDamagePressed(){this.damageUpPressed = true;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.shootPressed = false;
    }

    void unToggleDamagePressed() {this.damageUpPressed = false;}

    public void update(TRE game) {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.shootPressed && TRE.tick % 25 == 0) {
            Bullet b = new Bullet(x, y, angle, Resource.getResourceImage("bulletImage"));
            this.ammo.add(b);
            game.obj.add(b);

        }
        this.ammo.forEach(b -> b.update(game));
        if(this.damageUpPressed && TRE.tick % 15 == 0){
            Bullet laser = new Bullet(x, y, angle, Resource.getResourceImage("laser"));
            this.ammo.add(laser);
            game.obj.add(laser);
        }
        this.ammo.forEach(b -> b.update(game));
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        checkSplitScreen();
        this.hitBox.setLocation(x, y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        checkSplitScreen();
        this.hitBox.setLocation(x, y);
    }


    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    private void checkSplitScreen() {
        //x - screenw / 4  - center of width
        //y - screeny / 2  - center of height

        this.screen_x = this.getX() - GameConstants.GAME_SCREEN_WIDTH / 4;
        this.screen_y = this.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2;

        if (this.screen_x < 0) {
            this.screen_x = 0;
        }
        if (this.screen_y < 0) {
            this.screen_y = 0;
        }
        if (this.screen_x > GameConstants.WORLD_WIDTH - (GameConstants.GAME_SCREEN_WIDTH / 2)) {
            this.screen_x = GameConstants.WORLD_WIDTH - (GameConstants.GAME_SCREEN_WIDTH / 2);
        }
        if (this.screen_y > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) {
            this.screen_y = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }


    }

    private void drawHealthBar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (this.health <= 100 && this.health > 75) {
            g2d.setColor(Color.green);
            g2d.fillRect(x - 25, y - 50, 90, 20);
        } else if (this.health <= 75 && this.health >= 40) {
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(x - 25, y - 50, 75, 20);
        } else if (this.health < 40 && this.health > 0) {
            g2d.setColor(Color.red);
            g2d.fillRect(x - 25, y - 50, 25, 20);
        } else if (this.health <= 0) {
            lifeCount -= 1;
            health = 100;

        }


    }

    public int getLivesRemaining(){
        return lifeCount;
    }


    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        this.ammo.forEach(bullet -> bullet.drawImage(g));
        g2d.drawImage(this.img, rotation, null);
        drawHealthBar(g2d);



    }

}
