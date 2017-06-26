class GraphElement {

private String name;
private float flDataValue;


public GraphElement(String newName, float newDataValue) {
  name = newName;
  flDataValue = newDataValue;

}


public GraphElement() {
  name = "";
  flDataValue = 0;

}


public String getName() {
  return name;
}

public float getDataValue() {
  return flDataValue;
}

public void setName(String newName) {
  name = newName;
}

public void setDataValue(float newDataValue) {
  flDataValue = newDataValue;
}

public String toString() {
  String returnThis;

  returnThis = ("Data Name: " + name + "\nData Value: " + flDataValue);
  return returnThis;



}



}
