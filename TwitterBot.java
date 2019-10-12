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
   
   private TwitterBot(String topic
     
}