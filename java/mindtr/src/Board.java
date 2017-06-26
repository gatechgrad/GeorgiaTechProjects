import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Board extends Canvas implements MouseListener, MouseMotionListener {
  /************************ CONSTANTS *******************************/
  // Gameboard Constants for drawing
  public static final int OUTER_RING_RADIUS = 470; //the radius of the upper
                                                   //bound of the ring
  public static final int INNER_RING_RADIUS = 300; //the radius of the lower
                                                   //bound of the ring
  public static final int INIT_ANGLE = 55; // the angle position of the first
                                           // space on the ring
  public static final int DEGREES_PER_SPACE = 10; //the number of degrees
                                                  //for a space on the ring
  public static final Rectangle SPOKE_SPACE = new Rectangle(20, 20);
                                          //the size of a rectangular space

  // Color Constants
  public static final Color BACKGROUND_COLOR = new Color(0, 214, 214);
  public static final Color BLACK = new Color(0, 0, 0);

  public static final int NUM_SPACES = 73;

  /************************ CLASS VARIABLES *************************/


  //for drawing purposes
  private Image ImgBackground;
  private Image ImgBoard;

  private MediaTracker theMediaTracker;

  //variables for double buffering
  private boolean damage = true;
  private Image  bufferImage = null;
  private Graphics bufferGraphics = null;


  //non-drawing variables
  private Space theSpaces[] = new Space[NUM_SPACES];
  Mindtrial MTMainGame;
  private boolean isClickable;

  //data generation variables
  FileWriter outputFile;
  public PrintWriter printThis;
  int iTimes;
  int iCell;



  /**
    * Board - constructor
    **/
  public Board(Mindtrial MTMainGame) {

    this.MTMainGame = MTMainGame;
    isClickable = false;
  //take this out later
  //try {
    //outputFile = new FileWriter("output.dat");
  //} catch (Exception e) {}

  printThis = new PrintWriter(new BufferedWriter(outputFile));

    iTimes = 0;
    iCell = 0;

    initialize();
  }

  /**
    * paint - is called when the canvas needs to be repainted
    *       - required by Canvas
    **/
  public void paint(Graphics g) {
    updateDisplay(g);

  }


  /**
    * initalize - creates and displays the board
    *           - creates Space objects and adds Players
    *           - Paints the Board to the screen, creates space objects in
    *               set locations with their colors and whether they are
    *               wedge spaces or not
    *           - adds each player to the center space via Space's addPlayer
    *               procedure
    **/
  private void initialize() {
    this.setBackground(BACKGROUND_COLOR);
    this.setSize(480, 480);

    int[] iAdjCells = new int[6];
    int[] iXCoords = new int[4];
    int[] iYCoords = new int[4];

    //set the spaces


    iXCoords[0] = 242;
    iYCoords[0] = 10;
    iXCoords[1] = 242;
    iYCoords[1] = 49;
    iXCoords[2] = 265;
    iYCoords[2] = 51;
    iXCoords[3] = 270;
    iYCoords[3] = 11;
    iAdjCells[0] = 1;
    iAdjCells[1] = 41;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[0] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    0,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 273;
    iYCoords[0] = 13;
    iXCoords[1] = 268;
    iYCoords[1] = 51;
    iXCoords[2] = 288;
    iYCoords[2] = 55;
    iXCoords[3] = 303;
    iYCoords[3] = 19;
    iAdjCells[0] = 0;
    iAdjCells[1] = 1;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[1] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    1,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 306;
    iYCoords[0] = 20;
    iXCoords[1] = 292;
    iYCoords[1] = 55;
    iXCoords[2] = 314;
    iYCoords[2] = 65;
    iXCoords[3] = 334;
    iYCoords[3] = 31;
    iAdjCells[0] = 1;
    iAdjCells[1] = 3;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[2] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    2,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 338;
    iYCoords[0] = 32;
    iXCoords[1] = 317;
    iYCoords[1] = 66;
    iXCoords[2] = 345;
    iYCoords[2] = 83;
    iXCoords[3] = 365;
    iYCoords[3] = 47;
    iAdjCells[0] = 2;
    iAdjCells[1] = 42;
    iAdjCells[2] = 4;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[3] = new Space(1,
    Space.CATEGORY_GREEN,
    iAdjCells,
    3,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 369;
    iYCoords[0] = 51;
    iXCoords[1] = 349;
    iYCoords[1] = 84;
    iXCoords[2] = 367;
    iYCoords[2] = 102;
    iXCoords[3] = 392;
    iYCoords[3] = 70;
    iAdjCells[0] = 3;
    iAdjCells[1] = 5;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[4] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    4,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 396;
    iYCoords[0] = 73;
    iXCoords[1] = 370;
    iYCoords[1] = 103;
    iXCoords[2] = 384;
    iYCoords[2] = 120;
    iXCoords[3] = 414;
    iYCoords[3] = 95;
    iAdjCells[0] = 4;
    iAdjCells[1] = 6;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[5] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    5,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 418;
    iYCoords[0] = 99;
    iXCoords[1] = 386;
    iYCoords[1] = 123;
    iXCoords[2] = 396;
    iYCoords[2] = 138;
    iXCoords[3] = 431;
    iYCoords[3] = 119;
    iAdjCells[0] = 5;
    iAdjCells[1] = 7;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[6] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    6,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 434;
    iYCoords[0] = 122;
    iXCoords[1] = 398;
    iYCoords[1] = 142;
    iXCoords[2] = 408;
    iYCoords[2] = 162;
    iXCoords[3] = 444;
    iYCoords[3] = 146;
    iAdjCells[0] = 6;
    iAdjCells[1] = 8;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[7] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    7,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 447;
    iYCoords[0] = 151;
    iXCoords[1] = 409;
    iYCoords[1] = 166;
    iXCoords[2] = 416;
    iYCoords[2] = 184;
    iXCoords[3] = 455;
    iYCoords[3] = 181;
    iAdjCells[0] = 7;
    iAdjCells[1] = 9;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[8] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    8,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 455;
    iYCoords[0] = 183;
    iXCoords[1] = 417;
    iYCoords[1] = 189;
    iXCoords[2] = 420;
    iYCoords[2] = 210;
    iXCoords[3] = 459;
    iYCoords[3] = 211;
    iAdjCells[0] = 8;
    iAdjCells[1] = 10;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[9] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    9,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 461;
    iYCoords[0] = 214;
    iXCoords[1] = 419;
    iYCoords[1] = 215;
    iXCoords[2] = 419;
    iYCoords[2] = 244;
    iXCoords[3] = 461;
    iYCoords[3] = 243;
    iAdjCells[0] = 9;
    iAdjCells[1] = 47;
    iAdjCells[2] = 11;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[10] = new Space(2,
    Space.CATEGORY_BROWN,
    iAdjCells,
    10,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 461;
    iYCoords[0] = 248;
    iXCoords[1] = 420;
    iYCoords[1] = 248;
    iXCoords[2] = 416;
    iYCoords[2] = 273;
    iXCoords[3] = 456;
    iYCoords[3] = 280;
    iAdjCells[0] = 10;
    iAdjCells[1] = 12;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[11] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    11,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 455;
    iYCoords[0] = 282;
    iXCoords[1] = 416;
    iYCoords[1] = 277;
    iXCoords[2] = 409;
    iYCoords[2] = 296;
    iXCoords[3] = 446;
    iYCoords[3] = 312;
    iAdjCells[0] = 11;
    iAdjCells[1] = 13;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[12] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    12,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 444;
    iYCoords[0] = 315;
    iXCoords[1] = 407;
    iYCoords[1] = 300;
    iXCoords[2] = 398;
    iYCoords[2] = 320;
    iXCoords[3] = 434;
    iYCoords[3] = 341;
    iAdjCells[0] = 12;
    iAdjCells[1] = 14;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[13] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    13,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 431;
    iYCoords[0] = 343;
    iXCoords[1] = 397;
    iYCoords[1] = 323;
    iXCoords[2] = 386;
    iYCoords[2] = 340;
    iXCoords[3] = 417;
    iYCoords[3] = 365;
    iAdjCells[0] = 13;
    iAdjCells[1] = 15;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[14] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    14,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 415;
    iYCoords[0] = 368;
    iXCoords[1] = 384;
    iYCoords[1] = 344;
    iXCoords[2] = 370;
    iYCoords[2] = 359;
    iXCoords[3] = 395;
    iYCoords[3] = 389;
    iAdjCells[0] = 14;
    iAdjCells[1] = 16;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[15] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    15,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 393;
    iYCoords[0] = 392;
    iXCoords[1] = 367;
    iYCoords[1] = 362;
    iXCoords[2] = 348;
    iYCoords[2] = 377;
    iXCoords[3] = 369;
    iYCoords[3] = 411;
    iAdjCells[0] = 15;
    iAdjCells[1] = 17;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[16] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    16,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 367;
    iYCoords[0] = 414;
    iXCoords[1] = 344;
    iYCoords[1] = 379;
    iXCoords[2] = 318;
    iYCoords[2] = 395;
    iXCoords[3] = 338;
    iYCoords[3] = 431;
    iAdjCells[0] = 16;
    iAdjCells[1] = 52;
    iAdjCells[2] = 18;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[17] = new Space(3,
    Space.CATEGORY_CYAN,
    iAdjCells,
    17,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 334;
    iYCoords[0] = 432;
    iXCoords[1] = 314;
    iYCoords[1] = 397;
    iXCoords[2] = 291;
    iYCoords[2] = 406;
    iXCoords[3] = 305;
    iYCoords[3] = 442;
    iAdjCells[0] = 17;
    iAdjCells[1] = 19;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[18] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    18,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 303;
    iYCoords[0] = 445;
    iXCoords[1] = 288;
    iYCoords[1] = 408;
    iXCoords[2] = 268;
    iYCoords[2] = 409;
    iXCoords[3] = 272;
    iYCoords[3] = 450;
    iAdjCells[0] = 18;
    iAdjCells[1] = 20;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[19] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    19,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 271;
    iYCoords[0] = 451;
    iXCoords[1] = 265;
    iYCoords[1] = 411;
    iXCoords[2] = 242;
    iYCoords[2] = 412;
    iXCoords[3] = 242;
    iYCoords[3] = 451;
    iAdjCells[0] = 19;
    iAdjCells[1] = 21;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[20] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    20,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 238;
    iYCoords[0] = 452;
    iXCoords[1] = 239;
    iYCoords[1] = 412;
    iXCoords[2] = 217;
    iYCoords[2] = 411;
    iXCoords[3] = 210;
    iYCoords[3] = 451;
    iAdjCells[0] = 20;
    iAdjCells[1] = 22;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[21] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    21,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 208;
    iYCoords[0] = 450;
    iXCoords[1] = 213;
    iYCoords[1] = 410;
    iXCoords[2] = 193;
    iYCoords[2] = 405;
    iXCoords[3] = 177;
    iYCoords[3] = 443;
    iAdjCells[0] = 21;
    iAdjCells[1] = 23;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[22] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    22,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 175;
    iYCoords[0] = 443;
    iXCoords[1] = 189;
    iYCoords[1] = 407;
    iXCoords[2] = 166;
    iYCoords[2] = 398;
    iXCoords[3] = 146;
    iYCoords[3] = 433;
    iAdjCells[0] = 22;
    iAdjCells[1] = 24;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[23] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    23,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 143;
    iYCoords[0] = 431;
    iXCoords[1] = 163;
    iYCoords[1] = 395;
    iXCoords[2] = 136;
    iYCoords[2] = 377;
    iXCoords[3] = 114;
    iYCoords[3] = 413;
    iAdjCells[0] = 23;
    iAdjCells[1] = 57;
    iAdjCells[2] = 25;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[24] = new Space(4,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    24,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 112;
    iYCoords[0] = 412;
    iXCoords[1] = 132;
    iYCoords[1] = 377;
    iXCoords[2] = 114;
    iYCoords[2] = 361;
    iXCoords[3] = 87;
    iYCoords[3] = 393;
    iAdjCells[0] = 24;
    iAdjCells[1] = 26;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[25] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    25,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 86;
    iYCoords[0] = 391;
    iXCoords[1] = 112;
    iYCoords[1] = 359;
    iXCoords[2] = 98;
    iYCoords[2] = 342;
    iXCoords[3] = 68;
    iYCoords[3] = 367;
    iAdjCells[0] = 25;
    iAdjCells[1] = 27;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[26] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    26,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 64;
    iYCoords[0] = 364;
    iXCoords[1] = 95;
    iYCoords[1] = 340;
    iXCoords[2] = 84;
    iYCoords[2] = 322;
    iXCoords[3] = 49;
    iYCoords[3] = 343;
    iAdjCells[0] = 26;
    iAdjCells[1] = 28;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[27] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    27,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 48;
    iYCoords[0] = 341;
    iXCoords[1] = 84;
    iYCoords[1] = 321;
    iXCoords[2] = 73;
    iYCoords[2] = 301;
    iXCoords[3] = 37;
    iYCoords[3] = 315;
    iAdjCells[0] = 27;
    iAdjCells[1] = 29;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[28] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    28,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 33;
    iYCoords[0] = 311;
    iXCoords[1] = 71;
    iYCoords[1] = 297;
    iXCoords[2] = 65;
    iYCoords[2] = 277;
    iXCoords[3] = 27;
    iYCoords[3] = 283;
    iAdjCells[0] = 28;
    iAdjCells[1] = 30;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[29] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    29,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 25;
    iYCoords[0] = 278;
    iXCoords[1] = 65;
    iYCoords[1] = 274;
    iXCoords[2] = 61;
    iYCoords[2] = 247;
    iXCoords[3] = 19;
    iYCoords[3] = 247;
    iAdjCells[0] = 29;
    iAdjCells[1] = 31;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[30] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    30,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 18;
    iYCoords[0] = 244;
    iXCoords[1] = 60;
    iYCoords[1] = 243;
    iXCoords[2] = 62;
    iYCoords[2] = 215;
    iXCoords[3] = 19;
    iYCoords[3] = 212;
    iAdjCells[0] = 30;
    iAdjCells[1] = 62;
    iAdjCells[2] = 32;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[31] = new Space(5,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    31,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 19;
    iYCoords[0] = 210;
    iXCoords[1] = 61;
    iYCoords[1] = 209;
    iXCoords[2] = 66;
    iYCoords[2] = 187;
    iXCoords[3] = 24;
    iYCoords[3] = 183;
    iAdjCells[0] = 31;
    iAdjCells[1] = 33;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[32] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    32,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 26;
    iYCoords[0] = 179;
    iXCoords[1] = 66;
    iYCoords[1] = 185;
    iXCoords[2] = 72;
    iYCoords[2] = 165;
    iXCoords[3] = 35;
    iYCoords[3] = 150;
    iAdjCells[0] = 32;
    iAdjCells[1] = 34;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[33] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    33,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 36;
    iYCoords[0] = 146;
    iXCoords[1] = 73;
    iYCoords[1] = 163;
    iXCoords[2] = 84;
    iYCoords[2] = 142;
    iXCoords[3] = 48;
    iYCoords[3] = 122;
    iAdjCells[0] = 33;
    iAdjCells[1] = 35;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[34] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    34,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 49;
    iYCoords[0] = 119;
    iXCoords[1] = 85;
    iYCoords[1] = 139;
    iXCoords[2] = 95;
    iYCoords[2] = 121;
    iXCoords[3] = 64;
    iYCoords[3] = 98;
    iAdjCells[0] = 34;
    iAdjCells[1] = 36;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[35] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    35,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 66;
    iYCoords[0] = 94;
    iXCoords[1] = 98;
    iYCoords[1] = 119;
    iXCoords[2] = 112;
    iYCoords[2] = 104;
    iXCoords[3] = 86;
    iYCoords[3] = 73;
    iAdjCells[0] = 35;
    iAdjCells[1] = 37;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[36] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    36,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 88;
    iYCoords[0] = 70;
    iXCoords[1] = 113;
    iYCoords[1] = 101;
    iXCoords[2] = 133;
    iYCoords[2] = 86;
    iXCoords[3] = 112;
    iYCoords[3] = 49;
    iAdjCells[0] = 36;
    iAdjCells[1] = 38;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[37] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    37,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 114;
    iYCoords[0] = 47;
    iXCoords[1] = 135;
    iYCoords[1] = 84;
    iXCoords[2] = 164;
    iYCoords[2] = 67;
    iXCoords[3] = 142;
    iYCoords[3] = 30;
    iAdjCells[0] = 37;
    iAdjCells[1] = 67;
    iAdjCells[2] = 39;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[38] = new Space(6,
    Space.CATEGORY_RED,
    iAdjCells,
    38,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 146;
    iYCoords[0] = 30;
    iXCoords[1] = 165;
    iYCoords[1] = 64;
    iXCoords[2] = 190;
    iYCoords[2] = 57;
    iXCoords[3] = 175;
    iYCoords[3] = 19;
    iAdjCells[0] = 38;
    iAdjCells[1] = 40;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[39] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    39,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 178;
    iYCoords[0] = 19;
    iXCoords[1] = 192;
    iYCoords[1] = 57;
    iXCoords[2] = 212;
    iYCoords[2] = 53;
    iXCoords[3] = 208;
    iYCoords[3] = 12;
    iAdjCells[0] = 39;
    iAdjCells[1] = 41;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[40] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    40,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 210;
    iYCoords[0] = 10;
    iXCoords[1] = 215;
    iYCoords[1] = 52;
    iXCoords[2] = 238;
    iYCoords[2] = 51;
    iXCoords[3] = 238;
    iYCoords[3] = 9;
    iAdjCells[0] = 40;
    iAdjCells[1] = 0;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[41] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    41,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 317;
    iYCoords[0] = 70;
    iXCoords[1] = 304;
    iYCoords[1] = 91;
    iXCoords[2] = 330;
    iYCoords[2] = 109;
    iXCoords[3] = 343;
    iYCoords[3] = 86;
    iAdjCells[0] = 3;
    iAdjCells[1] = 43;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[42] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    42,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 303;
    iYCoords[0] = 96;
    iXCoords[1] = 328;
    iYCoords[1] = 112;
    iXCoords[2] = 315;
    iYCoords[2] = 133;
    iXCoords[3] = 290;
    iYCoords[3] = 117;
    iAdjCells[0] = 42;
    iAdjCells[1] = 44;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[43] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    43,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 289;
    iYCoords[0] = 119;
    iXCoords[1] = 313;
    iYCoords[1] = 135;
    iXCoords[2] = 300;
    iYCoords[2] = 159;
    iXCoords[3] = 276;
    iYCoords[3] = 142;
    iAdjCells[0] = 43;
    iAdjCells[1] = 45;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[44] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    44,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 275;
    iYCoords[0] = 146;
    iXCoords[1] = 298;
    iYCoords[1] = 162;
    iXCoords[2] = 282;
    iYCoords[2] = 184;
    iXCoords[3] = 259;
    iYCoords[3] = 169;
    iAdjCells[0] = 44;
    iAdjCells[1] = 46;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[45] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    45,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 259;
    iYCoords[0] = 174;
    iXCoords[1] = 282;
    iYCoords[1] = 190;
    iXCoords[2] = 268;
    iYCoords[2] = 211;
    iXCoords[3] = 246;
    iYCoords[3] = 197;
    iAdjCells[0] = 72;
    iAdjCells[1] = 45;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[46] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    46,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 393;
    iYCoords[0] = 216;
    iXCoords[1] = 393;
    iYCoords[1] = 244;
    iXCoords[2] = 417;
    iYCoords[2] = 244;
    iXCoords[3] = 416;
    iYCoords[3] = 216;
    iAdjCells[0] = 10;
    iAdjCells[1] = 48;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[47] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    47,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 363;
    iYCoords[0] = 216;
    iXCoords[1] = 364;
    iYCoords[1] = 244;
    iXCoords[2] = 389;
    iYCoords[2] = 245;
    iXCoords[3] = 389;
    iYCoords[3] = 216;
    iAdjCells[0] = 47;
    iAdjCells[1] = 49;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[48] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    48,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 333;
    iYCoords[0] = 216;
    iXCoords[1] = 333;
    iYCoords[1] = 244;
    iXCoords[2] = 361;
    iYCoords[2] = 244;
    iXCoords[3] = 361;
    iYCoords[3] = 216;
    iAdjCells[0] = 48;
    iAdjCells[1] = 50;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[49] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    49,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 302;
    iYCoords[0] = 216;
    iXCoords[1] = 301;
    iYCoords[1] = 243;
    iXCoords[2] = 330;
    iYCoords[2] = 244;
    iXCoords[3] = 330;
    iYCoords[3] = 216;
    iAdjCells[0] = 49;
    iAdjCells[1] = 51;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[50] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    50,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 270;
    iYCoords[0] = 215;
    iXCoords[1] = 273;
    iYCoords[1] = 241;
    iXCoords[2] = 296;
    iYCoords[2] = 242;
    iXCoords[3] = 298;
    iYCoords[3] = 217;
    iAdjCells[0] = 50;
    iAdjCells[1] = 72;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[51] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    51,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 329;
    iYCoords[0] = 353;
    iXCoords[1] = 304;
    iYCoords[1] = 370;
    iXCoords[2] = 316;
    iYCoords[2] = 392;
    iXCoords[3] = 343;
    iYCoords[3] = 376;
    iAdjCells[0] = 17;
    iAdjCells[1] = 53;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[52] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    52,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 315;
    iYCoords[0] = 330;
    iXCoords[1] = 290;
    iYCoords[1] = 348;
    iXCoords[2] = 301;
    iYCoords[2] = 366;
    iXCoords[3] = 327;
    iYCoords[3] = 351;
    iAdjCells[0] = 52;
    iAdjCells[1] = 54;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[53] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    53,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 299;
    iYCoords[0] = 303;
    iXCoords[1] = 275;
    iYCoords[1] = 319;
    iXCoords[2] = 290;
    iYCoords[2] = 343;
    iXCoords[3] = 314;
    iYCoords[3] = 327;
    iAdjCells[0] = 53;
    iAdjCells[1] = 55;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[54] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    54,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 282;
    iYCoords[0] = 276;
    iXCoords[1] = 259;
    iYCoords[1] = 292;
    iXCoords[2] = 274;
    iYCoords[2] = 316;
    iXCoords[3] = 297;
    iYCoords[3] = 302;
    iAdjCells[0] = 54;
    iAdjCells[1] = 56;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[55] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    55,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 268;
    iYCoords[0] = 252;
    iXCoords[1] = 245;
    iYCoords[1] = 265;
    iXCoords[2] = 258;
    iYCoords[2] = 289;
    iXCoords[3] = 281;
    iYCoords[3] = 274;
    iAdjCells[0] = 55;
    iAdjCells[1] = 72;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[56] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    56,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 152;
    iYCoords[0] = 354;
    iXCoords[1] = 176;
    iYCoords[1] = 370;
    iXCoords[2] = 165;
    iYCoords[2] = 392;
    iXCoords[3] = 137;
    iYCoords[3] = 376;
    iAdjCells[0] = 24;
    iAdjCells[1] = 58;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[57] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    57,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 167;
    iYCoords[0] = 330;
    iXCoords[1] = 190;
    iYCoords[1] = 347;
    iXCoords[2] = 179;
    iYCoords[2] = 366;
    iXCoords[3] = 155;
    iYCoords[3] = 350;
    iAdjCells[0] = 24;
    iAdjCells[1] = 58;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[58] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    58,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 182;
    iYCoords[0] = 305;
    iXCoords[1] = 206;
    iYCoords[1] = 319;
    iXCoords[2] = 192;
    iYCoords[2] = 345;
    iXCoords[3] = 167;
    iYCoords[3] = 325;
    iAdjCells[0] = 58;
    iAdjCells[1] = 60;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[59] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    59,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 199;
    iYCoords[0] = 277;
    iXCoords[1] = 221;
    iYCoords[1] = 293;
    iXCoords[2] = 208;
    iYCoords[2] = 317;
    iXCoords[3] = 184;
    iYCoords[3] = 301;
    iAdjCells[0] = 59;
    iAdjCells[1] = 61;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[60] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    60,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 213;
    iYCoords[0] = 253;
    iXCoords[1] = 235;
    iYCoords[1] = 266;
    iXCoords[2] = 223;
    iYCoords[2] = 290;
    iXCoords[3] = 200;
    iYCoords[3] = 274;
    iAdjCells[0] = 60;
    iAdjCells[1] = 72;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[61] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    61,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 64;
    iYCoords[0] = 215;
    iXCoords[1] = 88;
    iYCoords[1] = 216;
    iXCoords[2] = 89;
    iYCoords[2] = 244;
    iXCoords[3] = 64;
    iYCoords[3] = 244;
    iAdjCells[0] = 31;
    iAdjCells[1] = 63;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[62] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    62,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 93;
    iYCoords[0] = 215;
    iXCoords[1] = 116;
    iYCoords[1] = 215;
    iXCoords[2] = 116;
    iYCoords[2] = 243;
    iXCoords[3] = 93;
    iYCoords[3] = 244;
    iAdjCells[0] = 62;
    iAdjCells[1] = 64;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[63] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    63,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 120;
    iYCoords[0] = 217;
    iXCoords[1] = 147;
    iYCoords[1] = 216;
    iXCoords[2] = 147;
    iYCoords[2] = 244;
    iXCoords[3] = 119;
    iYCoords[3] = 243;
    iAdjCells[0] = 63;
    iAdjCells[1] = 65;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[64] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    64,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 151;
    iYCoords[0] = 216;
    iXCoords[1] = 178;
    iYCoords[1] = 217;
    iXCoords[2] = 179;
    iYCoords[2] = 243;
    iXCoords[3] = 151;
    iYCoords[3] = 244;
    iAdjCells[0] = 64;
    iAdjCells[1] = 66;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[65] = new Space(0,
    Space.CATEGORY_RED,
    iAdjCells,
    65,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 182;
    iYCoords[0] = 216;
    iXCoords[1] = 209;
    iYCoords[1] = 217;
    iXCoords[2] = 209;
    iYCoords[2] = 245;
    iXCoords[3] = 183;
    iYCoords[3] = 244;
    iAdjCells[0] = 65;
    iAdjCells[1] = 72;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[66] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    66,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 137;
    iYCoords[0] = 85;
    iXCoords[1] = 164;
    iYCoords[1] = 70;
    iXCoords[2] = 178;
    iYCoords[2] = 91;
    iXCoords[3] = 152;
    iYCoords[3] = 110;
    iAdjCells[0] = 38;
    iAdjCells[1] = 68;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[67] = new Space(0,
    Space.CATEGORY_CYAN,
    iAdjCells,
    67,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 154;
    iYCoords[0] = 112;
    iXCoords[1] = 178;
    iYCoords[1] = 95;
    iXCoords[2] = 192;
    iYCoords[2] = 116;
    iXCoords[3] = 166;
    iYCoords[3] = 132;
    iAdjCells[0] = 67;
    iAdjCells[1] = 69;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[68] = new Space(0,
    Space.CATEGORY_ORANGE,
    iAdjCells,
    68,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 168;
    iYCoords[0] = 135;
    iXCoords[1] = 192;
    iYCoords[1] = 118;
    iXCoords[2] = 207;
    iYCoords[2] = 144;
    iXCoords[3] = 181;
    iYCoords[3] = 158;
    iAdjCells[0] = 68;
    iAdjCells[1] = 70;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[69] = new Space(0,
    Space.CATEGORY_BROWN,
    iAdjCells,
    69,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 184;
    iYCoords[0] = 162;
    iXCoords[1] = 207;
    iYCoords[1] = 146;
    iXCoords[2] = 221;
    iYCoords[2] = 170;
    iXCoords[3] = 199;
    iYCoords[3] = 186;
    iAdjCells[0] = 69;
    iAdjCells[1] = 71;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[70] = new Space(0,
    Space.CATEGORY_GREEN,
    iAdjCells,
    70,
    new Polygon(iXCoords, iYCoords, 4));


    iXCoords[0] = 200;
    iYCoords[0] = 189;
    iXCoords[1] = 223;
    iYCoords[1] = 172;
    iXCoords[2] = 236;
    iYCoords[2] = 196;
    iXCoords[3] = 213;
    iYCoords[3] = 208;
    iAdjCells[0] = 70;
    iAdjCells[1] = 72;
    iAdjCells[2] = Space.NO_ADJ;
    iAdjCells[3] = Space.NO_ADJ;
    iAdjCells[4] = Space.NO_ADJ;
    iAdjCells[5] = Space.NO_ADJ;

    theSpaces[71] = new Space(0,
    Space.CATEGORY_YELLOW,
    iAdjCells,
    71,
    new Polygon(iXCoords, iYCoords, 4));

    iXCoords = new int[6];
    iYCoords = new int[6];

    iXCoords[0] = 224;
    iYCoords[0] = 201;
    iXCoords[1] = 255;
    iYCoords[1] = 201;
    iXCoords[2] = 268;
    iYCoords[2] = 248;
    iXCoords[3] = 241;
    iYCoords[3] = 264;
    iXCoords[4] = 217;
    iYCoords[4] = 254;
    iXCoords[5] = 212;
    iYCoords[5] = 215;

    iAdjCells[0] = 46;
    iAdjCells[1] = 51;
    iAdjCells[2] = 56;
    iAdjCells[3] = 61;
    iAdjCells[4] = 66;
    iAdjCells[5] = 71;

    theSpaces[72] = new Space(0,
    Space.CATEGORY_YELLOW,    //I don't know what this is supposed to be.
    iAdjCells,
    72,
    new Polygon(iXCoords, iYCoords, 6));


    //load images
    theMediaTracker = new MediaTracker(this);
    ImgBackground = Toolkit.getDefaultToolkit().getImage("bkg.gif");
    ImgBoard = Toolkit.getDefaultToolkit().getImage("board.gif");

    theMediaTracker.addImage(ImgBackground, 0);
    theMediaTracker.addImage(ImgBoard, 0);
    try {
      theMediaTracker.waitForAll();
    } catch (InterruptedException e) {}

    addMouseListener(this);
    addMouseMotionListener(this);

  }

  /**
    * isSpaceClicked - searches Spaces array for the one that contains the
    *                   point (x, y)
    *                - uses Spaaces array
    *                - goes through each Space calling isInSpace(), if true
    *                    then return Space
    * @param int x, int y
    * @return Space
    **/
  private Space isSpaceClicked(int x, int y) {

    //Levi: be sure to change this too
    return null;

  }

  /**
    * listenForMove   - gets the Space the user selects
    *                 - uses mouse coordinates
    *                 - checks which space the player has chosen by
    *                     isSpaceClicked() and returns that Space
    *                 - if isSpaceClicked() returns null, continue waiting
    * @return Space
    **/
  public Space listenForMove(int x, int y) {
    int i;
    if ((Mindtrial.DEBUG == 5) || (Mindtrial.DEBUG == 1)) {System.out.println("listenForMove()");}

    Space SpaceToReturn = null;

    for(i = 0; i < NUM_SPACES; i ++) {
      if (theSpaces[i].isInSpace(x, y)) {
        SpaceToReturn = theSpaces[i];
        if (MTMainGame.getStatusBox().getDieDisplay() != 0) {
          MTMainGame.movePiece(SpaceToReturn);
        }


      }

    }


    return SpaceToReturn;
  }



  /**
    * SetActive - sets the active player and waits for user input
    *           - uses current to set the active Player
    *           - once the player rolls the dice in StatusBox object, takes
    *               in the current player's turn and sets the board as active,
    *               meaning the player can click on the place they want to move
    *               their piece
    *           - calls listenForMove()
    **/
  private void SetActive(Player currentPlayer) {

  }

  /**
    * updateDisplay - redraws the board
    *               - loops through the spaces Vector and uses the Players array
    *                   from Mindtrial class
    *               - goes through each space and checks if it is occupied; if
    *                   so draws the game piece occupying it in the middle of
    *                   the space; if not, redraw the Space; game piece info
    *                   will be obtained  from Players array; if multiple
    *                   players occupy a space, display only the current player
    * @param thePlayers[]
    **/
  private void updateDisplay(Graphics g) {

    bufferImage = createImage(getBounds().width, getBounds().height);
    bufferGraphics = bufferImage.getGraphics();
    damage = true;

    drawBoard(bufferGraphics);
    damage = false;
    g.drawImage(bufferImage, 0, 0, this);


  }




  /**
    * drawBoard - draws the new game board
    **/
  public void drawBoard(Graphics g) {
    int i, iXCoord, iYCoord;
    Vector thePlayers = MTMainGame.getPlayers();
    Player currentPlayer;
    Rectangle rectBounds;

    g.drawImage(ImgBackground, 0, 0, 480, 480, this);
    g.drawImage(ImgBoard, 0, 0, ImgBoard.getWidth(this), ImgBoard.getHeight(this), this);

    for (i = 0; i < thePlayers.size(); i++) {
      currentPlayer = (Player) thePlayers.elementAt(i);

      rectBounds = getSpace(currentPlayer.getLocation()).getBounds();

      iXCoord = rectBounds.x + (rectBounds.width / 2) - (Player.PIECE_RADIUS / 2);
      iYCoord = rectBounds.y + (rectBounds.height / 2) - (Player.PIECE_RADIUS / 2); 

      currentPlayer.drawPiece(g, iXCoord, iYCoord);
    }

  }

  /**
    * setClickable
    **/
  public void setClickable(boolean isClickable) {
    this.isClickable = isClickable;
  }



  /**
    * clearScreen - determines the size of the window by using the
    *               getBounds() method, then fills the window with
    *               the color specified by 'BACKGROUND_COLOR'
    **/
  private void clearScreen(Graphics g) {
    Rectangle r;
    r = getBounds();

    g.setColor(BACKGROUND_COLOR);
    g.fillRect(0, 0, r.width, r.height);
  }


  /**
    * getSpace - returns a space determined by location passed in
    **/
  public Space getSpace(int iLocation) {
    int i;
    Space SpaceToReturn = null;

      for (i = 0; i < NUM_SPACES; i++) {
        if (iLocation == theSpaces[i].getLocation()) {
          SpaceToReturn = theSpaces[i];
        }
      }

    return SpaceToReturn;
  }


  /**
    *  mouseClicked - MouseListener methods
    *  mouseEntered
    *  mouseExited
    *  mousePressed
    *  mouseReleased
    *  mouseDragged
    *  mouseMoved
    **/
  public void mouseClicked(MouseEvent e) {

  }
  public void mouseEntered(MouseEvent e) {

  }
  public void mouseExited(MouseEvent e) {

  }
  public void mousePressed(MouseEvent e) {

  }
  public void mouseReleased(MouseEvent e) {
    if (isClickable) {
      listenForMove(e.getX(), e.getY());
    }

  
   


  }
  public void mouseDragged(MouseEvent e) {
  }

  public void mouseMoved(MouseEvent e) {
    int iXPosition, iYPosition, i;

    iXPosition = e.getX();
    iYPosition = e.getY();


    if ((Mindtrial.DEBUG == 5) || (Mindtrial.DEBUG == 1)) {
      for (i = 0; i < 72; i++) {
        if (theSpaces[i].isInSpace(iXPosition, iYPosition)) {
          System.out.println("Over: " + i);
        }
      }
    }


  }




}