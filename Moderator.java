import java.util.Scanner;
import java.util.Vector;

public class Moderator {

   private static final int defaultNumBots = 100;
   private static final int defaultNumSpams = 1000;
   
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
            
         // Read each of a tweet and put its fields into a tweek object
         String[] fields = line.split(Constants.TWEET_FIELD_DELIMITER);
         fields[0] = fields[0].substring(1); // Remove the '"' char at the begining of "topic" string
         int lastIndex = fields[4].length() - 2;
         fields[4] = fields[4].substring(0, lastIndex); // Remove the '"' char at the end of "text" string
         
         Tweet tweet = new Tweet(fields);
         tweetBacklog.add(tweet);
         
         count++;
      }
      
      // Create TwitterBobs
      int nBots = defaultNumBots;
      String topic = "", sentm = "";
      float prob = 0.0f;
      
      System.out.println("Welcome to the Twitterb bot moderator!");
      System.out.printf("Please enter the number of Twitter bots you want me to play with (>= %d): ", defaultNumBots);
      nBots = consoleInput.nextInt();
      if (nBots < defaultNumBots) {
         System.out.printf("Oops, you have to make the number of bots equal or greater than %d. Please try it again.", defaultNumBots);
         return;
      }
      
      TwitterBot[] twitterBotStock = TwitterBot.createBots(nBots, topic, sentm, prob);
      
      if (twitterBotStock.length != nBots) {
         System.out.println("Failed in creating Twitter bots.");
         return;
      }
      
      // Test each of the twitter bots by spamming them with sample tweets
      int curTweetIndex = 0; // index of the current tweek in the tweet backlog
      for (int i = 0; i < nBots; i++) {   // for each of the bots
         // Set the attributes of the bot with those of a setting tweek from the tweet backlog
         int settingIndex = curTweetIndex / defaultNumSpams;
         Tweet settingTweet = (Tweet)tweetBacklog.get(settingIndex);
         twitterBotStock[i].setTopic(settingTweet.getTopic());
         twitterBotStock[i].setSentiment(settingTweet.getSentiment());
         for (int j = 0; j < defaultNumSpams; j++) {  // test the bot with 1000 sample tweets
            Tweet curtweet = (Tweet)tweetBacklog.get(j);
            curTweetIndex++;
         }
      }
   }
}