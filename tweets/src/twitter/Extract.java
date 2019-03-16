/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	Timespan timespan = null;
    	Instant startingPointInstant = null;
    	Instant endingPointInstant = null;
    	
    	if(tweets.isEmpty())
    	{
    		try {
				throw new Exception("the list is empty");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else 
    	{
    		startingPointInstant=tweets.get(0).getTimestamp();
    		endingPointInstant=tweets.get(tweets.size()-1).getTimestamp();
    		
    	     timespan=new Timespan(startingPointInstant, endingPointInstant);	
    		  
    	}
    	System.out.println(timespan);
    	return timespan;
		
       // throw new RuntimeException("not implemented");
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
       	
    	Pattern p = Pattern.compile("\\B@[a-zA-Z0-9_-]+\\b");
    	
    	ArrayList<String> textList=new ArrayList<String>(); 
    	Set<String> mention=new HashSet<String>();
    	
    	
    	if(tweets.isEmpty())
    	{
    		System.out.println("list is empty");
    	}
    	else
    	{
			for (int i = 0; i <= tweets.size()-1; i++)
    		{
    			textList.add(i,((Tweet) tweets.get(i)).getText());
    		}
		}
    	
       for (String text : textList) 
        {
             System.out.println("text in list = " + text);
        } 
    	
    	
       if(textList.isEmpty())
       {
    	   System.out.println("list is empty");
       }
       else 
       {
    	   for (String text : textList)
    	   {
    		   Matcher matcher = p.matcher(text);
    		   
              while(matcher.find())
               {
            	   String myuser = matcher.group().substring(1);
                   mention.add(myuser);
                   continue;
               }
              
    	   }        
       }
       
       for (String text : mention) 
       {
            System.out.println("mentions in Tweet = " + text);
       } 
   	
         return (Set<String>) mention;
        //throw new RuntimeException("not implemented");
    }

}
