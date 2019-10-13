import java.util.Scanner;
import java.util.Vector;

public class Moderator {
   private static final int defaultNumBots = 100;
   private static final int defaultNumSpams = 1000;
   private static final float defaultBotRespCorrectness = 0.50f; // 50% use
   
   // Topic start indece in the tweet backlog
   private static final int[] topicStartIndece = { 0,         // "Apple"
                                                   1142,      // "Google"
                                                   2459,      // "Microsoft"
                                                   3824       // "Twitter"
                                                 };                                                  
   
   public static void main(String[] args) throws Exception {
   
      java.io.File file = new java.io.File("full-corpus.csv");
      Scanner fileInput = new Scanner(file, "UTF-8");
      Scanner consoleInput = new Scanner(System.in); 

      // Load all tweets from the data file into a backlog (vector)
      Vector tweetBacklog = new Vector();
      int count = 0;
      while (fileInput.hasNext()) {
         String line = fileInput.nextLine();
         
         // Drop the first line that consists of the field names
         if (count == 0) {
            count++;
            continue;
         }

}