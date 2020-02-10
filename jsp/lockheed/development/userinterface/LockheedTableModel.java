import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;

class LockheedTableModel extends AbstractTableModel {
  protected Vector vectDevices;


  protected String[] columnNames = new String[] {
    "ID", "Device Type", "Location", "Description", "Status"
  };

  protected Class[] columnClasses = new Class[] {
    Integer.class, String.class, String.class, String.class, String.class
  };

  public LockheedTableModel(Vector vectDevices) {
    this.vectDevices = vectDevices;
  }

  public int getColumnCount() { return 5; }
  public int getRowCount() { return vectDevices.size(); }

  public String getColumnName(int i) { return columnNames[i]; }
  public Class getColumnClass(int i) { return columnClasses[i]; }

  public Object getValueAt(int iRow, int iCol) {
    Object objToReturn;
    Device theDevice = (Device) vectDevices.elementAt(iRow);

    switch(iCol) {
      case 0:
        objToReturn = theDevice.getDeviceID();
        break;
      case 1:
        objToReturn = theDevice.getDeviceType();
        break;
      case 2:
        objToReturn = theDevice.getLocation();
        break;
      case 3:
        objToReturn = theDevice.getDescription();
        break;
      case 4:
        objToReturn = theDevice.getStatus();
        break;
      default:
        objToReturn = "";
    }

    return objToReturn;
  }




}
