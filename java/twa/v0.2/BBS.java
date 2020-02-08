class BBS {

  /** INSTANCE VARIABLES **/
  private String strName;
  private String strHost;
  private int iPort;
  private int iVersion;
  private GameData theData;

  /**
    * BBS
    **/
  public BBS(String strName, String strHost, int iPort, int iVersion) {
    this.strName = strName;
    this.strHost = strHost;
    this.iPort = iPort;
    this.iVersion = iVersion;

    loadGameData();
  }

  /**
    * loadGameData
    **/
  private void loadGameData() {
    theData = new GameData();  //for now
  }

  /**
    * getName
    **/
  public String getName() {
    return strName;
  }

  /**
    * getHost
    **/
  public String getHost() {
    return strHost;
  }

  /**
    * getPort
    **/
  public int getPort() {
    return iPort;
  }

  /**
    * getVersion
    **/
  public int getVersion() {
    return iVersion;
  }

  /**
    * getGameData
    **/
  public GameData getGameData() {
    return theData;
  }


  /**
    * toString
    **/
  public String toString() {
    String strToReturn;
    strToReturn = strName + "|" + strHost + "|" + iPort + "|" + iVersion;
    return strToReturn;
  }



}
