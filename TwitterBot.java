import java.util.Scanner;
import java.util.Random;
import java.util.Date;

public class TwitterBot {
   // Attributes
   private String topic;
   private String sentiment;
   private float irreTweetProbability;
   private static Random rand = new Random();
   private boolean guessMade;

  
   // Constructors
   private TwitterBot() {
      this.topic = Constants.TOPIC_NONE;
      this.sentiment = Constants.SENTIM_NEUTRAL;
      this.irreTweetProbability = 0.0f;
      this.guessMade = false;
   }

   private TwitterBot(String topic, String sentm, float prob) {
      this.topic = topic;
      this.sentiment = sentm;
      this.irreTweetProbability = prob;
      this.guessMade = false;
   }
   
   
   // Methods
   // Getters
   public String getTopic() { return this.topic; }
   public String getSentiment() { return this.sentiment; }
   public float getIrreTweetPorbability() { return this.irreTweetProbability; }
   
   // Setters
   public void setTopic(String topic) { this.topic = topic; }
   public void setSentiment(String sentm) { this.sentiment = sentm; }
   public void setIrreTweetProb(float prob) { this.irreTweetProbability = prob; }
   
   // Create n Twitter bots with the default settings
   public static TwitterBot[] createBots(int n) {
      TwitterBot[] bots = new TwitterBot[n];
      
      return bots; 
   }
   
   // Create n Twitter bots with given settings
   public static TwitterBot[] createBots(int n, String topic, String sentm, float prob) {
      TwitterBot[] bots = new TwitterBot[n];
      
      // Apply irrelevent tweet probability to the output bots
      for (int i=0; i<bots.length; i++) {
         bots[i] = new TwitterBot(topic, sentm, prob);
         
         // Switch tweet's sentiment up to the given irrelevant tweet probability
         float randProb = rand.nextFloat();
         if (randProb < prob) {
            if (bots[i].getSentiment().equalsIgnoreCase(Constants.SENTIM_POSITIVE)) {
               bots[i].setSentiment(Constants.SENTIM_NEGATIVE);
            }
            else if (bots[i].getSentiment().equalsIgnoreCase(Constants.SENTIM_NEGATIVE)) {
               bots[i].setSentiment(Constants.SENTIM_POSITIVE);
            }
            else {
               bots[i].setSentiment(Constants.SENTIM_POSITIVE); // Convert "neutral" into "positive" only
            }
         }
      }
      
      return bots; 
   }
   
   // Respond a Tweet with given sentiment
   Tweet reply(Tweet inTweet) {
      String topic = inTweet.getTopic();
      String sentm = Constants.SENTIM_NEUTRAL;
      String id = Long.toString(rand.nextInt(Integer.MAX_VALUE));
      String date = (new Date()).toString();
      String text;
      String[] fields = new String[Constants.TWEET_NUM_OF_FIELDS];
      
      if (this.sentiment.equalsIgnoreCase(Constants.SENTIM_POSITIVE)) {
         sentm = Constants.SENTIM_POSITIVE;
         text = Constants.RESPONSE_AGREE;
      }
      else if (this.sentiment.equalsIgnoreCase(Constants.SENTIM_NEGATIVE)) {
          sentm = Constants.SENTIM_NEGATIVE;
          text = Constants.RESPONSE_DISAGREE;
      }
      else {
         sentm = Constants.SENTIM_NEUTRAL;
         text = Constants.RESPONSE_NOCOMMENTS;
      }
      
      // Populate response tweet fields
      fields[0] = topic;
      fields[1] = sentm;
      fields[2] = id;
      fields[3] = date;
      fields[4] = text;
      
      Tweet rsps = new Tweet(fields);
      
      return rsps;
   }
   
   // Guess bot's design
   public boolean guess(String topic, String sentm) {
      if (guessMade) {
         return false;
      }
      
      return topic.equalsIgnoreCase(this.topic) && sentm.equalsIgnoreCase(this.sentiment);
   }
}