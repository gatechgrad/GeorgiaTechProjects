import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import java.io.*;

public class RPGGenerator implements ActionListener{
    //CONSTANTS
    public static final Dimension DISPLAY_SIZE = new Dimension(640, 480);
    public static final Dimension NEW_WINDOW_INCREMENT = new Dimension(32, 16);
    public static final Point INITIAL_WINDOW_POSITION = new Point(50, 50);

    public static final String WINDOW_TITLE = "RPG Generator";

    public static final String CREATE = "Manage";
    public static final String CREATE_MONSTER = "Monsters";
    public static final String CREATE_TITLE_SCREENS = "Title Screens";
    public static final String CREATE_MAPS = "Maps";
    public static final String CREATE_WEAPONS = "Weapons";
    public static final String CREATE_MAGIC = "Magic";
    public static final String CREATE_TEXTURES = "Textures";

    public static final String FILE = "File";
    public static final String FILE_SAVE = "Save";
    public static final String FILE_LOAD = "Load";
    public static final String FILE_EXIT = "Exit";

    //INSTANCE VARIABLES (components)
    private JFrame theFrame;
    private JDesktopPane theDesktopPane;
    
    //INSTANCE VARIABLES (data)
    private GameData theGameData;
    private Point pntNextWindowPosition;

    /**
     * constructor
     */
    public RPGGenerator() {
        theGameData = new GameData();

        setupWindow();

    }

    /**
     * creates the main window of the program
     */
    private void setupWindow() {
        theFrame = new JFrame();
        theDesktopPane = new JDesktopPane();
        theDesktopPane.setPreferredSize(DISPLAY_SIZE);
        theFrame.getContentPane().add(theDesktopPane);

        pntNextWindowPosition = new Point(INITIAL_WINDOW_POSITION);
        


        /*
        theInternalFrame = new JInternalFrame("Hello World", true, true, true, true);

        theDesktopPane.add(theInternalFrame);

        theInternalFrame.setSize(128, 256);
        theInternalFrame.setLocation(50, 50);
        theInternalFrame.getContentPane().add(new JLabel("Hello World"));
        theInternalFrame.show();
        */

        WindowListener theWindowListener = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        theFrame.addWindowListener(theWindowListener);
        
        /*
        JPanel pnlMain = new JPanel();
        pnlMain.setPreferredSize(DISPLAY_SIZE);
        
        theFrame.getContentPane().add(pnlMain);
        */
        theFrame.setJMenuBar(createMenuBar());

        theFrame.setTitle(WINDOW_TITLE);

        theFrame.pack();
        theFrame.setLocation(RPGGeneratorConstants.getCenteredWindowLocation(theFrame));
        theFrame.show();


    }

    /**
     * makes the menubar for the application
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBarToReturn;

        menuBarToReturn = new JMenuBar();


        JMenu menuFile = new JMenu(FILE);
        addMenuBarItem(FILE_SAVE, menuFile);
        addMenuBarItem(FILE_LOAD, menuFile);
        addMenuBarItem(FILE_EXIT, menuFile);
                    
        JMenu menuCreate = new JMenu(CREATE);
        addMenuBarItem(CREATE_MONSTER, menuCreate);
        addMenuBarItem(CREATE_TITLE_SCREENS, menuCreate);
        addMenuBarItem(CREATE_MAPS, menuCreate);
        addMenuBarItem(CREATE_WEAPONS, menuCreate);
        addMenuBarItem(CREATE_TEXTURES, menuCreate);
                 

        menuBarToReturn.add(menuFile);
        menuBarToReturn.add(menuCreate);


        return menuBarToReturn;


    }

    /**
     * adds a new choice to the menubar
     */
    private void addMenuBarItem(String strLabel, JMenu menuOwner) {
        JMenuItem miNewMenuItem = new JMenuItem(strLabel);
        miNewMenuItem.setActionCommand(strLabel);
        miNewMenuItem.addActionListener(this);
        menuOwner.add(miNewMenuItem);
    }

    /**
     * adds a new internal frame to the desktop
     */
    public void addInternalFrame(JInternalFrame theInternalFrame) {
        theInternalFrame.setLocation(pntNextWindowPosition);
        pntNextWindowPosition.translate(NEW_WINDOW_INCREMENT.width, NEW_WINDOW_INCREMENT.height);
        theDesktopPane.add(theInternalFrame);
    }

    /**
     * gets the main frame
     */
    public JFrame getFrame() {
        return theFrame;
    }

    /**
     * required by ActionListener interface
     */
    public void actionPerformed(ActionEvent e) {
        String strCommand = e.getActionCommand();

        if (strCommand.equals(CREATE_MONSTER)) {
            addInternalFrame(new MonsterViewer(theGameData.getMonsters(), this));
        } else if (strCommand.equals(CREATE_MAPS)) {
            addInternalFrame(new MapViewer(theGameData.getMaps(), theGameData.getTextures(), this));
        } else if (strCommand.equals(CREATE_TEXTURES)) {
            addInternalFrame(new TextureViewer(theGameData.getTextures(), this));
        } else if (strCommand.equals(FILE_SAVE)) {
            save();
        } else if (strCommand.equals(FILE_LOAD)) {
            load();
        } else if (strCommand.equals(FILE_EXIT)) {
            theFrame.setVisible(false);
            theFrame.dispose();
            System.exit(0);
        }

    }

    /**
     * saves the game created
     */
    public void save() {
        JFileChooser jfc;
        File fileSave;
        String strOutput;

        jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File("."));
        jfc.showSaveDialog(theFrame);
        fileSave = jfc.getSelectedFile();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileSave));
            oos.writeObject(theGameData);
            oos.close();
        } catch (IOException e) {
        }
    }

    /**
     * loads the created game
     */
    public void load() {

        JFileChooser jfc;
        File fileLoad;

        jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File("."));
        jfc.showOpenDialog(theFrame);
        fileLoad = jfc.getSelectedFile();

        try {
          theFrame.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
          ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLoad));
          theGameData = (GameData) ois.readObject();
          ois.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(theFrame, "IO error while reading: " + fileLoad.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) { 
            JOptionPane.showMessageDialog(theFrame, "Class error while reading: " + fileLoad.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        theFrame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        
    }


    /**
     * called when program is started as an application
     */
    public static void main(String args[]) {
      new RPGGenerator();
    }
}
