import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.util.Vector;


/**
 *  MonsterViewer - displays all of the monsters created by the user
 *  @author Levi D. Smith
 *  @version 0.1
 */
public class MonsterViewer extends Viewer {

    //CONSTANTS
    public static final String WINDOW_TITLE = "Monster Viewer";

    //INSTANCE VARIABLES (COMPONENTS)
    private JLabel lblMonsterName;
    private JLabel lblMonsterMaxHP;
    private JLabel lblMonsterMaxMP;
    private JLabel lblMonsterLevel;
    private JLabel lblMonsterMoney;
    private JLabel lblMonsterExperience;

    private JPanel pnlImages;


    //INSTANCE VARIABLES (DATA)
    private Vector vectMonsters;
    
    public MonsterViewer(Vector vectMonsters, RPGGenerator theRPGG) {
        super(theRPGG);
        this.vectMonsters = vectMonsters;
        setupWindow();
        
    }

    /**
     * MonsterViewer specific window commands
     */
    private void setupWindow() {
        
        listItems.setListData(vectMonsters);
        
        pnlImages = new JPanel();
        pnlImages.setLayout(new BoxLayout(pnlImages, BoxLayout.X_AXIS));
        pnlImages.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTableau.add(pnlImages);

        JPanel pnlTemp;
        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Name: "));
        lblMonsterName = new JLabel("");
        pnlTemp.add(lblMonsterName);
        pnlTableau.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("HP: "));
        lblMonsterMaxHP = new JLabel("");
        pnlTemp.add(lblMonsterMaxHP);
        pnlTableau.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("MP: "));
        lblMonsterMaxMP = new JLabel("");
        pnlTemp.add(lblMonsterMaxMP);
        pnlTableau.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Level: "));
        lblMonsterLevel = new JLabel("");
        pnlTemp.add(lblMonsterLevel);
        pnlTableau.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Money: "));
        lblMonsterMoney = new JLabel("");
        pnlTemp.add(lblMonsterMoney);
        pnlTableau.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Experience: "));
        lblMonsterExperience = new JLabel("");
        pnlTemp.add(lblMonsterExperience);
        pnlTableau.add(pnlTemp);

        this.setTitle(WINDOW_TITLE);
        show();
    }

    /**
     * finds the selected monster
     */
    private Monster findSelectedMonster() {
        Monster monsterToReturn;
        
        monsterToReturn = null;

        if (this.selectionIsInBounds(vectMonsters.size())) {
            monsterToReturn = (Monster) vectMonsters.elementAt(this.getListCurrentSelectedIndex());
        }

        return monsterToReturn;
    }

    
    /**
     * frame containing specific monster data
     */
    protected void updateTableauPanel() {
        int i;
        Monster monsterCurrent;
         
        monsterCurrent = findSelectedMonster();
        
        

        if (monsterCurrent != null) {
            lblMonsterName.setText(monsterCurrent.getName());
            lblMonsterMaxHP.setText(String.valueOf(monsterCurrent.getMaxHP()));
            lblMonsterMaxMP.setText(String.valueOf(monsterCurrent.getMaxMP()));
            lblMonsterLevel.setText(String.valueOf(monsterCurrent.getLevel()));
            lblMonsterMoney.setText(String.valueOf(monsterCurrent.getMoney()));
            lblMonsterExperience.setText(String.valueOf(monsterCurrent.getExperience()));

            Vector vectSprites = monsterCurrent.getAllSprites();
            pnlImages.removeAll();
            for (i = 0; i < vectSprites.size(); i++) {
                pnlImages.add(new JLabel(((ImageIcon) vectSprites.elementAt(i))));

            }
        }        
    }

    /**
     * to be executed when new button is pressed
     */
    protected void newButtonPressed() {
        Monster monsterNew = new Monster("New Monster", 0, 0, 0);
        vectMonsters.addElement(monsterNew);
        listItems.setListData(vectMonsters);
        this.repaint();
    }

    /**
     * to be executed when delete button is pressed
     */
    protected void deleteButtonPressed() {
        if (this.selectionIsInBounds(vectMonsters.size())) {
                vectMonsters.removeElementAt(getListCurrentSelectedIndex());
                this.repaint();
        }
    }

    /**
     * to be executed when edit button is pressed
     */
    protected void editButtonPressed() {
        Monster monsterCurrent;

        monsterCurrent = findSelectedMonster();

        if (monsterCurrent != null) {
            theRPGG.addInternalFrame(new MonsterEditor(this, monsterCurrent));
        } else {
            JOptionPane.showMessageDialog(theRPGG.getFrame(), "Please Choose A Monster First", "Monster Not Chosen", JOptionPane.WARNING_MESSAGE);
        }

    }

    


}
