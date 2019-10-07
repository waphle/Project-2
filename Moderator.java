import java.util.Scanner;
import java.util.Vector;

public class Moderator {

   private static final int defaultNumBots = 100;
   private static final int defaultNumSpams = 1000;
   private static final float defaultBotRespCorrectness = 0.50f; // 50%
   
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
      float botThreshold = defaultBotRespCorrectness * 100.0f; // Bot identification threshold (Correct response percentage)
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
      
      System.out.printf("Please enter the bot vetting threshold (bot response correctness percentage): ");
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
      Vector correctBotIndece = new Vector();   // Indece of correctly identified bots
      Vector correctCountsTopicPlusSentiments = new Vector();  // Count of Topic+Sentiments of the correctly identified bots
      Vector incorrectBotIndece = new Vector();   // Indece of wrongly identified bots
      Vector incorrectCountsTopicPlusSentiments = new Vector();  // Count of Topic+Sentiments of the wrongly identified bots
      int tweetIndex = 0; // Index of the current tweek in the tweet backlog
      
      for (int i = 0; i < nBots; i++) {   // For each of the bots
         TwitterBot curBot = twitterBotStock[i];
         
         // Set the attributes of the bot with those of the start tweet of a same topic in the tweet backlog
         int settingIndex = topicStartIndece[nBots % topicStartIndece.length];
         Tweet settingTweet = (Tweet)tweetBacklog.get(settingIndex);
         curBot.setTopic(settingTweet.getTopic());
         curBot.setSentiment(settingTweet.getSentiment());
         
         // Test the bot with 1000 sample tweets from the backlog
         int correctResps = 0;   // Number of correct response
         for (int j = 0; j < defaultNumSpams; j++) {
            Tweet tweet = (Tweet)tweetBacklog.get(tweetIndex);
            Tweet response = curBot.reply(tweet);
            // Only when the topic and sentiment of a bot's response match those
            // of the input tweet, the response is regarded as correct:
            if (response.getTopic().equalsIgnoreCase(tweet.getTopic()) &&
                response.getSentiment().equalsIgnoreCase(tweet.getSentiment())) { 
               correctResps++;
            }
            
            // Reset current tweet pointer to reuse the tweets in the backlog when the tweet counter  
            // exceeds the maximum number of the tweets in the backlog:
            tweetIndex++;
            if (tweetIndex >= nTweets)
               tweetIndex %= nTweets;     
         }
         
         // Vet the tweet responder to be a bot or not - if the response correctness percentage  
         // is less than the given threshold, the responder is regarded as a bot, and its index is recorded:
         float correctPercentage = correctResps * 100.0f / defaultNumSpams;
         if ( correctPercentage < botThreshold) {
            correctBotIndece.add(i);
            correctCountsTopicPlusSentiments.add(correctResps);
         }
         else {
            incorrectBotIndece.add(i);
            incorrectCountsTopicPlusSentiments.add(defaultNumSpams - correctResps);
         }
      }
      
      // Print out the twitter bot identification results
      System.out.println();
      System.out.printf("Percentage of correctly identified twitter bots: %.2f%%\n", (float)correctBotIndece.size() * 100 / nBots);
      
      System.out.printf("Counts of correct responses out of %d spams of the correctly identified bots:\n", defaultNumSpams);
      for (int i = 0; i < correctBotIndece.size(); i++) {
         int index = (int)correctBotIndece.get(i);
         int respCount = (int)correctCountsTopicPlusSentiments.get(i);
         System.out.printf("Twitter Bot #%d: %d\n", index, respCount);
      }
      
      System.out.println();
      System.out.printf("Counts of incorrect responses out of %d spams of the wrongly identified bots:\n", defaultNumSpams);
      for (int i = 0; i < incorrectBotIndece.size(); i++) {
         int index = (int)incorrectBotIndece.get(i);
         int respCount = (int)incorrectCountsTopicPlusSentiments.get(i);
         System.out.printf("Twitter Bot #%d: %d\n", index, respCount);
      }
   }
}