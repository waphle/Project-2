# Project 2
In this project, you will use a data file of tagged social media posts to design a bot that affects the mood of a conversation. This project bears many similarities to the previous project, but uses objects and class inheritance.

You are being provided with a data set of 5113 tweets in a file (*full-corpus.csv*). This is a comma-separated value file (.csv) which is a human-readable file type in which each line contains data. Pieces of data are separated by a comma, and for this data set, there are five pieces of data (i.e., fields) per line. You can open up the file in any text editor (e.g., Sublime) or even Excel if you want to look at the data directly (and you should always inspect your data!). The first line contains the name of the fields: "Topic" (what is this tweet about?), "Sentiment" (is this tweet positive about that topic, negative, neutral, or irrelevant?), "TweetId" (a unique ID used by Twitter - all of these will be different), "TweetDate" (when did this get tweeted?), and "TweetText" (what did the tweet actually say?).

**Notice - these are real tweets from real twitter users, and do not reflect the view of your instructor, department, or university. Tweets may contain foul language  or worse.**

**Notice - some of the tweets are tagged as 'irrelevant'. For this project, they truly are. You may safely ignore them.**

# Teams
Teams have been assigned for this project and posted on Blackboard. They are fixed - no switching or cooperating across team lines. It is up to teammates to ensure that their partner adheres to the <a href="https://www.american.edu/academics/integrity/code.cfm">American University Honor Code.</a>

## Step 0 - Background Research.
1. **Both members** should review the description for <a href="https://en.wikipedia.org/wiki/Group_polarization">Group Polarization</a> on Wikipedia.
1. **Both members** should work together to devise the new PusherBot class described below.

## Step 1 - Import and Clone the Repo for your Team.
1. **Both members** will clone the repository to your local machines. You will then each have a local repository that is linked to the shared repository, and can work on the code together.
1. As a reference for how to use git, I suggest <a href='http://codingdomain.com/git/'>this site</a>, as it avoids some of the more complicated theory behind git and focuses on the bare minimum practicalities.

## Step 2 - Review the Provided Code.
In the starter code you have been provided, you will find a superclass called ChatterBot and a subclass called NeutralBot. You will notice that in the class header for the NeutralBot, it says that the NeutralBot *extends* ChatterBot. We will discuss this relationship - **inheritance**- in an upcoming lesson. For now, you should know that all NeutralBots objects are also ChatterBot objects, but not all ChatterBot objects are also NeutralBot objects.

All ChatterBot objects have two class member variables: a list of messages that they can use to respond to others, and a ChatRoom object that they receive updates from and post responses to. The also have a mood, which can potentially be affected by the things they read and post.

```java
protected Message[] messages;
protected final ChatRoom room;
protected double mood = 1;
```

NeutralBot class instances should read and post responses to messages based on the valence of their mood (negative:0; neutral:1; positive:2). When created, an instance of a NeutralBot starts out close to a neutral valence with some small variation (+/- 0.125). The mood represents the probability that a NeutralBot will respond to a message, and if it does, whether that response will be negative, neutral, or positive.

- A NeutralBot will only read a message if it is Neutral or has the same valence as its mood (positive mood will respond to positive or neutral, negative will respond to negative or neutral).
- When it reads a message, its mood shifts slightly towards the valence of the message that it read. It shifts +/- 0.01, depending on the message sentiment.
- It will respond to a message based on the results of a random number. While the mood of the bot is close to neutral, it is less likely to post. As the mood begins to move closer to positive or negative, the possibility of the bot posting grows larger.

All ChatterBots have a single ChatRoom object that they are associated with. That ChatRoom notifies each ChatterBot that a message has been posted and allows bots to post new messages.

## Step 3 - Parse the Data File.
Your first task is to write the code in the `MessageParser` class to read the data into the program from the csv file. There is an empty shell of a method to do this - you need to fill in the missing code so that that method reads each line of the csv into a new Message object. Don't forget, the first line is not actually a message, it contains the field names! How to do this - look at Chapter 12 in the book. Be wary of the subtle gotcha here - this is a file encoded as "UTF-8", so whatever class you are using to read in the data needs to know that to read it correctly.

## Step 4 - Give NeutralBots the ability to post.
Finish the missing code in the NeutralBot's `post()` method. While the mood of the NeutralBot is between 0.5 and 1.5, it will only post neutral articles. Once the mood of the NeutralBot dips below 0.5 (negative) or above 1.5 (positive), it should then begin posting positive or negative articles to match its mood.

## Step 5 - Write your own bot.
Create a new class, `PusherBot`, that retrieves the mean mood of the ChatRoom and tries to slowly push that value of that room to its extreme. If the mean is negative, it should post negative or neutral messages. If it is positive, the Bot should post positive messages.

## Step 6 - Provide an environment for lonely bots to chat
Finish the incomplete methods specified in the ChatRoom class according to the specifications in the Javadoc comments. When a ChatterBot decides to post to a ChatRoom, it should call its ChatRoom object's `postNewMessage()` method.

## Step 7 - Kick it all off.
Create a `Test` class with a main method from which to run your program. This class should run the method to parse the messages, create an instance of the `ChatRoom` class, and populate it with ChatterBots. There should be 90 NeutralBots and 10 of your newly-created `PusherBot` class. You will need to kick things off with one or more messages in the `ChatRoom` posts list.
- If there are messages to post, the ChatRoom should update all of the bots that it knows about.
- As each Bot is updated with each post, that bot will decide whether or not to post a message for the next round of updates.
- When all the bots have been updated with all the posts, if there are new posts, the process should repeat.

The loop should run until either the mean mood of the ChatRoom is below 0.1 or above 1.9, or for 1000 iterations, whichever comes first. When the loop terminates, the system should print out the number of messages posted by all bots, the number of iterations, and the final mood mean and variance values.

## Step 7 - Push to Github
Do not forget to push your final submission to Github before the deadline.

# Grading
Grading will be assigned according to the following categories. As described in the syllabus, each category can receive a  &#10003; (satisfactory); a &#10003;+ (above and beyond); a &#10003;- (incorrect, incomplete, or sloppy); or in the worst case an &#10005;, meaning it was altogether missing or inappropriate (assigned at the instructor's discretion). A category assigned an &#10005; can carry significant grade penalties for this assignment.

1. Repository / Teamwork. Everyone contributed and used the repository well.
1. Comments and Code Formatting. The code is easy to follow and understand.
1. Project Design. The code is designed to do what the project requires.
1. Implementation. The code does what it was designed to do.

Rating | Repository | Form | Design | Implementation
-|-|-|-|-
&#10003;+ | Evidence of branching and merging. | Project is commented and styled according to [Javadoc](https://www.oracle.com/technetwork/java/javase/documentation/index-137868.html) Guidelines. | Project includes all steps and an additional `DividerBot` class that attempts to maximize the variance of the mood in the ChatRoom. | Project is free of logical and syntax errors; extensive and creative use of Java commands and classes to reduce length and/or complexity.
&#10003; | Multiple commits from each teammate | Project is commented and styled neatly. | Project includes working steps 1-5 with a visible attempt to complete the remaining steps. | Project compiles
&#10003;- | Evidence that one teammate did most of the work and/or all commits were through web interface | Comments are sparse and/or code styling is difficult to follow. | Project missing `PusherBot` design and/or `Test` class with main method | Project compiles with some minor correction