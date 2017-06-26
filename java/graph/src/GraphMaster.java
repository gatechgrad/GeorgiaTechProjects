import java.awt.*;
import java.awt.event.*;
import java.net.*;


class GraphMaster extends Frame implements WindowListener, ActionListener {

public static final int WINDOWHEIGHT = 420;
public static final int WINDOWWIDTH = 640;
public static final int OFFSET = 100;
public static final String THIS_DIRECTORY = "/graph/";
public static final String VERSION = "1.02";
public static final String ICON_NAME = "GMIcon.gif";
public static final boolean WEB = true;

public static final String THIS_URL = "http://www.prism.gatech.edu/~gte187k/graph/";

//this is to simulate an enum
public static final String PIE_CHART = "Pie Chart";
public static final String BAR_GRAPH = "Bar Graph";
public static final String LINE_GRAPH = "Line Graph";
public static final String SAVE = "Save";
public static final String GENERATE = "Generate HTML and GIFs";
public static final String LOAD = "Load";
public static final String EXIT = "Exit";
public static final String COLORS = "Colors";
public static final String NAMES = "Names";
public static final String ABOUT = "About";
public static final String EDIT = "Create/Edit";
public static final String ARRANGE = "Arrangement";
public static final String PRINT = "Print";


//private Button butPieChart, butBarGraph, butLineGraph;

private MenuBar myMenuBar;
private Menu fileMenu, grMenu, optMenu, aboutMenu, createMenu;
private MenuItem
  MISave, MILoad, MIExit, MIPieChart, MIBarGraph,
  MILineGraph, MIColors, MINames, MIAbout, MIEdit,
  MIGenerate, MIArrange;


private Graph myGraph;


public GraphMaster() {
  Panel panel2;

  if (WEB) {
    try {
      URL ThisURL = new URL(THIS_URL + ICON_NAME);
      setIconImage(Toolkit.getDefaultToolkit().getImage(ThisURL));
    } catch (MalformedURLException e) {
//      System.out.println("Error");
    }

  } else {
    setIconImage(Toolkit.getDefaultToolkit().getImage(THIS_DIRECTORY + ICON_NAME));
  }

  makeMenu();

  //Window Setup
  setTitle("GraphMaster v1.01");
  setBounds(OFFSET, OFFSET, WINDOWWIDTH, WINDOWHEIGHT);
  setResizable(false);

  setBackground(new Color(192, 192, 192));


  //Add pieces to Window
  myGraph = new Graph(PIE_CHART);
  myGraph.setSize(640, 400);



  setLayout(new BorderLayout());
  add(myGraph, BorderLayout.CENTER);
//  add(panel2, BorderLayout.SOUTH);

  setMenuBar(myMenuBar);

  setVisible(true);
  addWindowListener(this);

}

public void actionPerformed(ActionEvent e) {
  String theAction;
  theAction = e.getActionCommand();

  if (theAction.equals(PIE_CHART) && !myGraph.getMode().equals(PIE_CHART)) {
    myGraph.setMode(PIE_CHART);
  } else if (theAction.equals(BAR_GRAPH) && !myGraph.getMode().equals(BAR_GRAPH)) {
    myGraph.setMode(BAR_GRAPH);
  } else if (theAction.equals(LINE_GRAPH) && !myGraph.getMode().equals(LINE_GRAPH)) {
    myGraph.setMode(LINE_GRAPH);
  } else {
    //The user has clicked on the button of the current graph
    //do nothing (reduce flicker!!)
  }



  if (theAction.equals(EXIT) ) {
    shutdown();
  } else  if (theAction.equals(EDIT) ) {
    InputBox myAddDialog = new InputBox(myGraph, this);
    myAddDialog.setGraph(myGraph);
    myAddDialog.setVisible(true);
  } else  if (theAction.equals(ABOUT) ) {
    WarningDialog myWD = new WarningDialog("Graph Master v1.02 by Levi D. Smith", this);
    myWD.setVisible(true);
  } else if (theAction.equals(COLORS) ) {
    ColorDialog myCD = new ColorDialog(myGraph, this);

  }


}


public void makeMenu() {
  myMenuBar = new MenuBar();

  fileMenu = new Menu("File", false);
    MISave = new MenuItem(SAVE, new MenuShortcut(83) );
    MILoad = new MenuItem(LOAD);
    MIExit = new MenuItem(EXIT, new MenuShortcut(KeyEvent.VK_X));
    MIGenerate = new MenuItem(GENERATE);
    MISave.addActionListener(this);
    MILoad.addActionListener(this);
    MIExit.addActionListener(this);
    MIGenerate.addActionListener(this);
    fileMenu.add(MISave);
    fileMenu.add(MILoad);
    fileMenu.add(MIGenerate);
    fileMenu.add(MIExit);

  if (WEB) {
    MISave.setEnabled(false);
    MILoad.setEnabled(false);
    MIGenerate.setEnabled(false);
  }

  grMenu = new Menu("Graph", false);
    MIEdit = new MenuItem(EDIT);
    MIPieChart = new MenuItem(PIE_CHART);
    MIBarGraph = new MenuItem(BAR_GRAPH);
    MILineGraph = new MenuItem(LINE_GRAPH);
    MIEdit.addActionListener(this);
    MIPieChart.addActionListener(this);
    MIBarGraph.addActionListener(this);
    MILineGraph.addActionListener(this);
    grMenu.add(MIEdit);
    grMenu.addSeparator();
    grMenu.add(MIPieChart);
    grMenu.add(MIBarGraph);
    grMenu.add(MILineGraph);
  optMenu = new Menu("Options", false);
    MIColors = new MenuItem(COLORS);
    MINames = new MenuItem(NAMES);
    MIArrange = new MenuItem(ARRANGE);
    MIColors.addActionListener(this);
    MINames.addActionListener(this);
    MIArrange.addActionListener(this);
    optMenu.add(MIColors);
    optMenu.add(MINames);
    optMenu.add(MIArrange);
  aboutMenu = new Menu("About", false);
    MIAbout = new MenuItem(ABOUT);
    MIAbout.addActionListener(this);
    aboutMenu.add(MIAbout);

  comingSoon();


  myMenuBar.add(fileMenu);
  myMenuBar.add(grMenu);
  myMenuBar.add(optMenu);
  myMenuBar.setHelpMenu(aboutMenu);


}

public void comingSoon() {
  MINames.setEnabled(false);
  MIArrange.setEnabled(false);

}

public String getDir() {
  return (THIS_DIRECTORY);
}

public Image getIcon() {
  return (getIconImage());
}

public Image getMyImage(String imageName) {
  Image ImageToReturn = null;

  if (WEB) {
    try {
      URL ThisURL = new URL(THIS_URL + imageName);
      ImageToReturn = Toolkit.getDefaultToolkit().getImage(ThisURL);
    } catch (MalformedURLException e) {

    }

  } else {
    ImageToReturn = Toolkit.getDefaultToolkit().getImage(THIS_DIRECTORY + imageName);
  }
    return ImageToReturn;

}

public static void main (String argv[]) {
  GraphMaster myGraphMaster = new GraphMaster();
}


public void windowClosing (WindowEvent e) {
  shutdown();
}

public void shutdown() {
  setVisible(false);
  dispose();

  try {
    System.exit(0);
  } catch (Exception e) {
    //if this is an applet, then an exception might be thrown (applets
    //probably don't know how to "exit")
    //we should just accept it to prevent the crash
  }

}

public void windowDeactivated (WindowEvent e) {
}
public void windowClosed(WindowEvent e) {
}
public void windowActivated (WindowEvent e) {
}
public void windowDeiconified (WindowEvent e) {
}
public void windowIconified (WindowEvent e) {
}
public void windowOpened (WindowEvent e) {
}


}
