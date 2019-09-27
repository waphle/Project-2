# Project 2
In this project, you will use a data file of tagged social media posts to design a bot. Unlike the previous homework where we kept most of the code in the `main` method, you will be designing classes that interact with each other.

## The Data
You are being provided with a data set of 5113 tweets in a file (*full-corpus.csv*). This is a comma-separated value file (.csv) which is a human-readable file type in which each line contains data. Pieces of data are separated by a comma, and for this data set, there are five pieces of data (i.e., fields) per line. You can open up the file in any text editor (e.g., Sublime) or even Excel if you want to look at the data directly (and you should always inspect your data!). The first line contains the name of the fields: "Topic" (what is this tweet about?), "Sentiment" (is this tweet positive about that topic, negative, neutral, or irrelevant?), "TweetId" (a unique ID used by Twitter - all of these will be different), "TweetDate" (when did this get tweeted?), and "TweetText" (what did the tweet actually say?).

**Notice - these are real tweets from real twitter users, and do not reflect the view of your instructor, department, or university. Tweets may contain foul language  or worse.**

Here is what the first few lines of the file looks like as a table:

> "Topic","Sentiment","TweetId","TweetDate","TweetText"
>
> "apple","positive","126415614616154112","Tue Oct 18 21:53:25 +0000 2011","Now all @Apple has to do is get swype on the iphone and it will be crack. Iphone that is"
>
> "apple","negative","125405005493706752","Sun Oct 16 02:57:37 +0000 2011","@Blackberry does take better quality photos than @Apple iPhone though. I give them that! #photograhersEye"

As this is a comma-separated value (.csv) file, it might be easier too look at what data these lines represent:

"Topic"|"Sentiment"|"TweetId"|"TweetDate"|"TweetText"
-|-|-|-|-
"apple"|"positive"|"126415614616154112"|"Tue Oct 18 21:53:25 +0000 2011"|"Now all @Apple has to do is get swype on the iphone and it will be crack. Iphone that is"
"apple"|"negative"|"125405005493706752"|"Sun Oct 16 02:57:37 +0000 2011"|"@Blackberry does take better quality photos than @Apple iPhone though. I give them that! #photograhersEye"

## The Goal
For this project you will be designing three classes: `TwitterBot.java`, `Tweet.java`, and `Moderator.java`. The exact contents of each of the classes are largely up to you, though there is functionality that is required for each of them. Some guidelines are given below for how to structure your code, but a major component of this assignment is to design classes that achieve the desired results.

Each of these classes has a different purpose, which is outlined below:

**TwitterBot.java**: A TwitterBot has an agenda. If it sees a tweet that is relevant to a topic that the bot cares about, it posts a reply. This reply can be positive or negative, depending on whether the bot was created to sway opinion about the topic in a positive or negative direction. These do not have to come up with new content for tweets, they can reuse existing tweets that are in the provided data file.

**Tweet.java**: A tweet is a container class for all of the data associated with a single twitter message. These are the fields that are present in each row of the date file you have been provided with. Access modifiers and appropriate getter / setter methods should be provided for all necessary fields.

**Moderator.java**: A moderator is always on the lookout for bots that are trying to sway public opinion about a topic. Like a detective, the moderator has to figure out which users are genuine, and which are bots. The moderator tests the users by giving them tweets and seeing what their reply is. If a user replies frequently to tweets about a topic in the negative, then it may be a bot that is configured to do so.

# Teams
Teams have not been assigned for this project, but you are expected to work with a partner. However - no cooperating across team lines. It is up to teammates to ensure that their partner adheres to the <a href="https://www.american.edu/academics/integrity/code.cfm">American University Honor Code.</a>

## Step 1 - Import and Clone the Repo for your Team.
1. **Both members** will clone the repository to your local machines. You will then each have a local repository that is linked to the shared repository, and can work on the code together.
1. As a reference for how to use git, I suggest <a href='http://codingdomain.com/git/'>this site</a>, as it avoids some of the more complicated theory behind git and focuses on the bare minimum practicalities.

## Step 2 - Design your Classes as a Team.
No matter what methods and fields are in your classes, you need to be able to ensure that everyone is in agreement about the overall design. This means that, up front, you should start by having a conversation about what the initial UML diagram for your classes should be. We have talked in class about UML diagrams, and there is much more information in the textbook.

