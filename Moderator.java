import java.util.Scanner;
import java.util.Vector;

public class Moderator {

   private static final int defaultNumBots = 100;
   private static final int defaultNumSpams = 1000;
   private static final float defaultBotRespCorrectness = 0.50f; // 50%
   
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
      
      // Number of tweets in the backlog
      int nTweets = tweetBacklog.size();
      
      // Create TwitterBobs
      int nBots = defaultNumBots;
      float botThreshold = defaultBotRespCorrectness * 100.0f; // Bot identification threshold (response percentage)
      String topic = Constants.TOPIC_NONE;
      String sentm = Constants.SENTIM_NEUTRAL;
      float prob = 0.0f; // Topic-irrelevant trick probability applied to the twitter bots: when prob = 0, no tricks applied.
      
      System.out.println("Welcome to the Twitterb bot moderator!");
      System.out.printf("Please enter the number of Twitter bots you want me to play with (>= %d): ", defaultNumBots);
      nBots = consoleInput.nextInt();
      if (nBots < defaultNumBots) {
         System.out.printf("Oops, you have to make the number of bots equal or greater than %d. Please try it again.", defaultNumBots);
         return;
      }
      
      System.out.printf("Please enter the bot vetting threshold percentage: ");
      botThreshold = consoleInput.nextFloat();
      if (botThreshold < 0.0f || botThreshold > 100.0f) {
         System.out.printf("Oops, the percentage number %f is invalid. Please try it again.", botThreshold);
         return;
      }
      
      TwitterBot[] twitterBotStock = TwitterBot.createBots(nBots, topic, sentm, prob);
      
      if (twitterBotStock.length != nBots) {
         System.out.println("Failed in creating Twitter bots.");
         return;
      }
      
      // Test each of the twitter bots by spamming them with sample tweets
      Vector correctBotIndece = new Vector(); // Indece of correctly identified bots
      int tweetIndex = 0; // Index of the current tweek in the tweet backlog
      
      for (int i = 0; i < nBots; i++) {   // For each of the bots
         TwitterBot curBot = twitterBotStock[i];
         
         // Set the attributes of the bot with those of a setting tweek extracted from the tweet backlog
         int settingIndex = tweetIndex / defaultNumSpams;
         Tweet settingTweet = (Tweet)tweetBacklog.get(settingIndex);
         curBot.setTopic(settingTweet.getTopic());
         curBot.setSentiment(settingTweet.getSentiment());
         
         // Test the bot with 1000 sample tweets from the backlog
         int correctResps = 0; // Number of correct response
         for (int j = 0; j < defaultNumSpams; j++) {
            Tweet tweet = (Tweet)tweetBacklog.get(tweetIndex);
            Tweet response = curBot.reply(tweet);
            if (response.getSentiment().equalsIgnoreCase(tweet.getSentiment())) {
               correctResps++;
            }
            
            // Reset current tweet pointer to reuse the tweets in the backlog
            tweetIndex++;
            if (tweetIndex >= nTweets)
               tweetIndex %= nTweets;     
         }
         
         // Vet the tweet responder to be a bot or not - if the response correctness rate  
         // is less than the given threshold, the responder is regarded as a bot, and record it.
         if (correctResps * 100.0f / defaultNumSpams < botThreshold) {
            correctBotIndece.add(i);
         }
      }
      
      // Print out the twitter bot identification results
      System.out.printf("Percentage of correctly identified twitter bots: %.2f%%", (float)correctBotIndece.size() * 100 / nBots);
      
   }
}