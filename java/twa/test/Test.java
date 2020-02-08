import java.util.Vector;

class Test {



  public static void main(String argv[]) {
    Vector myVector = new Vector();
    int i;

    myVector.add("One");
    myVector.add("Two");
    myVector.add("Three");
    myVector.add("Four");
    myVector.add("Five");
    myVector.add("Six");
    myVector.add("Seven");
    myVector.add("Eight");
    myVector.add("Nine");
    myVector.add("Ten");

    for (i = 0; i < myVector.size(); i++) {
      System.out.println("i: " + i);
      System.out.println(myVector.elementAt(i));
    }

    myVector.removeElementAt(0);

    System.out.println("---------------");

    for (i = 0; i < myVector.size(); i++) {
      System.out.println("i: " + i);
      System.out.println(myVector.elementAt(i));
    }



  }


}
