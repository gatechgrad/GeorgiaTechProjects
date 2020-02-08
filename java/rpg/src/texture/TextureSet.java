import java.util.Vector;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * holds a set of textures
 * 
 * @author Levi D. Smith
 * @version 0.1, December 30, 2000
 */
public class TextureSet implements Serializable {

    //INSTANCE VARIABLES
    private Vector vectTextures;
    private String strName;

    /**
     * called when object is instantiated
     */
    public TextureSet() {
        vectTextures = new Vector();
        strName = "";
    }
    public TextureSet(String str) {
        vectTextures = new Vector();
        strName = str;
    }



    public void setName(String str) { strName = str; }
    public void addTexture(ImageIcon img) { vectTextures.add(img); }
    public void setTextures(Vector vect) { vectTextures = vect; }

    public String getName() { return strName; }
    public Vector getTextures() { return vectTextures; }

    /**
     * String representing this object
     */
    public String toString() {
        return strName;
    }

}