You and your partner should create a file in your repository that contains your up-to-date UML diagrams for your classes. This does not have to be fancy, and can even be a simple text file, but it should have class diagrams for all three of the classes. Everything that is in the classes (fields, methods, contructors) should be captured in the class diagrams by the time of submission. You do not have to capture class interaction or more complex UML aspects, simply an accurate list (with descriptions) for what each class contains and can do. You may find it useful to use an online text editor for UML diagrams (e.g., [PlanText](https://www.planttext.com)).

## Step 3 - Write the code for your Tweet Class.
Whatever your group decides should be included in the Tweet class, your first priority should be to write the code that defines the fields and methods in that class. You should work on this together, as it will affect the other two classes. While you have some flexibility in how you define things, there are two requirements:

1. The Tweet class must store and provide access to all of the contents of the messages for each row
2. Tweet objects must be immutable

You are not expected to analyze the tweet text in any way for this. Other algorithms have been used to detect topic and sentiment for these tweets, and by writing a Tweet class, you can take full advantage of this.

## Step 4 - Write code to Parse the Data File.
Now that you have a way to store tweets in objects, your next task is to write code to read the data into the program from the csv file. How to do this - look at Chapter 12.11 (specifically Listing 12.15) in the book. Be wary of the subtle gotcha here - this is a file encoded as "UTF-8", so whatever class you are using to read in the data needs to know that to read it correctly.

Here is an example of the code in Section 12.11, with some slight modifications. You will need to adopt this code to get it working with your design, but this will give you some idea for how a file is read line by line. Notice the `split` method - it takes a string and splits it by commas that are surrounded by " " marks.

```java
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
```

Some important considerations:

1. Where will the code for this go? It could, potentially, be in any of the classes.
1. Is it static or non-static?
1. Does it go into a main method, or some other method?

Regardless of your choices, all of the data should be converted into Tweet objects and made available in your program for use by the Moderator and TwitterBot classes.

## Step 5 - Write the code for the TwitterBot class
Your TwitterBot class can be initialized in any number of ways, but there are some firm requirements:

1. It should have at least one constructor that takes a topic as a String and a sentiment as a String. This will define whether it is in favor of that topic, neutral about that topic, or negative. This constructor, like all the constructors, should be private.
1. It should have at least one other method that takes a Tweet object as a parameter and returns a different Tweet object (chosen from the tweets that you have parsed). The object passed to this method represents a message that your bot is reading, and the returned object is the reply. If the bot chooses not to reply to the message, then the returned object should be null.
1. It should have a static method that takes an int n and returns an array of n length initialized with TwitterBot objects. These can each be initialized with different parameters or all the same, it is up to you. This should be the only place that TwitterBot objects can be constructed, otherwise the Moderator might cheat!
1. The bot should have a method called `guess` that takes a topic and a sentiment and returns true if the parameters match the topic and sentiment of the Bot. The bot should only allow this method to be called once, otherwise the Moderator might cheat!

Other methods could help supplement the behavior of your bot. For example, constructors with additional parameters could control the probability of a response, or indicate whether the bot should occasionally tweet "irrelevant" tweets. This is up to you, but remember the lessons that you learned in the previous homework. Sometimes complex ideas sound captivating, but the simpler solutions (e.g., Tit-for-Tat) are the best.

While it can be advantageous to occasionally vary your responses, remember that the ultimate goal of the Bot is that when it does tweet about its topic, it is more likely to tweet according to its sentiment than against it. So while randomness might help you avoid detection, it may be against the best interests of the bot! However, adding bots that tweet randomly to the pool of bots can help lower the accuracy of the Moderator, so it can be a useful strategy! Just make sure to allow a setting for topic="none" so that a bot doesn't tweet randomly when it is supposed to be supporting a specific topic.

## Step 6 - Write the code for the Moderator class.
Create a main method for the moderator class - this will be the primary entry point of your program. The moderator class should use the required static method of the TwitterBot class to get an array of TwitterBot objects to test. How many is up to you, but it should be at least 100. For each of them, the moderator will call the correct object's method to pass it a tweet and observe its response. Working methodically, it should determine what topics provoke a positive response, and which topics provoke a negative response.

Your moderator can attempt to spam each bot with a thousand tweets about each topic (half positive, half negative), but if the bot is tracking how many of a particular topic it sees it might catch on. So, you may want to be clever, and mix it up a little bit.

At the end of the Moderator's analysis, it will make a guess for each of the bots: which topic the bot was associated with, and whether the bot was positive, negative, or neutral. For each bot, it will call the `guess` method with these parameters to find out if it was right.

The Moderator will then print out the results:
1. The % of bots that it correctly identified
1. For the correct bots, the count of each pair of topic + sentiment
1. For the incorrect bots, the count of each pair of topic + sentiment

## Step 7 - Push to Github
Do not forget to push your final submission to Github before the deadline.

# Grading
Grading will be assigned according to the following categories. As described in the syllabus, each category can receive a  &#10003; (satisfactory); a &#10003;+ (above and beyond); a &#10003;- (incorrect, incomplete, or sloppy); or in the worst case an &#10005;, meaning it was incorrect in several ways. A category assigned an &#10005; can carry significant grade penalties for this assignment, but usually does not receive more than 50% of the possible credit for an task.

Missing components receive a score of zero, but it is better to be missing a component (or comment it out) instead of submitting code that does not compile. Code that does not compile will not be graded, and a zero will be assigned for the project. Submitted code that contains runtime errors will be graded at the instructor's discretion.

1. **10%** Multiple commits were made throughout the project as progress was made, not just one big upload at the end. Multiple branches were created throughout the course of the project and were merged as progress was made on individual features.
1. **10%** The code is easy to follow and understand, and a UML diagram is included to clearly communicate the contents of classes.
1. **10%** The data file is parsed correctly and made available to the Tweet Class and Moderator class in some way.
1. **20%** Tweet Class supports the required functionality.
1. **20%** Moderator Class has the required constructor and methods.
1. **20%** TwitterBot class has the required constructor and methods.
1. **10%** The results are printed cleanly at the end.

## Acknowledgment
Data for this assignment comes from Sanders Analytics, and is compiled (among other places) in a [Github repo](https://github.com/zfz/twitter_corpus) by u/zfz.