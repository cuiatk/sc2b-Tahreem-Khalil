/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
    	
    	List<Tweet> tweetWrittenBy = new ArrayList<>();
    	 	
         if(tweets.isEmpty())
         {
      	   System.out.println("list is empty");
         }
         else
         {
        	 for (Tweet tweet : tweets) 
        	 {
        		 if (tweet.getAuthor().toLowerCase().equals(username.toLowerCase()))
        		 {
                    System.out.println(tweetWrittenBy.add(tweet)+" written by"); 
                 }
			 }
         	
         }
 	 
    	return   tweetWrittenBy;
       // throw new RuntimeException("not implemented");
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    @SuppressWarnings("unused")
	public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
    	
    	
    	List<Tweet> tweetWrittenByTimespan = new ArrayList<>();
    	Timespan timeSpan = null;
    	Instant startingPointInstant = null;
    	Instant endingPointInstant = null;
    	
			//startingPointInstant=tweets.get(0).getTimestamp();
    		//endingPointInstant=tweets.get(tweets.size()-1).getTimestamp();
    		
    	     //timeSpan=new Timespan(startingPointInstant, endingPointInstant);	
		
         if(tweets.isEmpty())
         {
      	   System.out.println("list is empty");
         }
         else
         {
        	 for (Tweet tweet : tweets) 
        	 {
        		 if (tweet.getTimestamp().isAfter(timespan.getStart()) && tweet.getTimestamp().isBefore(timespan.getEnd()))
        		 {
                   tweetWrittenByTimespan.add(tweet); 
                 }
        		 else {
					System.out.println("no tweet is present in particular time sapn");
				}
			 }
         	
         }	 
    	return   tweetWrittenByTimespan;
    	
        //throw new RuntimeException("not implemented");
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
    	
    	List<Tweet> tweetContainCertainWord = new ArrayList<>();
    	  List<String> loweredCaseList = new ArrayList<>();
    	  
    	  
          for (String lowered : words) {
              loweredCaseList.add(lowered.toLowerCase());
          }
    	
        if(tweets.isEmpty())
        {
     	   System.out.println("list is empty");
        }
        else
        {
       	 for (Tweet tweet : tweets) 
       	 {
       			 List<String> wordsfromtweet = new ArrayList<String>(Arrays.asList(tweet.getText().split(" ")));
       		 for (String wordTweet : wordsfromtweet) {
				
       			if (loweredCaseList.contains(wordTweet.toLowerCase()))
       		 {
                  tweetContainCertainWord.add(tweet); 
                  break;
                }
       		 
			}
       		 
		 }
        	
        }	
        
        for (Tweet containingList : tweetContainCertainWord) {
           System.out.println(containingList+"containing");
        }
        
        return tweetContainCertainWord;
        //throw new RuntimeException("not implemented");
    }

}
