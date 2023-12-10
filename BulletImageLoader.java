import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BulletImageLoader {
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] bulletImages = null;
    private static Map<String, Image> imgs = new HashMap<String, Image>();

    static {
        bulletImages = new Image[]{
                tk.getImage(Bullets.class.getClassLoader().getResource("images/bulletL.gif")),
                tk.getImage(Bullets.class.getClassLoader().getResource("images/bulletU.gif")),
                tk.getImage(Bullets.class.getClassLoader().getResource("images/bulletR.gif")),
                tk.getImage(Bullets.class.getClassLoader().getResource("images/bulletD.gif")),
        };

        imgs.put("L", bulletImages[0]);
        imgs.put("U", bulletImages[1]);
        imgs.put("R", bulletImages[2]);
        imgs.put("D", bulletImages[3]);
    }

    public static Image getBulletImage(String direction) {
        return imgs.get(direction);
    }
}
