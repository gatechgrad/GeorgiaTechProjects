import java.util.Vector;

class City {
  /********************** CONSTANTS **********************/

  /********************** CLASS VARIABLES **********************/
  Vector thePlayers;
  Rules theRules;
  String strName;

  public City(String newName) {
    thePlayers = new Vector();
    strName = newName;
    theRules = new Rules(Rules.TRADE_ONE, false, false, false, false,
                         false, false, false);
  }

  /**
    * addPlayer - adds a player to this city
    **/
  public void addPlayer(Player newPlayer) {
    thePlayers.addElement(newPlayer);
  }

  /**
    * getRules - returns the rules for this area
    **/
  public Rules getRules() {
    return theRules;
  }

  /**
    * setRules
    **/
  public void setRules(Rules newRules) {
    theRules = newRules;
  }


}
