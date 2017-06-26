import java.util.Vector;

/* I have to do this to bypass the goofy IO protection
 * with Web browsers, even though I am just reading in data
 */

class GraphData {

private Vector myVector = new Vector();

public GraphData() {
  setupVector();

}


public void setupVector() {
//put your data here
  
  myVector.addElement("Who is your favorite Driver??");
myVector.addElement("Elliot|9");
myVector.addElement("Ernhart|2");
myVector.addElement("Ernhart Jr|1");
myVector.addElement("Gordon|6");
myVector.addElement("Jarrett|12");


}


public String getLine(int i) {
  if(i < myVector.size() ) {
    return((String) myVector.elementAt(i));
  } else {
    return null;
  }

}














}
