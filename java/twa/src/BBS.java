class BBS {

  /** INSTANCE VARIABLES **/
  private String strName;
  private String strHost;
  private int iPort;
  private String strVersion;
  private GameData theData;

  /**
    * BBS
    **/
  public BBS(String strName, String strHost, int iPort, String strVersion) {
    this.strName = strName;
    this.strHost = strHost;
    this.iPort = iPort;
    this.strVersion = strVersion;

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
  public String getVersion() {
    return strVersion;
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
    strToReturn = strName + "|" + strHost + "|" + iPort + "|" + strVersion;
    return strToReturn;
  }



}
