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

import org.eclipse.jdt.annotation.Nullable;
import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * 
     * testing strategy for getTimespan and getMentionedUsers
     * 
     * partition space for getTimeSpan
     * check if the list is empty if not empty then,
     * check if instant d1 is the starting point
     * check if instant d3 is the ending point
     * 
     * partition for getMentionedUsers
     * check if tweet list is empty if not empty then,
     * check if tweets are null
     * check if tweet has mention once
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
    
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * checking if list is empty, and also asserting instants time
     */
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1,tweet2,tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
        
        assertFalse("not expected empty tweets list",Arrays.asList(timespan).isEmpty());
       
        assertEquals("tweet 1 id is null", tweet1.getId(), tweet1.getId());
        assertEquals("tweet 2 id is null", tweet2.getId(), tweet2.getId());
        

       // assertEquals("instant d1 is not equal ", d1, timespan.getStart().is);
        //assertEquals("instant d2 is not equal ", d2, timespan.getStart().toString()=="");
        assertNotNull("instance 2 is null", d2);
    }
    
    /*
     * checking if list is empty
     */
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1,tweet2,tweet3));
        
        assertFalse("expected empty set", mentionedUsers.isEmpty());
        
        assertNotNull("tweet 1 is null", tweet1);
        assertNotNull("tweet 2 is null", tweet2);
        
    }
    
    /*
     * checking if tweet has one mention
     */
    @Test
    public void testGetMentionedUsersOneMentionTweet() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet2));
        Set<String> mentionedUsersLowerCase = new HashSet<>();
        for (String mentionedUser : mentionedUsers) {
            mentionedUsersLowerCase.add(mentionedUser.toLowerCase());
        }
        assertTrue(mentionedUsersLowerCase.contains("rivest"));
    }
    
    /*
     * checking if tweet has two mentions
     */
    @Test
    public void testGetMentionedUsersTwoMentionTweet() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        Set<String> mentionedUsersLowerCase = new HashSet<>();
        for (String mentionedUser : mentionedUsers) {
            mentionedUsersLowerCase.add(mentionedUser.toLowerCase());
        }
        assertTrue(mentionedUsersLowerCase.containsAll(Arrays.asList("byeboss", "yologirl")));
    
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
