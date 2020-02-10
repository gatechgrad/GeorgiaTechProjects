import java.util.Vector;

class Lockheed {

  /** INSTANCE VARIABLES **/
  private Vector vectDevices;  //a Vector holding all of the devices
  private Vector vectDeviceTypes;  //a Vector holding all of the device types (Strings)

  private DeviceDisplay theDeviceDisplay;

  /**
    * Lockheed - constructor
    **/
  public Lockheed() {
    vectDevices = new Vector();
    vectDeviceTypes = new Vector();

    loadDevicesFromDatabase();
    theDeviceDisplay = new DeviceDisplay(this);
  };



  /**
    * loadDevicesFromDatabase - reads the device tables from the SQL database
    **/
  private void loadDevicesFromDatabase() {

    vectDevices.removeAllElements();
    vectDeviceTypes.removeAllElements();

    //SQL calls go here
    //read all of the devices from the database and add them to the device
    //vector

    //sample data
    vectDevices.add(new Device("WATER_PUMP"));
    vectDevices.add(new Device("HEAT_SENSOR"));

    vectDeviceTypes.add("WATER_PUMP");
    vectDeviceTypes.add("HEAT_SENSOR");
  }

  /**
    * addDevice - adds a device to the system
    **/
  public void addDevice(Device theDevice) {
    vectDevices.add(theDevice);
    //SQL calls here

    theDeviceDisplay.refresh();

  }

  /**
    * removeDevice - removes devices from the system
    **/
  public void removeDevice(int[] iRowsToRemove) {
    int i;

    Vector vectDevicesToRemove;

    //create a vector containing the devices to remove
    vectDevicesToRemove = new Vector();
    for (i = 0; i < iRowsToRemove.length; i++) {
      vectDevicesToRemove.add(vectDevices.elementAt(iRowsToRemove[i]));
    }

    //remove the devices from the device vector
    for (i = 0; i < vectDevicesToRemove.size(); i++) {
      vectDevices.removeElement(vectDevicesToRemove.elementAt(i));

    }


    //also remember to update the SQL database



    theDeviceDisplay.refresh();
  }

  /**
    * addDeviceType - adds a device type
    **/
  public void addDeviceType(String str) {
    vectDeviceTypes.add(str);

    //will probably need to add the new device type to the SQL database
  }



  /**
    * get methods
    **/
  public Vector getDevices() { return vectDevices; };
  public Vector getDeviceTypes() {
    return vectDeviceTypes;
                          

  }



  /**
    * main
    **/
  public static void main(String args[]) {
    ValidationDisplay theValidationDisplay = new ValidationDisplay();
  }

}
