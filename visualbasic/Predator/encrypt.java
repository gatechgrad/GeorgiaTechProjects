import java.io.*;

public class encrypt {

    public static void main(String args[]) {
	try {
	    FileReader in = new FileReader(args[0]);
	    int key = Integer.parseInt(args[1]);
	    
	    int c = in.read();
	    
	    while (c != -1) {
		if (c != '\n') {
		    c += key;
		}
		System.out.print((char)c);

		c = in.read();
	    }
	    
	    in.close();
	} catch (Exception e) {
	    System.out.println("usage: encrypt <file> <key>");
	    System.exit(-1);
	}
    }
}
