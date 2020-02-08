import java.net.URL;
import java.net.MalformedURLException;
import java.applet.Applet;
import java.applet.AudioClip;


class Sound {
  private AudioClip theAudioClip;
  private boolean isPlaying;

  public Sound(String strFileName) {
   try {
      URL urlSound = new URL("file://" + TripleTriad.DIRECTORY + strFileName);
      theAudioClip = Applet.newAudioClip(urlSound);
   } catch (MalformedURLException e) {
     System.out.println("Filename error");
   }
   isPlaying = false;

  }


  public void play() {
    theAudioClip.play();
    isPlaying = true;
  }

  public void stop() {
    theAudioClip.stop();
    isPlaying = false;
  }

  public void loop() {
    theAudioClip.loop();
    isPlaying = true;
  }

  public boolean isPlaying() {
    return isPlaying;
  }

}

