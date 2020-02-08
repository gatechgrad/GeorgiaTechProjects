class Rules {

  /********************** CONSTANTS **********************/
  public static final int TRADE_ONE = 1;
  public static final int TRADE_DIFF = 2;
  public static final int TRADE_DIRECT = 3;
  public static final int TRADE_ALL = 4;



  /********************** CLASS VARIABLES **********************/
  private boolean isOpen;
  private boolean isSuddenDeath;
  private boolean isRandom;
  private boolean isSame;
  private boolean isPlus;
  private boolean isSameWall;
  private boolean isElemental;

  private int iTradeRule;

  /**
    * Rules - constructor
    **/
  public Rules(int newTradeRule, boolean newOpen, boolean newSuddenDeath,
               boolean newRandom, boolean newSame, boolean newPlus,
               boolean newSameWall, boolean newElemental) {

  isOpen = newOpen;
  isSuddenDeath = newSuddenDeath;
  isRandom = newRandom;
  isSame = newSame;
  isPlus = newPlus;
  isSameWall = newSameWall;
  isElemental = newElemental;

  iTradeRule = newTradeRule;



  }












}
