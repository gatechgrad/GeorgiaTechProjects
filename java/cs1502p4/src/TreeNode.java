/**
 * CS1502: Program #4
 *
 * <PRE>
 * Family Tree Emulator
 *
 * Revisions:  1.0  February 5, 1999
 *                  Created class TreeNode 
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, February 5, 1999
 */

class TreeNode {

//*** CONSTANTS
public static final boolean DEBUG = false; // turns debugging on/off
public static final String TERMINATE = "END"; //loop terminating condition
public static final boolean DEBUGPROCESSING = false; //displays processing
public static final boolean INDENTING = true; // turns indenting on/off

//*** INSTANCE VARIABLES
private TreeNode next; //the next in the list
private String strName; //the person's name
private ChildrenList myChildrenList; //the person's list of children
private String strParentName;//the person's parent's name

//*** CONSTRUCTORS
/**
  * TreeNode - constructor with no parameters
  * Pre - new TreeNode must be created.
  * Post - next is set to null, new ChildrenList is created
  */
public TreeNode() {
   next = null;
   myChildrenList = new ChildrenList();
   strName = null;
} //endmethod TreeNode

/**
  * TreeNode - constructor with one parameter
  * Pre - TreeNode must be created.
  * Post - next is set to null, strName is set
  *        to the name passed in, and new ChildrenList is made
  * @param newName the name of this person
  */
public TreeNode(String newName) {
   strName = newName;
   next = null;
   myChildrenList = new ChildrenList();
} //endmethod TreeNode

/**
  * TreeNode - constructor with three parameters
  * Pre - a TreeNode must be created.
  * Post - strParentName is set to the parent's name, strName is set to
  *        this node's name, new ChildrenList is created, and next is
  *        set to null
  * @param newName the name of this person
  */
public TreeNode(String newName, String newParentName) {
   strParentName = newParentName;
   strName = newName;
   next = null;
   myChildrenList = new ChildrenList();
} //endmethod TreeNode


//*** MOIDIFIERS

/**
  * findChildren - searches the dataList for all children... when a child is
  *                found, it is added to the ChildrenList
  * Pre - there must be a dataList to search.
  * Post - all nodes in the dataList that have this node as a parent are
  *        added to the childrenList.
  * @param tempPtr the current position in the data list.
  *                
  */

public void findChildren(TreeNode tempPtr) {
     
   if (DEBUGPROCESSING) System.out.print(". ");
   if (DEBUG) System.out.println("*** Entering findChildren");

   if (tempPtr != null) {

      //if the data node has a parent that is equal to this node, then add
      //it to this node's childrenList
      if (strName.equals(tempPtr.getParentName())) {
      if (DEBUG) System.out.println("*** Adding " + tempPtr.getName() +
         " to list");
             TreeNode myTreeNode = new TreeNode(tempPtr.getName(), strName);
             this.myChildrenList.addToList(myTreeNode);
      } else {
      if (DEBUG) System.out.println("*** " + tempPtr.getName() +
         " not a child of " + strName + "... recursing");
      }

      if (DEBUG) System.out.println("*** Current Children:  ");
      if (DEBUG) myChildrenList.printNames();
   
      //go to the next node in the dataList
      if (tempPtr.getNextNode() != null)
         findChildren(tempPtr.getNextNode());
   } else {
      //proceed to the next node in the dataList
      if (tempPtr.getNextNode() != null)
         findChildren(tempPtr.getNextNode());

   }
   if (DEBUG) System.out.println("*** exiting findChildren");
}//endmethod findChildren


/**
  * printChildren - prints all children of this node
  * Pre - node must have children.
  * Post - all children are printed and each child's child is printed.
  * @param tempPtr the node to have it's children printed.
  * @param spacer keeps track of how many spaces to indent.
  */
public void printChildren(TreeNode tempPtr, String spacer) {

          if (tempPtr.getParentName() == null) {
             System.out.println(tempPtr.getName());
          } else {
             System.out.println(spacer + tempPtr.getName() + ", " +
               tempPtr.getParentName() + "'s child");
          }

          if (DEBUG) System.out.println("Temp is: " + tempPtr.getName());  
          if (DEBUG) System.out.println("Passing in: " +
            tempPtr.getChildrenList().getHeadPtr().getName());


          if (tempPtr.getChildrenList().getHeadPtr() != null) {
             if (INDENTING) spacer = (spacer + "  "); //add two spaces


             printChildren(tempPtr.getChildrenList().getHeadPtr(), spacer);

             if (INDENTING) spacer = spacer.substring(2);//subtract two spaces

          }
          if  (tempPtr.getNextNode() != null) {
            
            printChildren(tempPtr.getNextNode(), spacer);

          }
} //endmethod printChildren




/**
  * setNextNode - sets the value of the next node
  * Pre - must pass in data.
  * Post - next node is set to the value passed in.
  * @param data the node data to add to the next node
  */
public void setNextNode (TreeNode data) {
   next = data;   
} //endmethod setNextNode


/**
  * setName - sets the name of this node
  * Pre - none.
  * Post - name is set.
  * @param newName the name to set
  */

public void setName (String newName) {
   strName = newName;
} //endmethod setName


//*** ACCESSORS
/**
  * getNextNode - returns the next node
  * Pre - next node must not be null.
  * Post - the next node is returned.
  * @return the next node
  */
public TreeNode getNextNode() {
   return(next);
}//endmethod getNextNode


/**
  * getChildrenList - returns this node's childrenList
  * Pre - childrenList must have data.
  * Post - childrenList is returned.
  * @return this node's childrenList
  */
public ChildrenList getChildrenList() {
   return(myChildrenList);
}//endmethod getChildrenList

/**
  * getName - returns the name of this node
  * Pre - none.
  * Post - the name of this node is returned.
  * @return the name of this node 
  */
public String getName() {
   return(strName);
}//endmethod getName

/**
  * getParentName - returns the parent name of this node
  * Pre - none.
  * Post - the parent name is returned.
  * @return the parent name
  */
public String getParentName() {
   return(strParentName);
}//endmethod getParentName



/**
  * toString - a default String that is returned when the node is printed
  * Pre - none.
  * Post - the default String is returned.
  * @return the default String
  */
public String toString() {
   String returnThis;
   returnThis = ("Current Node is: " + strName + "\n   Parent Node: " +
      strParentName);
   return(returnThis);

}//endmethod toString


} //class TreeNode
