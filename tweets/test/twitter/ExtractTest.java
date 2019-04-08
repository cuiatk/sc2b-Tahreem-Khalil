/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.awt.List;
import java.io.OptionalDataException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.OptionalLong;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * 
     * testing strategy for getTimespan and getMentionedUsers
     * 
     * partition space for getTimespan(tweets) -> timespan
     * tweet.size(): 1,>1
     * check tweets having similar timspan
     * check if instant d1, d3 is the starting and ending instants
     * 
     * partition for getMentionedUsers(tweets) -> mentionedUsers
     * tweet.size(): 0,1,>1
     * mentions in tweet: 0,1,>1
     * user names with mixed cases: upper, lower, mixed-case
     * check if any tweet has mentions twice
     * 
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "yassGirl", "to be or not to be. @byeBoss @yoloGirl", d3);
    private static final Tweet tweet4 = new Tweet(4, "tahreem_k", "you should see me in a crown.", d2);
    private static final Tweet tweet5 = new Tweet(5, "starry_girl", "desi parents are too much.", d2);
    private static final Tweet tweet6 = new Tweet(6, "likeWise_b", "@hobbit_man @BLUE_NOT @CANyou_not get ready.", d2);
    
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    @Test
    /*
     * test case: checking timespan one tweet
     * tweet.size = 1
     * 
     */
    public void testGetTimespanOfOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
        
        assertFalse("expected empty tweets list",Arrays.asList(timespan).isEmpty());
    }
    
    
    @Test
    /*
     * test case: checking timespan two tweet
     * tweet.size >1
     * 
     */
    public void testGetTimespanOftwoTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1,tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
        
        assertFalse("expected empty tweets list",Arrays.asList(timespan).isEmpty());
    }
    
    @Test
    /*
     * test case: checking timespan two tweet
     * tweet.size >1
     * 
     */
    public void testGetTimespanOfMultipleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1,tweet2,tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
        
        assertFalse("expected empty tweets list",Arrays.asList(timespan).isEmpty());
    }
    
    
    /*
     * test case: check if tweet list is empty
     * tweet.size = 0
     * mentions = 0
     */
    @Test
    public void testGetMentionedUsersNoMentionNoTweet() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList());
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
      
        
    }
    
    /*
     * test case: one tweet one mention
     * tweet.size = 1
     * mention = 1
     */
    @Test
    public void testGetMentionedUsersOneMentionOneTweet() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet2));
        Set<String> mentionedUsersLowerCase = new HashSet<>();
        for (String mentionedUser : mentionedUsers) {
            mentionedUsersLowerCase.add(mentionedUser.toLowerCase());
        }
        assertTrue(mentionedUsersLowerCase.contains("rivest"));
    }
    
    /*
     * test case: one tweet zero mention
     * tweet.size = 1
     * mention = 0
     */
    @Test
    public void testGetMentionedUsersZeroMentionOneTweet() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
       assertEquals("expected no mentions",0,mentionedUsers.size());
    }
    
    /*
     * checking one tweet has two mentions
     * tweet.size = 1
     * mentions = >1
     */
    @Test
    public void testGetMentionedUsersTwoMentionOneTweet() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        Set<String> mentionedUsersLowerCase = new HashSet<>();
        for (String mentionedUser : mentionedUsers) {
            mentionedUsersLowerCase.add(mentionedUser.toLowerCase());
        }
        assertTrue(mentionedUsersLowerCase.containsAll(Arrays.asList("byeboss", "yologirl")));
    }
    
    /*
     * checking multiple tweet has one mentions
     * tweet.size = >1
     * mentions = 1
     */
    @Test
    public void testGetMentionedUsersOneMentionMultipleTweet() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2));
        Set<String> mentionedUsersLowerCase = new HashSet<>();
        for (String mentionedUser : mentionedUsers) {
            mentionedUsersLowerCase.add(mentionedUser.toLowerCase());
        }
        assertTrue(mentionedUsersLowerCase.containsAll(Arrays.asList("rivest")));
    }
    
    /*
     * checking multiple tweet has multiple mentions
     * tweet.size = >1
     * mentions = >1
     */
    @Test
    public void testGetMentionedUsersMultipleMentionMultipleTweet() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3));
        Set<String> mentionedUsersLowerCase = new HashSet<>();
        for (String mentionedUser : mentionedUsers) {
            mentionedUsersLowerCase.add(mentionedUser.toLowerCase());
        }
        assertTrue(mentionedUsersLowerCase.containsAll(Arrays.asList("rivest","yologirl","byeboss")));
    }
    
    /*
     * checking multiple tweet has zero mentions
     * tweet.size = >1
     * mentions = 0
     */
    @Test
    public void testGetMentionedUserZeroMentionMultipleTweet() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet4, tweet5));
        
        assertEquals("expected no mentions",0, mentionedUsers.size());
    }
    
    /*
     * checking multiple tweet has multiple mentions lower case
     * tweet.size = >1
     * mentions = >1
     * case : lower, upper, mixed case
     */
    @Test
    public void testGetMentionedUserMultipleMentionMixedCase() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        
        assertTrue("expected to have all mentions in lower case",mentionedUsers.containsAll(Arrays.asList("hobbit_man","blue_not","canyou_not")));
    }
    
    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
