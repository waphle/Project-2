import java.util.Scanner;

public class TwitterBot {
  //Wow, such empty

  public static void main(String[] args) throws Exception{

    java.io.File file = new java.io.File("full-corpus.csv");

    Scanner input = new Scanner(file, "UTF-8");

    while(input.hasNext()){
      String line = input.nextLine();
      String[] fields = line.split("\",\"");

      for(String str: fields)
        System.out.println(str);
   }
  }
}