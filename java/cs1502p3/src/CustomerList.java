/**
 * CS1502: Program #3
 *
 * <PRE>
 * Saturday Night at the MOvies
 *
 * Revisions:  1.0  January 29, 1999
 *                  Created class CustomerList
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, January 29, 1999
 */

class CustomerList {

   //*** CONSTANTS
   public static final boolean DEBUG = false; //debugging constant

   //*** INSTANCE VARIABLE
   private CustomerNode currentPtr;

   //*** CONSTRUCTOR
   /**
    * CustomerList - makes a new CustomerList.
    * Pre - this class must be created from the driver.
    * Post - a new, null CustomerList is created.
    */
   public CustomerList () {
      currentPtr = null;
   }

   //*** MODIFIERS
   /**
    * addToList - adds a new customerNode to the linked list.
    * Pre - must be called from the driver.
    * Post - a new node is added to the list.
    */
   public void addToList (CustomerNode newCustomer) {
      CustomerNode tempPtr;
      if (currentPtr == null) {
         currentPtr = newCustomer;
      } else {
         tempPtr = currentPtr;
         while (tempPtr.getNextNode() != null) {
            tempPtr = tempPtr.getNextNode();
         }
         tempPtr.setNextNode(newCustomer);
      }
   } // endmethod addToList


   //*** ACCESSOR METHODS

   /**
    * traverseList - traverses the linked list / for use with DEBUG only.
    * Pre - DEBUG must be on.
    * Post - traverses the list.
    */
   public void traverseList () {
      traverseListHelper(currentPtr);
   }

   /**
    * traverseListHelper - helps traverseList.
    * Pre - call from traverseList.
    * Post - all nodes are printed.
    * @param tempPtr The linked list to be printed
    */
   private void traverseListHelper (CustomerNode tempPtr) {
      if (tempPtr != null) {
         getString(tempPtr);
         traverseListHelper (tempPtr.getNextNode());
      }
   }// traverseListHelper

   /**
    * getString - prints the toString of the current node.
    * Pre - call from another method.
    * Post - node data is printed.
    * @param printThis The node to be printed
    */
   public void getString (CustomerNode printThis) {
      System.out.println (printThis);
   }


   /**
    * getCurrentPtr - returns the head pointer.
    * Pre - none.
    * Post - the head pointer is returned.
    * @return the CustomerList head pointer
    */
   public CustomerNode getCurrentPtr() {
      return (currentPtr);
   }


}//endclass Customer List
