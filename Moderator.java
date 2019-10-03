import java.util.Scanner;
import java.util.Vector;

public class Moderator {
   public static void main(String[] args) throws Exception {
      java.io.File file = new java.io.File("full-corpus.csv");
      Scanner input = new Scanner(file, "UTF-8");
      
      // Load all tweets from the data file into a vector of TWeeks
      Vector tweetBacklog = new Vector();
      int count = 0;
      while (input.hasNext()) {
         String line = input.nextLine();
         
         // Drop the first line that consists of the field names
         if (count == 0)
            continue;
            
         // Read each of a tweet and put its fields into a tweek object
         String[] fields = line.split(Constants.TWEEK_FIELD_DELIMITER);
         fields[0] = fields[0].substring(1); // Remove the '"' char at the begining of "topic" string
         int lastIndex = fields[4].length() - 2;
         fields[4] = fields[4].substring(0, lastIndex); // Remove the '"' char at the end of "text" string
         
         Tweet tweet = new Tweet(fields);
         tweetBacklog.add(tweet);
         
         count++;
      }
      
      
   }
}