public class Moderator {
  // Attributes
   private String topic;
   private String sentiment;
   private String ID;
   private String date;
   private String text;

   // Constructors
   public Tweet(String[] fields) {
      this.topic = fields[0];
      this.sentiment = fields[1];
      this.ID = fields[2];
      this.date = fields[3];
      this.text = fields[4];
   }


   // Getters
   public String getTopic() { return this.topic; }
   public String getSentiment() { return this.sentiment; }
   public String getID() { return this.ID; }
   public String getDate() { return this.date; }
   public String getText() { return this.text; }
}