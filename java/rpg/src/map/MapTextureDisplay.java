import java.awt.*;
import javax.swing.*;

public class MapTextureDisplay extends JComponent {

    private Map theMap;

    public MapTextureDisplay(Map theMap) {
        this.theMap = theMap;

    }



    public static void main(String args[]) {
        Map myMap = new Map();
        new MapTextureDisplay(myMap);
    }
}
