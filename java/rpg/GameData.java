import java.util.Vector;
import java.io.Serializable;

/**
 * holds all of the data for a game
 * @author <a href="mailto:command@cc.gatech.edu">Levi D. Smith</a>
 * @version 0.01, December 23, 2000
 */
public class GameData implements Serializable {

    private Vector vectMonsters;
    private Vector vectTextures;
    private Vector vectMaps;
    
    /**
     * default constructor
     */
    public GameData() {
        vectMonsters = new Vector();
        vectTextures = new Vector();
        vectMaps = new Vector();
    }

    public Vector getMonsters() { return vectMonsters; }
    public Vector getTextures() { return vectTextures; }
    public Vector getMaps() { return vectMaps; }

    public void setMonsters(Vector theVector) { vectMonsters = theVector; }
    public void setTextures(Vector theVector) { vectTextures = theVector; }
    public void setMaps(Vector theVector) { vectMaps = theVector; }
}
