public class Tweet{
   private String topic;
   private String sentiment;
   private String ID;
   private String date;
   private String text;

   public Tweet(String[] fields ) {
      this.topic = fields[0];
      this.sentiment = fields[1];
      this.ID = fields[2];
      this.date = fields[3];
      this.text = fields[4]
   
}