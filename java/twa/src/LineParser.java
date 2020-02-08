public interface LineParser {

  /**
    * all methods that implement this must support line parsing
    **/
  public void parseString(String theString);
  public void quitParsing();


}
