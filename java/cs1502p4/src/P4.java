/**
 * CS1502: Program #4
 *
 * <PRE>
 * Family Tree Emulator
 *
 * Revisions:  1.0  February 5, 1999
 *                  Created class P4 (driver class)
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, February 5, 1999
 */

//NOTE: I am using a modified version of my addToList method that I
//      made in p3... my accessor and some of my modifier methods are
//      also similar to the ones I have already written (how do you write
//      them differnetly??  getNextNode, setNextNode, etc. always do the
//      same thing.)  I'm also using a menu similar to the menu style in
//      P1 and P2

//TOD, BE SURE TO READ THIS:  I did the formatting Extra Credit so my program
//will not fc properly with p4_100.ans or p4_extra.ans unless the /w tag is
//used with fc.  Or alteratively, you can go into my "TreeNode.java" file and
//change the constant "INDENTING" to false.  I also did the search with path
//Extra Credit so be sure to check out option #3 on the menu.
//Also, I never got around to assigning IDs to people.  If two people have
//the same name the program will probably crash :-(

class P4 {

//*** CONSTANTS
public static final boolean DEBUG = false; // turns debugging on/off
public static final String TERMINATE = "END"; //terminating String for loop
public static final boolean DEBUGPROCESSING = false; //displays processing
                                                    //messages


//*** INSTANCE VARIABLES
private TreeNode rootNode; //the head of the data list
private ChildrenList dataList; // the data list
private TreeNode treeRootNode; //the root of the tree
private ChildrenList treeList; //the children list of the root tree node
private TreeNode nodeToFind;//Used to see if a name is found... I had to make
                            //it an instance variable because it would print
                            //out the "Name is not in the tree." message too
                            //many times when I put it in the helper module

//*** CONSTRUCTOR


/**
  * P4 - constructor... called when a new P4 is created
  * Pre - new P4 must be created.
  * Post - Root Node for tree and head node for dataList is created, then
  * nodes are added to the data List.  The data is then converted to a tree
  */

public P4() {
   nodeToFind = null;
   dataList = new ChildrenList();

   //read in the root node's name
   System.out.println("Enter the root node's name :\n");
   String strEnterName = util.readLine();

   //set the name just entered to the head of the data list and the root
   //of the tree
   rootNode = new TreeNode(strEnterName);
   treeRootNode = new TreeNode(strEnterName);

   //add the root node to the data list
   dataList.addToList(rootNode);

   //add the rest of the nodes to the data list
   TreeNode newDataPtr = dataList.getHeadPtr();
   fillDataList(newDataPtr, newDataPtr.getName());

   if (DEBUG) dataList.printNames();
   if (DEBUGPROCESSING) System.out.print("Processing ");

   //take the data list and make a tree out of it
   makeATree(treeRootNode);

   System.out.println("");  
   menu();

}//endmethod P4



/**
  * fillDataList - adds nodes to the data list
  * Pre - root node must be created.
  * Post - data list will be filled.
  * @param tempPtr the current position in the data List.
  * @param strAddParent the node's parent name.
  */

public void fillDataList(TreeNode tempPtr, String strAddParent) {
   String strChoice;

   do {
      System.out.println("Enter " + tempPtr.getName() +
         "'s child's name :");
      strChoice = util.readLine();

      //add a new node unless the user has typed "END"
      if (!strChoice.equals (TERMINATE)){
            
            TreeNode myTreeNode = new TreeNode(strChoice, strAddParent);
            dataList.addToList(myTreeNode);
      }

   } while (!strChoice.equals (TERMINATE));

   if (tempPtr.getNextNode() != null) {
      System.out.println("");
      tempPtr = tempPtr.getNextNode();

      fillDataList(tempPtr, tempPtr.getName());
   }

}


/**
  * makeATree - makes a tree out of the data List
  * Pre - a data list must be created.
  * Post - a tree is created... root node is treeRootNode.
  * @param tempPtr the current position in the tree.
  */

public void makeATree(TreeNode tempPtr) {
if (DEBUG) System.out.println("*** Entered makeATree");
if (DEBUG) dataList.printNames();
   if (tempPtr != null) {
            if (DEBUG) System.out.println("*** Finding children of " +
               tempPtr.getName());

            //finds the children of the current tree node
            tempPtr.findChildren(dataList.getHeadPtr());

         if (DEBUG) System.out.println("*** makeATree: Recursing");
      makeATree(tempPtr.getChildrenList().getHeadPtr());
      makeATree(tempPtr.getNextNode());
   }
} //makeATree


//*** MAIN
/**
  * main - this just makes a new P4 object
  * Pre - program must be executed.
  * Post - a new P4 object is created.
  * @param argv[] a command list array or something.
  */
public static void main (String argv[]) {
   P4 myP4 = new P4();
}//endmethod main


//*** MENU METHODS
/**
  * menu - the program's menu... reads in a choice from the user and then
  *        calls the appropirate method... is redisplayed until the user
  *        wants to exit
  * Pre - P4 must be created and initalized.
  * Post - reads in user's data and calls the appropriate method.
  */

public void menu() {
   int iChoice;
   boolean keeplooping = true;

   System.out.println("Welcome to Winter 99 Program 4");
   System.out.println("------------------------------\n");

   while(keeplooping) {
      System.out.println("(1) Print the Tree");
      System.out.println("(2) Search for a Name");
      System.out.println("(3) Search for a Name, and Display its Path");
      System.out.println("(0) Quit");

      iChoice = util.readInteger().intValue();

            if (iChoice == 1) 
                 printTree(treeRootNode);
       else if (iChoice == 2) 
               searchTree(false);
       else if (iChoice == 3) 
               searchTree(true);
       else if (iChoice == 0) 
               keeplooping = false;
      
   }
      System.exit(0);
}//endmethod menu


/**
  * printTree - prints all of the nodes in the tree in pre-order fashion
  * Pre - tree must be created.
  * Post - tree is printed.
  * @param tempPtr the current node position in the tree... starts at
  *                tree root node.
  */
public void printTree(TreeNode tempPtr) {
   String spacer = "";
   tempPtr.printChildren( tempPtr, spacer);
} //endmethod printTree


/**
  * searchTree - searches the tree for a name entered by the user
  * Pre - there must be an existing tree.
  * Post - prints wheter the name is in the tree or not... if showPath is
  *        true, then the path is printed
  * @param showPath if true, then path is shown... if false, path is not
  *                 shown.
  */
public void searchTree(boolean showPath) {
   nodeToFind = null;
   String spacer = "";
   String newNameToSearch;

   System.out.println("Enter the name to search :");
   newNameToSearch = util.readLine();

   //call the helper to search the tree, and set nodeToFind to the node
   //that is being searched... if node is not in the tree then leave
   //nodeToFind set to null
   searchTreeHelper(treeRootNode, newNameToSearch);

   //if the node has data, then print the name, else, the node was not found
   if (nodeToFind != null) {
      System.out.println(newNameToSearch + " is in the tree.");
   }
   else
      System.out.println(newNameToSearch + " is not in the tree.");


   //if the node is in the tree, and showPath is on, then search the tree
   //for all of the parents
   //the reason why I didn't make a separate method for this is because I
   //would have to duplicate all of the code written above
   if ((showPath) && (nodeToFind != null)) {

      //find the parent node unless the parent is null (root pointer)
      while(nodeToFind.getParentName() != null) {
         //print relationship to parent
         System.out.println(spacer + nodeToFind.getName() + ", " +
            nodeToFind.getParentName() + "'s child");
   
         //set nodeToFind to the parent node by searching the tree
         searchTreeHelper(treeRootNode, nodeToFind.getParentName());
         spacer = (spacer + "  ");
      }
   }
}//searchTree


/**
  * searchTreeHelper - searches for a name in the tree, then set that
  *                    nodeToFind to the node found
  * Pre - must have a tree to search.
  * Post - if node is found, then nodeToFind is set to that node, if it
  *        is not found, then nodeToFind remains null.
  * @param tempPtr current node being searched.
  * @param strNameToSearch the name to be searched
  *               
  */
public void searchTreeHelper(TreeNode tempPtr, String strNameToSearch) {

 if (tempPtr != null) {

   //if the node is found, then assign it to nodeToFind
   if (tempPtr.getName().equals(strNameToSearch)) {
      if (DEBUG) System.out.println("Changing nodeToFind => " +
      strNameToSearch );
      nodeToFind = tempPtr;

   }



   if (DEBUG) System.out.println("Searching => " + tempPtr.getName());


   //search down, and then search across
   if (tempPtr.getChildrenList().getHeadPtr() != null){
      searchTreeHelper(tempPtr.getChildrenList().getHeadPtr(),
         strNameToSearch);
   }
   if (tempPtr.getNextNode() != null) {
      if (DEBUG) System.out.println("Searching => " + tempPtr.getName());
      searchTreeHelper(tempPtr.getNextNode(), strNameToSearch);
   }

 }
}//endmethod searchTreeHelper


} //class P4
