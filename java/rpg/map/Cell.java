import java.io.Serializable;
import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * contains the data for a map cell
 * @author Levi D. Smith
 * @version 1.00
 */
public class Cell implements Serializable {

    //INSTANCE VARIABLES
    private int iImageNumber; //the ID of the image
    private ImageIcon imgTexture;
    private int iHeight;
    private boolean isPassable;


    /**
     * default constructor
     */
    public Cell() {
        imgTexture = null;
    }

    public void setImageIcon(ImageIcon img) { imgTexture = img; }
    public void setHeight(int i) { iHeight = i; }
    public void setIsPassable(boolean b) { isPassable = b; }


    public Image getImage() { 
        Image imgToReturn;

        imgToReturn = null;

        if (imgTexture != null) {
            imgToReturn = imgTexture.getImage();
        }
        
        return imgToReturn; 
    }
}
