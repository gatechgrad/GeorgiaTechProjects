class Brute {




  public static void main(String argv[]) {
    int i;
    int a, b;
    long iTotalTime;

    a = 1;
    b = 2;
    boolean isTest;

    long startTime;
    long stopTime;

    startTime = System.currentTimeMillis();
    for (i = 0; i < 1000000000; i++) {
        isTest = true;

    }
    stopTime = System.currentTimeMillis();

    iTotalTime = stopTime - startTime;

    System.out.println("Time: " + iTotalTime);

  }

}
