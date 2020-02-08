import java.awt.*;

public class RPGGeneratorConstants {
    //CONSTANTS
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    

    /**
     * returns the point where the passed in window should be placed to center the window
     */
    public static Point getCenteredWindowLocation(Window theWindow) {
        Point pntToReturn;
        
        pntToReturn = new Point((SCREEN_SIZE.width - theWindow.getWidth()) / 2,
                                (SCREEN_SIZE.height - theWindow.getHeight()) / 2
                                );
        return pntToReturn;
    }


}
