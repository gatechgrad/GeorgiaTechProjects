class Device {

  /** INSTANCE VARIABLES **/
  private int iDeviceID;
  private String strDeviceType;
  private String strName;
  private String strLocation;
  private String strDescription;
  private String strStatus;

  private static int iCurrentDeviceNumber = 0;

  /**
    * Device - constructor
    **/
  public Device(String strDeviceType) {

    iDeviceID = iCurrentDeviceNumber;
    iCurrentDeviceNumber++;

    this.strDeviceType = strDeviceType;
  }



  /**
    * set methods
    **/
  public void setName(String str) { strName = str; }
  public void setLocation(String str) { strLocation = str; }
  public void setDescription(String str) { strDescription = str; }


  /**
    * get methods
    **/
  public Integer getDeviceID() { return (new Integer(iDeviceID)); }
  public String getDeviceType() { return strDeviceType; }
  public String getLocation() { return strLocation; }
  public String getDescription() { return strDescription; }
  public String getStatus() { return strStatus; }

  public static int getCurrentID() { return iCurrentDeviceNumber; }

  /**
    * toString
    **/
  public String toString() {
    String strToReturn;

    strToReturn = "ID: " + iDeviceID + " Device Type: " + strDeviceType +
                  " Location: " + strLocation + " Description: " +
                  " Status: " + strStatus;

    return strToReturn;

  }











}
