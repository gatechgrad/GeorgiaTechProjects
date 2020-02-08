import java.net.*;
import java.io.*;

class TWAdvantage {

  private BufferedInputStream myInputStream;
  private BufferedOutputStream myOutputStream;


  public TWAdvantage() {
    int i = 0;

    try {
      Socket mySocket = new Socket("oscar.cc.gatech.edu", 23);
      myInputStream = new BufferedInputStream(mySocket.getInputStream());
      myOutputStream = new BufferedOutputStream(mySocket.getOutputStream());



     while(i < 1000) {
      int iCount = myInputStream.available();
      byte bBuffer[] = new byte[iCount];
      iCount = myInputStream.read(bBuffer);



      System.out.println("Count: " + iCount);
      System.out.println(bBuffer);

    }

      mySocket.close();


    } catch (Exception e) {
      System.out.println("Host not found");
    }



  }




  public static void main(String argv[]){
    new TWAdvantage();
  }


}
