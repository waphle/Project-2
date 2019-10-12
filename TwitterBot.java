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
      this irreTweetProbability = prob;
      this.guessMade = false;
   }
   
   // Name
   public String getTopic() { retun this.topic; }
   public String getSentiment() { return this.sentiment; }
   public float getIrreTweetPorbability() {return this.irreTweenProbability; }
   
   // Name
   public void setTopic(String topic) { this.topic = topic; }
   public void setSentiment() return this,sentiment;} //
   public void setIrreTweetProb(float prob) { this.irreTweetProbability = prob; }
   
   
   //Create n number of Twitter Bots with the default settings
   public static TwitterBot[] createBots(int n, String topic, String sentm, float prob) {
      TwitterBot[] bots = new TwitterBot[n];
      
      // Name
      for (int i = 0; i < bots.length; i++) {
         bots[i] = new TwitterBot(topic, sentm, prob);
         
         // Switch tweet's sentiment up to the given irrelevant tweet probability
         float  randProb = rand.nextFloat();
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
   
   
}