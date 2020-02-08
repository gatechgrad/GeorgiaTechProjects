import java.util.Vector;
import javax.swing.ImageIcon;
import java.io.Serializable;


/**
 * represents a monster in the game
 */
public class Monster implements Serializable {
    //CONSTANTS
    public static final int IDLE_IMAGES = 0;
    public static final int ATTACK_IMAGES = 1;
    public static final int MAGIC_IMAGES = 2;
    public static final int DEATH_IMAGES = 3;




    //INSTANCE VARIABLES
    private int iMaxHP;
    private int iHP;
    private int iMaxMP;
    private int iMP;
    private int iLevel;
    private String strName;
    private int iExperience;
    private int iGold;
    private int iMaxWait;
    private int iWait;
    private int iMoney;

    private Vector vectImages; //holds images of the monster

    public Monster() {
        setupImageVector();
    }

    public Monster(String strName, int iMaxHP, int iMaxMP, int iLevel) {
        this.strName = strName;
        this.iMaxHP = iMaxHP;
        this.iMaxMP = iMaxMP;
        this.iLevel = iLevel;
        
        setupImageVector();
    }

    /**
     * creates vectors for all of the image sets
     */
    public void setupImageVector() {

        vectImages = new Vector();

        vectImages.add(new Vector()); //idle images
        vectImages.add(new Vector()); //attack images
        vectImages.add(new Vector()); //magic images
        vectImages.add(new Vector()); //death images
    }

    public void setMaxHP(int i) { iMaxHP = i; }
    public void setHP(int i) { iHP = i; }
    public void setMaxMP(int i) { iMaxMP = i; }
    public void setMP(int i) { iMP = i; }
    public void setLevel(int i) { iLevel = i; }
    public void setName(String str) { strName = str; }
    public void setMoney(int i) { iMoney = i; }
    public void setExperience(int i) { iExperience = i; }
    public void addImage(ImageIcon img, int i) { ((Vector) vectImages.elementAt(i)).add(img); }

    public int getMaxHP() { return iMaxHP; }
    public int getHP() { return iHP; }
    public int getMaxMP() { return iMaxMP; }
    public int getMP() { return iMP; }
    public int getLevel() { return iLevel; }
    public int getMoney() { return iMoney; }
    public int getExperience() { return iExperience; }
    public String getName() { return strName; }
    public Vector getSprites(int i) { return (Vector) vectImages.elementAt(i); }
    public Vector getAllSprites() {
        Vector vectToReturn;

        vectToReturn = new Vector();

        int i, j;
        for (i = 0; i < vectImages.size(); i++) {
            for (j = 0; j < ((Vector) vectImages.elementAt(i)).size(); j++) {
                vectToReturn.add( ((Vector) vectImages.elementAt(i)).elementAt(j));
            }
        }
        return vectToReturn;
    }


    /**
     * returns the name of the image set number passed in
     * @param i - the image set number
     * @return the image set name
     */
    public static String getImageSetName(int i) {
        String strToReturn;

        switch(i) {
        case IDLE_IMAGES:
            strToReturn = "Idle";
            break;
        case ATTACK_IMAGES:
            strToReturn = "Attack";
            break;
        case MAGIC_IMAGES:
            strToReturn = "Magic";
            break;
        case DEATH_IMAGES:
            strToReturn = "Death";
            break;
        default:
            strToReturn = "";
        }

        return strToReturn;
    }

    /**
     * a String representing the object
     */
    public String toString() {
        return (strName);
    }
    
}
