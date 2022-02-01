package tankgame;

import tankgame.game.moveable.TRE;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resource {
    private static Map<String, BufferedImage> resources;

    static{
        Resource.resources = new HashMap<>();
        try{
            Resource.resources.put("t1img", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank1.png"))));
            Resource.resources.put("t2img", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank2.png"))));
            Resource.resources.put("breakableWall", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall1.gif"))));
            Resource.resources.put("unBreakableWall", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall2.gif"))));
            Resource.resources.put("bulletImage", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("bullet.gif"))));
            Resource.resources.put("dmgUp", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("dmg.PNG"))));
            Resource.resources.put("speedUp", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("speedup.png"))));
            Resource.resources.put("healthUp", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health.gif"))));
            Resource.resources.put("laser", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("laserBullet.png"))));

        } catch (IOException e){
            e.printStackTrace();
            System.exit(-5);
        }
    }

    public static BufferedImage getResourceImage(String key){
        return Resource.resources.get(key);
    }
}
