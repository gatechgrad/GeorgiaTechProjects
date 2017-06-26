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



public class Post {

private boolean fenceUp; //is there a fence going up from this post??
private boolean fenceDown; //is there a fence going down from this post??
private boolean fenceLeft; //is there a fence going left from this post??
private boolean fenceRight; // is there a fence going right from this post??


/**
  * Post - constructor for a regular post
  * Pre: call from player matrix
  * Post: posts are set
  *
  */
public Post() {
  fenceUp = false;
  fenceDown = false;
  fenceLeft = false;
  fenceRight = false;

}

/**
  * Post - the constructor for a post on one of the edges of the matrix
  * Pre: call from PlayerMatrix
  * Post: edge posts are set
  *
  * @param newUp is there a fence above
  * @param newDown is there a fence below
  * @param newLeft is there a fence on the left
  * @param newRight is there a fence to the right
  */
public Post(boolean newUp, boolean newDown, boolean newLeft,
  boolean newRight) {

  fenceUp = newUp;
  fenceDown = newDown;
  fenceLeft = newLeft;
  fenceRight = newRight;

}

/**
  * addLeftFence - adds a fence on the left
  * Pre: none
  * Post: a fence is added on the left
  *
  */
public void addLeftFence() {
  fenceLeft = true;
}

/**
  * addRightFence - adds a fence on the right
  * Pre: none
  * Post: a fence is added on the right
  *
  */
public void addRightFence() {
  fenceRight = true;
}

/**
  * addUpFence - adds a fence above the post
  * Pre: none
  * Post: a new fence is added above the post
  *
  */
public void addUpFence() {
  fenceUp = true;
}

/**
  * addDownFence - adds a fence below the post
  * Pre: none
  * Post: a fence is added below the post
  *
  */

public void addDownFence() {
  fenceDown = true;
}


/**
  * fenceAbove - returns wheter or not there is a fence above the post
  * Pre: none
  * Post: returns true if there is a fence above
  * @return if there is a fence above
  *
  */
public boolean fenceAbove() {
  return(fenceUp);
}

/**
  * fenceBelow - returns wheter or not a fence is below the post
  * Pre: none
  * Post: true if there is a fence below
  * @return if there is a fence below
  *
  */
public boolean fenceBelow() {
  return(fenceDown);
}

/**
  * fenceOnLeft - returns wheter or not there is a fence to the left of post
  * Pre: none
  * Post: returns true if there is a fence to the left
  * @return if there is a fence to the left
  *
  */
public boolean fenceOnLeft() {
  return(fenceLeft);
}

/**
  * fenceOnRight - returns true if there is a fence on the right
  * Pre: none
  * Post: returns true if there is a fence on the right
  * @return return true if there is a fence on the right
  *
  */
public boolean fenceOnRight() {
  return(fenceRight);
}


}
