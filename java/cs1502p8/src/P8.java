/**
 * CS1502: Program #8
 *
 * <PRE>
 * Fence Game
 *
 * Revisions:  1.0  March 5, 1999
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, March 5, 1999
 */



//NOTE: Sometimes the first or second fence may not display until the
//      next turn... This usually happens when the system gets loaded
//      down... I guess Java sacrifies the repaint process for other
//      ones... Anyway, keep clicking, and the fences should eventually
//      show up.


class P8{

/**
  * P8 - the constructor which makes a new FenceGame
  * Pre: new P8 created
  * Post: new FenceGame created
  *
  */

public P8() {
  FenceGame myFenceGame = new FenceGame();
}


/**
  * main - makes a new P8
  * Pre: program starts
  * Post: a new P8 is created
  *
  * @param argv[] Finally found out what this is... is is an array of
  *               console flags
  */
public static void main(String argv[]) {
  P8 myP8 = new P8();

}

}
