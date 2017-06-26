import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class Graph extends Canvas {

//Pie Chart Constants
public static final int OFFSET_X = 150;
public static final int OFFSET_Y = 50;
public static final int GR_WIDTH = 250;
public static final int GR_HEIGHT = 250;
public static final int WORD_EXTENT = 75; //how far out from the center the word should be placed
                                          //should be less than half the value of GR_WIDTH/GR_HEIGHT

//Bar and Line Graph Constants
public static final int BASELINE = (250 + OFFSET_Y);
public static final int BAR_OFFSET_R = 50;
public static final int BAR_SPAN = 500;
public static final int INCREMENT = 50;
public static final int NUM_CHARS_DISP = 7;//number of characters to display
public static final int SPACE_CONSTANT = 8;

public static final int DOT_SIZE = 2;

//General Constants
public static final String FILENAME = "graph.dat";
public static final boolean WEB = true; //is this an applet or application??
                                         //if true, then you use the GraphData class
                                         //if false, you can use a text file to read in data
                                         //when in doubt, the GraphData class will work both ways
public static final int MAX_COLORS = 16;

//this is to simulate an enum
private static final String PIE_CHART = "Pie Chart";
private static final String BAR_GRAPH = "Bar Graph";
private static final String LINE_GRAPH = "Line Graph";

private Vector dataVector = new Vector();
private float flMaxValue = 0;
private String GraphTitle = "";
private String Mode = PIE_CHART;

private Color[] ColorArray = new Color [MAX_COLORS];

private boolean legendIsOn = true;
private Font currentFont = new Font("Dialog", Font.BOLD, 12);


public Graph(String theMode) {
  setBackground(new Color(224, 224, 224) );
  Mode = theMode;
  setupColors();

/*
  //this is for testing, or IO if I decide to add that functionality
  if(WEB) {
    setVectorApplet();
  } else {
    setVectorApplication();
  }
*/

}



public void paint (Graphics g) {
  int i;
  int iPercent;
  int iAngle;
  int iOldAngle;
  double radCurrentAngle;
  int iWordPositionX;
  int iWordPositionY;
  GraphElement myElement;

  //Print the title of the graph
  g.setFont(new Font("Dialog", Font.BOLD, 16));
  g.setColor( new Color(1, 1, 1) );
  g.drawString(GraphTitle, 10, OFFSET_Y - 10);


 if(Mode.equals(PIE_CHART)) {
  drawPieChart(g);
 } else  if(Mode.equals(BAR_GRAPH)) {
  drawBarGraph(g);
 } else  if(Mode.equals(LINE_GRAPH)) {
  drawLineGraph(g);
 }

}



/*
 * drawPieChart - the follow lines of code draw a Pie Chart
 */
public void drawPieChart(Graphics g) {
  int i;
  int iPercent;
  int iAngle;
  int iOldAngle;
  double radCurrentAngle;
  int iWordPositionX;
  int iWordPositionY;
  GraphElement myElement;

  iOldAngle = 0;
  radCurrentAngle = 0;

  for(i = 0; i < dataVector.size(); i++) {
    myElement = (GraphElement) dataVector.elementAt(i);
    iPercent = (int) (myElement.getDataValue() / flMaxValue * 100);
    iAngle = iPercent * 360 / 100;


    g.setColor( getColor(i) );

    if(i == dataVector.size() - 1) {
      iAngle = 360 - iOldAngle; //clears up any round-off error
    }


    g.fillArc(OFFSET_X, OFFSET_Y, GR_HEIGHT, GR_WIDTH, iOldAngle, iAngle );
    g.setColor( new Color(1, 1, 1) ); //set color to black
    g.drawArc(OFFSET_X, OFFSET_Y, GR_HEIGHT, GR_WIDTH, iOldAngle, iAngle );

    iOldAngle += iAngle;
  }

  /* The names and data associated with them must be painted after the
   * graph slices have been painted, else the names will get painted
   * over
   */

  iOldAngle = 0;
  radCurrentAngle = 0;

  for(i = 0; i < dataVector.size(); i++) {
    myElement = (GraphElement) dataVector.elementAt(i);
    iPercent = (int) (myElement.getDataValue() / flMaxValue * 100);
    iAngle = iPercent * 360 / 100;

    iPercent = (int) (myElement.getDataValue() / flMaxValue * 100);


    g.setFont(new Font("Dialog", Font.BOLD, 12));
    g.setColor( new Color(1, 1, 1) );

    if(i == dataVector.size() - 1) {
      iAngle = 360 - iOldAngle; //clears up any round-off error
    }


    radCurrentAngle = ((iAngle + iOldAngle) + (iOldAngle)) / 2; //set the position of the word to be halfway between the old angle and the new angle
    radCurrentAngle = radCurrentAngle * 2 * Math.PI / 360;



    iWordPositionX = ((int) (Math.cos(radCurrentAngle) * WORD_EXTENT)) +  (GR_WIDTH / 2) + OFFSET_X;
    iWordPositionY = ((int) -(Math.sin(radCurrentAngle) * WORD_EXTENT)) + (GR_HEIGHT / 2) + OFFSET_Y;

    g.drawString(myElement.getName(), iWordPositionX, iWordPositionY);
    g.drawString((iPercent + "%"), iWordPositionX, (iWordPositionY + 12));

    iOldAngle += iAngle;

    }
}

/*
 * drawBarGraph - this draws a Bar Graph
 */
public void drawBarGraph(Graphics g) {
    int i;
    int iWordPositionX;
    int iWordPositionY;
    GraphElement myElement;
    int iInterval;
    int iSpacing;
    int iFirstBarPos;

    int iBarHeight;
    int iBarWidth;
    String printName;

    //draw the x and y axis
    g.setColor(new Color(1, 1, 1));
    g.drawLine(BAR_OFFSET_R, OFFSET_Y, BAR_OFFSET_R, BASELINE);
    g.drawLine(BAR_OFFSET_R, BASELINE, (BAR_SPAN + (BAR_SPAN / SPACE_CONSTANT)), BASELINE);

    iInterval = BAR_SPAN / (dataVector.size() );
    iSpacing = iInterval / SPACE_CONSTANT;
    iBarWidth = iInterval - iSpacing;

    iFirstBarPos = BAR_OFFSET_R + iSpacing;
    
    for(i = 0; i < dataVector.size(); i++) {
      myElement = (GraphElement) dataVector.elementAt(i);

      iBarHeight = ((int) myElement.getDataValue()) * BASELINE / ((int)flMaxValue);


      g.setColor( getColor(i) );
      g.fillRect(((i * iInterval) + iFirstBarPos), (BASELINE - iBarHeight),
          iBarWidth, iBarHeight);

      //draw the name
      g.setColor(Color.black);
      g.setFont(new Font("Dialog", Font.BOLD, 12));

      printName = myElement.getName();
      if (printName.length() > NUM_CHARS_DISP) {
        printName = printName.substring(0, NUM_CHARS_DISP);
      }

      g.drawString(printName, findWordPosition(iBarWidth, printName, ((i * iInterval) + iFirstBarPos)), (BASELINE + 20) );
    }
}

/*
 * Line Graph - guess what, this draws a line graph
 */
public void drawLineGraph(Graphics g) {
    int i;
    int iWordPositionX;
    int iWordPositionY;
    GraphElement myElement;


    int iOldLinePositionY;
    int iOldLinePositionX;
    int iLinePositionX;
    int iLinePositionY;
    String printName;

    iOldLinePositionX = 0;
    iOldLinePositionY = 0;
    iLinePositionX = INCREMENT;

    //draw the X and Y axis
    g.drawLine(BAR_OFFSET_R, OFFSET_Y, BAR_OFFSET_R, BASELINE);
    g.drawLine(BAR_OFFSET_R, BASELINE, BAR_SPAN, BASELINE);


    for(i = 0; i < dataVector.size(); i++) {
      myElement = (GraphElement) dataVector.elementAt(i);

      iLinePositionY = (int) (BASELINE - myElement.getDataValue());
      iLinePositionX += INCREMENT;

      g.setColor(new Color(1, 1, 1));
      g.fillOval(iLinePositionX, iLinePositionY,
          DOT_SIZE, DOT_SIZE);

      if (i > 0) {
      //draw a line connecting to the previous point
        g.drawLine(iLinePositionX, iLinePositionY,
            iOldLinePositionX, iOldLinePositionY);    
      }

      //draw the name
      g.setFont(new Font("Dialog", Font.BOLD, 12));
      printName = myElement.getName();
      if (printName.length() > NUM_CHARS_DISP) {
        printName = printName.substring(0, NUM_CHARS_DISP);
      }

      g.drawString(printName, iLinePositionX, (BASELINE + 20) );


      iOldLinePositionX = iLinePositionX;
      iOldLinePositionY = iLinePositionY;


    }


}

/*
 * findWordPosition - takes in a String, the bar width, and an
 *                    offset, then return the correct x-coordinate
 *                    where the string should be placed
 */
public int findWordPosition(int iBounds, String name, int iOffset) {
  int iWordWidth;
  iWordWidth = getFontMetrics(currentFont).stringWidth(name);

  return( (iBounds / 2) - (iWordWidth / 2) + iOffset);

}


public void setColors(Color[] newArray) {
  ColorArray = newArray;
  repaint();

}

public Color[] getColors() {
  return ColorArray;
}

public void setupColors() {
  ColorArray[0] = (new Color(255, 64, 64));  //off red
  ColorArray[1] = (new Color(255, 255, 64)); //off yellow
  ColorArray[2] = (new Color(128, 128, 255));//off blue 
  ColorArray[3] = (new Color(64, 255, 64));  //off green
  ColorArray[4] = (new Color(128, 255, 255));//off cyan
  ColorArray[5] = (new Color(255, 128, 255));//off magenta
  ColorArray[6] = (new Color(255, 128, 0));  //off orange
  ColorArray[7] = (new Color(192, 192, 192));//gray
  ColorArray[8] = (new Color(255, 192, 192));//bright red
  ColorArray[9] = (new Color(192, 80, 0));//dark yellow 
  ColorArray[10] = (new Color(192, 192, 255));//bright blue
  ColorArray[11] = (new Color(192, 255, 192));//bright green
  ColorArray[12] = (new Color(192, 255, 255));//bright cyan
  ColorArray[13] = (new Color(255, 192, 255));//bright magenta
  ColorArray[14] = (new Color(255, 192, 64));//bright orange
  ColorArray[15] = (new Color(255, 255, 255));//white

}



public Color getColor(int i) {
  i %= 16;
/*
  switch (i) {
    case 0:
      return (new Color(255, 64, 64));
    case 1:
      return (new Color(255, 255, 64));
    case 2:
      return (new Color(128, 128, 255));
    case 3:
      return (new Color(64, 255, 64));
    case 4:
      return (new Color(128, 255, 255));
    case 5:
      return (new Color(255, 128, 255));
    case 6:
      return (new Color(255, 128, 192));
    case 7:
      return (new Color(255, 255, 192));
    case 8:
      return (new Color(192, 192, 255));
    case 9:
      return (new Color(192, 255, 192));
    case 10:
      return (new Color(192, 255, 255));
    case 11:
      return (new Color(255, 192, 255));



    default:
      return (new Color(192, 192, 192));
*/
  if (i < 16) {
     return (ColorArray[i]);
  } else {
      return (new Color(192, 192, 192));
  }


}

/*
 * setVectorApplication - for applications
 */
public void setVectorApplication() {
  String nextline;

  try {
    FileReader fr = new FileReader (FILENAME);
    BufferedReader br = new BufferedReader (fr);             

    GraphTitle = br.readLine();
    nextline = br.readLine();

    while (nextline != null && !nextline.equals ("")) {
      parseInput (nextline);
      nextline = br.readLine ();
    }                          
    br.close ();
    fr.close ();


  } catch (IOException e) {
     System.out.println ("Error while reading from " + FILENAME
       + ":");
     System.out.println (e);
     System.exit (-1);
  }

  findMax();


}


/*
 * setVectorApplet - for web browsers
 */
public void setVectorApplet() {
  String nextline;
  GraphData myGraphData = new GraphData();
  int i;

  i = 0;
  GraphTitle = myGraphData.getLine(i);
  i++; //to stay consistant with the way we're reading in data
  nextline = myGraphData.getLine(i);

  while (nextline != null) {
    parseInput(nextline);
    i++;
    nextline = myGraphData.getLine(i);
  }

  findMax();

}


private void parseInput (String nextline) {
  Float myFloatValue = new Float( 0 );

  StringTokenizer st = new StringTokenizer (nextline, "|");
  try {
    GraphElement myGraphElement = new GraphElement();
    myGraphElement.setName( st.nextToken() );  

    try {
      myFloatValue = new Float(st.nextToken());
    } catch (NumberFormatException e) {
      System.out.println("A non-float String");
    }


    myGraphElement.setDataValue( myFloatValue.floatValue() );


/*    System.out.println("Adding: " + myGraphElement);
    System.out.println("Max Value: " + flMaxValue);
*/

    dataVector.addElement(myGraphElement);


  } catch (NoSuchElementException e) {
            System.out.println ("Invalid input format");
            System.exit (-1);
  }
}


public void setMode(String newMode) {
  Mode = newMode;
  repaint();

}

public String getMode() {
  return(Mode);
}

public String getGraphTitle() {
  return(GraphTitle);
}

public void setGraphTitle(String newTitle) {
  GraphTitle = newTitle;
  repaint();
}

public Vector getData() {
  Vector clone = new Vector();
  clone = (Vector) dataVector.clone();
  return (clone);
}

private void findMax() {
  float flValue = (float) 0;
  flMaxValue = 0;
  int i;

  for (i = 0; i < dataVector.size(); i++) {

    flValue = ((GraphElement) dataVector.elementAt(i)).getDataValue();
    flMaxValue += flValue;
  }

}

public void setData(Vector dataUpdate) {
  dataVector = dataUpdate;
  System.out.println(dataVector);
  findMax();
  repaint();
}

}
