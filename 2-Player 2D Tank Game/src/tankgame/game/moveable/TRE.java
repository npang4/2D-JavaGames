package tankgame.game.moveable;

import tankgame.GameConstants;
import tankgame.Launcher;
import tankgame.Resource;


import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.stationary.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


public class TRE extends JPanel implements Runnable{

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private Launcher lf;

    static long tick = 0;
    ArrayList<GameObject> obj;
    public TRE(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            this.resetGame();
            while (true) {
                tick++;
                this.t1.update(this); // update tank
                this.t2.update(this); // update tank2
                this.repaint();   // redraw game

                for(int i = 0; i < obj.size(); i++){
                    Collidable c = obj.get(i);
                    if(c instanceof Wall || c instanceof PowerUp) continue;
                    for(int j = 0; j < obj.size(); j++){
                        if(i == j){
                            continue;
                        }
                        Collidable co = this.obj.get(j);
                        if(c.getHitBox().intersects(co.getHitBox())){
                            c.handleCollision(co);
                            if(!(c instanceof Tank) && co instanceof BreakableWall){
                                obj.remove(co);
                            }
                        }

                    }
                }

                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
//                if (this.tick > 2000) {
//                    this.lf.setFrame("end");
//                    return;
//                }
                if(t1.getLivesRemaining() <= 0){
                    this.lf.setFrame("tank2");
                    return;
                }
                else if(t2.getLivesRemaining() <= 0){
                    this.lf.setFrame("tank1");
                    return;
                }

            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(130);
        this.t1.setY(460);
        this.t2.setX(1700);
        this.t2.setY(735);

    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        obj = new ArrayList<>();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */

            InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(TRE.class.getClassLoader().getResourceAsStream("maps/map1")));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if(row == null){
                throw new IOException("no data found");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for(int curRow = 0; curRow < numRows; curRow++){
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol < numCols; curCol++){
                    switch (mapInfo[curCol]) {
                        case "2" -> {
                            BreakableWall br = new BreakableWall(curCol * 32, curRow * 32, Resource.getResourceImage("breakableWall"));
                            this.obj.add(br);
                        }
                        case "4" -> {
                            DamageUp du = new DamageUp(curCol * 30, curRow * 30, Resource.getResourceImage("dmgUp"));
                            this.obj.add(du);
                        }
                        case "5" -> {
                            SpeedUp su = new SpeedUp(curCol * 30, curRow * 30, Resource.getResourceImage("speedUp"));
                            this.obj.add(su);
                        }
                        case "6" -> {
                            HealthUp hu = new HealthUp(curCol * 30, curRow * 30, Resource.getResourceImage("healthUp"));
                            this.obj.add(hu);
                        }
                        case "3", "9" -> {
                            UnbreakableWall ubr = new UnbreakableWall(curCol * 32, curRow * 32, Resource.getResourceImage("unBreakableWall"));
                            this.obj.add(ubr);
                        }
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        
        t1 = new Tank(120, 460, 0, 0, 0, Resource.getResourceImage("t1img"));
        t2 = new Tank(1700, 735, 0, 0, 180, Resource.getResourceImage("t2img"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_V);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_SHIFT);

        this.obj.add(t1);
        this.obj.add(t2);

        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

        buffer.setColor(Color.black);
        buffer.fillRect(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        this.obj.forEach(obj -> obj.drawImage(buffer));

        BufferedImage leftHalf = world.getSubimage(t1.getScreen_x(),t1.getScreen_y(), GameConstants.GAME_SCREEN_WIDTH /  2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(t2.getScreen_x(), t2.getScreen_y(), GameConstants.GAME_SCREEN_WIDTH /  2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage mm = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        g2.drawImage(leftHalf, 0, 0, null);
        g2.drawImage(rightHalf, GameConstants.GAME_SCREEN_WIDTH /  2 + 1, 0, null);
        g2.scale(.1, .1);
        g2.drawImage(mm, 5500, 200, null);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 250));
        g2.setColor(Color.white);
        g2.drawString("Tank 1 Lives Remaining: " + t1.getLivesRemaining(), 120, 300);
        g2.drawString("Tank 2 Lives Remaining: " + t2.getLivesRemaining(), 9300, 300);




    }



}
