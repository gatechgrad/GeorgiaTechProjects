/**
 * CS1502: Program #4
 *
 * <PRE>
 * Family Tree Emulator
 *
 * Revisions:  1.0  February 5, 1999
 *                  Created class ChildrenList 
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, February 5, 1999
 */

class ChildrenList {

//*** CONSTANTS
public static final boolean DEBUG = false; // turns debugging on/off
public static final String TERMINATE = "END"; //loop terminating condition

//*** INSTANCE VARIABLES
private TreeNode headPtr; //the head of this list
private String parentName; //the name of the parent of this list

//*** CONSTRUCTOR
/**
  * ChildrenList - the constructor... sets the parent name to the parent
  *                passed in and sets headPtr to null
  * Pre - a Children list must be created.
  * Post - headPtr and parentName is set.
  * @param parentName the name of the parent
  */
public ChildrenList(String parentName) {
   headPtr = null;
   this.parentName = parentName;
}//endmethod ChildrenList

/**
  * ChildrenList - another constructor... if no parent is passed in
  * Pre - ChildrenList must be created.
  * Post - headPtr and parentName are set to null.
  */
public ChildrenList() {
   headPtr = null;
   parentName = null;
}//endmethod ChildrenList


//*** MODIFIER METHODS

/**
  * addToList - adds a new node to the childrenlist
  * Pre - list must be created.
  * Post - node is added to the end of the list.
  * @param newNode the Node to add to the list
  */
public void addToList(TreeNode newNode) {
   if (DEBUG) System.out.println ("*** Entered AddToList");

   TreeNode tempPtr;
   if (headPtr == null) 
      headPtr = newNode;
   else {
      tempPtr = headPtr;
      while (tempPtr.getNextNode() != null)
         tempPtr = tempPtr.getNextNode();
      tempPtr.setNextNode(newNode);
   }
}//endmethod addToList
   



//*** ACCESSOR METHODS
/**
  * getHeadPtr - returns the head pointer
  * Pre - list must be created (must have data to return something).
  * Post - head pointer is returned.
  * @return a pointer to the first (head) node
  */
public TreeNode getHeadPtr() {
   return(headPtr);
}//endmethod getHeadPtr

/**
  * printNames - prints all of the names in the list... only used with DEBUG 
  *              for dataList 
  * Pre - list must have data.
  * Post - all nodes in the list are printed.
  */
public void printNames() {
//uses a similar process as used in addToList... we are just printing instead
//of adding
   if (DEBUG) System.out.println("*** Entered printNames");
   TreeNode tempPtr;
   if (headPtr == null) {
      System.out.println("No Children Nodes");
   } else {
      tempPtr = headPtr;

      while (tempPtr != null) {
         System.out.println(tempPtr);
         tempPtr = tempPtr.getNextNode();
      }
   }
}//endmethod printNames


/**
  * main - a test method for this class... you shouldn't worry about this
  * Pre - none.
  * Post - test run.
  * @param argv[] I still don't really know what this means... something
  *               about command line arguments or something
  */
public static void main (String argv[]) {

   String strParent = "Command.Com";
   ChildrenList testList = new ChildrenList("Command.Com");

   TreeNode node1 = new TreeNode("a", strParent);
   TreeNode node2 = new TreeNode("b", strParent);
   TreeNode node3 = new TreeNode("c", strParent);


   testList.addToList(node1);
   testList.addToList(node3);
   testList.addToList(node2);

} //endmethod main


} //class ChildrenList
